package top.klw8.alita.dubbodoc.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Import;
import top.klw8.alita.dubbodoc.annotations.DubboApi;
import top.klw8.alita.dubbodoc.annotations.DubboApiModule;
import top.klw8.alita.dubbodoc.annotations.RequestParam;
import top.klw8.alita.dubbodoc.core.beans.HtmlTypeEnum;
import top.klw8.alita.dubbodoc.core.beans.ParamBean;
import top.klw8.alita.dubbodoc.provider.DubboDocProviderImpl;
import top.klw8.alita.dubbodoc.provider.IDubboDocProvider;
import top.klw8.alita.dubbodoc.utils.ClassTypeUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboDocAnnotationScanner
 * @Description: 扫描并处理dubbo doc 注解
 * @date 2020/2/2 18:26
 */
@Slf4j
@Import({DubboDocProviderImpl.class})
public class DubboDocAnnotationScanner implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ApplicationConfig application;

    @Autowired
    private RegistryConfig registry;

    @Autowired
    private ProtocolConfig protocol;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // 注册dubbo doc 的提供者
        IDubboDocProvider dubboDocProvider = applicationContext.getBean(IDubboDocProvider.class);
        exportDubboService(IDubboDocProvider.class, dubboDocProvider, false);

        // 并且是否异步根据Service注解里的来(在这里写死也可以, alita那边写死也可以,因为这样注册的就那几个服务而已)
        log.info("================= Dubbo Doc--开始扫描并处理Dubbo Doc注解 ================");

        Map<String, Object> apiModules = applicationContext.getBeansWithAnnotation(DubboApiModule.class);
        apiModules.forEach((key, apiModule) -> {
//            System.out.println("===================模块: "
//                    + apiModule.getClass().getAnnotation(DubboApiModule.class).value()
//                    + "【" + apiModule.getClass().getCanonicalName() + "】");
            DubboApiModule moduleAnn = apiModule.getClass().getAnnotation(DubboApiModule.class);
            if(!apiModule.getClass().isAnnotationPresent(Service.class)){
                log.warn("【警告】{}使用了 @DubboApiModule 注解,但是它不是一个Dubbo提供者(没有使用{}注解)", apiModule.getClass().getName(), Service.class.getName());
                return;
            }
            Service dubboService = apiModule.getClass().getAnnotation(Service.class);
            boolean async = dubboService.async();
            Map<String, Object> moduleCacheItem = new HashMap<>(4);
            DubboDocCache.addApiModule(moduleAnn.apiInterface().getCanonicalName(), moduleCacheItem);
            //模块中文名称
            moduleCacheItem.put("moduleChName", moduleAnn.value());
            //包含包路径的接口名
            moduleCacheItem.put("moduleClassName", moduleAnn.apiInterface().getCanonicalName());
            //模块版本
            moduleCacheItem.put("moduleVersion", moduleAnn.version());

            Method[] apiModuleMethods = apiModule.getClass().getMethods();
            // 模块缓存中的api基本信息列表
            List<Map<String, Object>> moduleApiList = new ArrayList<>(apiModuleMethods.length);
            moduleCacheItem.put("moduleApiList", moduleApiList);
            for(Method method : apiModuleMethods){
                if(method.isAnnotationPresent(DubboApi.class)){
                    DubboApi dubboApi = method.getAnnotation(DubboApi.class);

                    //                    System.out.println("    接口: " + dubboApi.value() + "【" + method.getName() + "】");

                    // 模块中的api列表中的Api基本信息
                    Map<String, Object> apiListItem = new HashMap<>(4);
                    moduleApiList.add(apiListItem);
                    //用于调用的接口名
                    apiListItem.put("apiName", method.getName());
                    //接口中文名称
                    apiListItem.put("apiChName", dubboApi.value());
                    //接口版本
                    apiListItem.put("apiVersion", dubboApi.version());
                    //接口返回数据的说明
                    apiListItem.put("apiRespDec", dubboApi.responseClassDescription());

                    // 接口参数和响应信息
                    Map<String, Object> apiParamsAndResp = new HashMap<>(2);
                    DubboDocCache.addApiParamsAndResp(moduleAnn.apiInterface().getCanonicalName() + "." + method.getName(), apiParamsAndResp);

                    Class<?>[] argsClass = method.getParameterTypes();
                    Annotation[][] argsAnns = method.getParameterAnnotations();
                    Parameter[] parameters = method.getParameters();
                    List<Map<String, Object>> paramList = new ArrayList<>(argsClass.length);
                    apiParamsAndResp.put("async", async);
                    apiParamsAndResp.put("apiName", method.getName());
                    apiParamsAndResp.put("apiChName", dubboApi.value());
                    apiParamsAndResp.put("apiVersion", dubboApi.version());
                    apiParamsAndResp.put("apiRespDec", dubboApi.responseClassDescription());
                    apiParamsAndResp.put("apiModelClass", moduleCacheItem.get("moduleClassName"));
                    apiParamsAndResp.put("params", paramList);
                    apiParamsAndResp.put("response", ClassTypeUtils.calss2Json(method.getGenericReturnType(), method.getReturnType()));
                    for(int i = 0; i < argsClass.length; i++){
                        Class<?> argClass = argsClass[i];
                        Annotation[] argAnns = argsAnns[i];
                        Map<String, Object> prarmListItem = new HashMap<>(2);
                        paramList.add(prarmListItem);
                        prarmListItem.put("prarmType", argClass.getCanonicalName());
                        prarmListItem.put("prarmIndex", i);
                        RequestParam requestParam = null;
                        // 处理参数上的 RequestParam 注解
                        for(Annotation ann : argAnns) {
                            if (ann instanceof RequestParam){
                                requestParam = (RequestParam)ann;
                            }
                        }
                        ParamBean paramBean = this.processHtmlType(argClass, requestParam, null);
                        if(paramBean == null){
                            // 不是基本类型,处理方法参数中的属性
//                            List<ParamBean> apiParamsList = processField(argClass, 0);
                            List<ParamBean> apiParamsList = processField(argClass);
                            if(apiParamsList != null && !apiParamsList.isEmpty()) {
                                prarmListItem.put("prarmInfo", apiParamsList);
                            }
                        } else {
                            // 是基本类型
                            Parameter methodParameter = parameters[i];
                            prarmListItem.put("name", methodParameter.getName());
                            prarmListItem.put("htmlType", paramBean.getHtmlType().name());
                            prarmListItem.put("allowableValues", paramBean.getAllowableValues());
                            if(requestParam != null) {

                                // 处理参数上的 RequestParam 注解
                                prarmListItem.put("nameCh", requestParam.value());
                                prarmListItem.put("description", requestParam.description());
                                prarmListItem.put("example", requestParam.example());
                                prarmListItem.put("defaultValue", requestParam.defaultValue());
                                prarmListItem.put("required", requestParam.required());
                            } else {
                                prarmListItem.put("required", false);
                            }
                        }
                    }
                }
            }
        });
//        System.out.println(JSON.toJSONString(DubboDocCache.getApiParamsAndResp("top.klw8.alita.examples.dubbodoc.api.IDemoApi.demoApi5"), SerializerFeature.PrettyFormat));
        log.info("================= Dubbo Doc--Dubbo Doc注解扫描并处理完毕 ================");
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 处理方法参数中的属性,只处理1层,更深层直接转json,更深层也最多处理5层
     */
    private List<ParamBean> processField(Class<?> argClass) {

        List<ParamBean> apiParamsList = new ArrayList(16);
        // 获取所有字段
        List<Field> allFields = ClassTypeUtils.getAllFields(null, argClass);
        for (Field field : allFields) {
            ParamBean paramBean = new ParamBean();
            paramBean.setName(field.getName());
            paramBean.setJavaType(field.getType().getCanonicalName());
            RequestParam requestParam = null;
            if(field.isAnnotationPresent(RequestParam.class)) {
                // 处理属性上的 RequestParam 注解
                requestParam = field.getAnnotation(RequestParam.class);
//                    System.out.println("            @有注解@: " + field.getName()
//                            + "【" + field.getType().getCanonicalName() + "】" + requestParam.value());
                paramBean.setNameCh(requestParam.value());
                paramBean.setRequired(requestParam.required());
                paramBean.setDescription(requestParam.description());
                paramBean.setExample(requestParam.example());
                paramBean.setDefaultValue(requestParam.defaultValue());
            } else {
                paramBean.setRequired(false);
            }

            if(this.processHtmlType(field.getType(), requestParam, paramBean) == null){
                // 不是基本元素,处理成JSON
                Object objResult =  ClassTypeUtils.initClassTypeWithDefaultValue(field.getGenericType(), field.getType(), 0);
                if(!ClassTypeUtils.isBaseType(objResult)){
                    paramBean.setHtmlType(HtmlTypeEnum.TEXT_AREA);
                    paramBean.setSubParamsJson(JSON.toJSONString(objResult, ClassTypeUtils.FAST_JSON_FEATURES));
                }
            }
            apiParamsList.add(paramBean);
        }
        return apiParamsList;
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 判断该用什么 htmlType
     * @Date 2020/2/28 18:59
     * @param: classType
     * @param: annotation
     * @param: prarm
     * @return top.klw8.alita.dubbodoc.core.beans.ParamBean
     */
    private ParamBean processHtmlType(Class<?> classType, RequestParam annotation, ParamBean prarm){
        if(prarm == null){
            prarm = new ParamBean();
        }
        if(annotation != null){
            prarm.setAllowableValues(annotation.allowableValues());
        }
        // 是否有允许值
        boolean hasAllowableValues = (prarm.getAllowableValues() != null && prarm.getAllowableValues().length > 0);
        // 是否已处理
        boolean processed = false;
        if(Integer.class.isAssignableFrom(classType)){
            prarm.setHtmlType(HtmlTypeEnum.NUMBER_INTEGER);
            processed = true;
        } else if(Byte.class.isAssignableFrom(classType)){
            prarm.setHtmlType(HtmlTypeEnum.TEXT_BYTE);
            processed = true;
        } else if(Long.class.isAssignableFrom(classType)){
            prarm.setHtmlType(HtmlTypeEnum.NUMBER_INTEGER);
            processed = true;
        } else if(Double.class.isAssignableFrom(classType)){
            prarm.setHtmlType(HtmlTypeEnum.NUMBER_DECIMAL);
            processed = true;
        } else if(Float.class.isAssignableFrom(classType)){
            prarm.setHtmlType(HtmlTypeEnum.NUMBER_DECIMAL);
            processed = true;
        } else if(String.class.isAssignableFrom(classType)){
            prarm.setHtmlType(HtmlTypeEnum.TEXT);
            processed = true;
        } else if(Character.class.isAssignableFrom(classType)){
            prarm.setHtmlType(HtmlTypeEnum.TEXT_CHAR);
            processed = true;
        } else if(Short.class.isAssignableFrom(classType)){
            prarm.setHtmlType(HtmlTypeEnum.NUMBER_INTEGER);
            processed = true;
        }
        if(processed){
            // 处理过了,该返回了
            if(hasAllowableValues) {
                // 允许值有值,变成select
                prarm.setHtmlType(HtmlTypeEnum.SELECT);
            }
            return prarm;
        }

        // 还没处理过,继续
        if(Boolean.class.isAssignableFrom(classType)){
            prarm.setHtmlType(HtmlTypeEnum.SELECT);
            // boolean 只有 true/false可选,不管之前的允许值是啥,强制替换
            prarm.setAllowableValues(new String[]{"true", "false"});
            processed = true;
        } else if(Enum.class.isAssignableFrom(classType)){
            prarm.setHtmlType(HtmlTypeEnum.SELECT);
            if(!hasAllowableValues){
                // 如果没有可选值,从枚举里取. 有可选值的情况就不做可选值是否与枚举匹配的检查了,后面有必要再加
                Object[] enumConstants = classType.getEnumConstants();
                String[] enumAllowableValues = new String[enumConstants.length];
                try {
                    Method getNameMethod = classType.getMethod("name");
                    for (int i = 0; i < enumConstants.length; i++){
                        Object obj = enumConstants[i];
                        enumAllowableValues[i] = (String) getNameMethod.invoke(obj);
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    log.error("", e);
                }
                prarm.setAllowableValues(enumAllowableValues);
            }
            processed = true;
        }
        if(processed){
            return prarm;
        }
        return null;
    }

    private <I, T> void exportDubboService(Class<I> serviceClass, T serviceImplInstance, boolean async) {
        ServiceConfig<T> service = new ServiceConfig<>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(serviceClass);
        service.setRef(serviceImplInstance);
        service.setAsync(async);
//        service.setVersion("1.0.0");
        service.export();// 暴露及注册服务
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 处理方法参数中的属性,最多5层
     * @Date 2020/2/27 22:51
     * @param: argClass
     * @param: processCount
     * @return java.util.List<top.klw8.alita.dubbodoc.core.beans.ParamBean>
     */
//    private List<ParamBean> processField(Class<?> argClass, int processCount) {
//
//        if(processCount >= 5){
//            log.warn("使用Dubbo Doc的Bean的深度已超过5层,剩下的Dubbo Doc 注解将被忽略!请检查Bean中是否有循环引用!");
//            return null;
//        }
//        processCount++;
//
//        if(ClassTypeUtils.isBaseType(argClass)){
//            return null;
//        }
//
//        List<ParamBean> apiParamsList = new ArrayList(16);
//        // 获取所有字段
//        List<Field> allFields = ClassTypeUtils.getAllFields(null, argClass);
//        for (Field field : allFields) {
//            ParamBean paramBean = new ParamBean();
//            paramBean.setName(field.getName());
//            paramBean.setJavaType(field.getType().getCanonicalName());
//            if(field.isAnnotationPresent(RequestParam.class)) {
//                // 处理属性上的 RequestParam 注解
//                RequestParam requestParam = field.getAnnotation(RequestParam.class);
////                    System.out.println("            @有注解@: " + field.getName()
////                            + "【" + field.getType().getCanonicalName() + "】" + requestParam.value());
//                paramBean.setNameCh(requestParam.value());
//                paramBean.setDescription(requestParam.description());
//                paramBean.setExample(requestParam.example());
//                paramBean.setDefaultValue(requestParam.defaultValue());
//// TODO            paramBean.setHtmlType();
//                paramBean.setAllowableValues(requestParam.allowableValues());
//            }
//
//            // 处理常用集合
//            if (field.getType().isArray()) {
//                Class<?> arrType = field.getType().getComponentType();
//                List<ParamBean> tempList = processField(arrType, processCount);
//                if(tempList != null && !tempList.isEmpty()) {
//                    paramBean.setSubParams(tempList);
//                }
//            } else if (Collection.class.isAssignableFrom(field.getType())) {
//                // 已经判断了是 集合 ,肯定有泛型
//                ParameterizedType pt  = (ParameterizedType)field.getGenericType();
//                List<ParamBean> tempList = processField((Class<?>) pt.getActualTypeArguments()[0], processCount);
//                if(tempList != null && !tempList.isEmpty()) {
//                    paramBean.setSubParams(tempList);
//                }
//            } else if (Map.class.isAssignableFrom(field.getType())) {
//                // 已经判断了是 Map ,肯定有泛型
//                ParameterizedType pt  = (ParameterizedType)field.getGenericType();
//                List<ParamBean> tempList = processField((Class<?>) pt.getActualTypeArguments()[1], processCount);
//                if(tempList != null && !tempList.isEmpty()) {
//                    paramBean.setSubParams(tempList);
//                }
//            } else {
//                List<ParamBean> tempList = processField(field.getType(), processCount);
//                if(tempList != null && !tempList.isEmpty()) {
//                    paramBean.setSubParams(tempList);
//                }
//            }
//            apiParamsList.add(paramBean);
//        }
//        return apiParamsList;
//    }

}
