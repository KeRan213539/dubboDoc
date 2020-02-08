package top.klw8.alita.dubbodoc.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import top.klw8.alita.dubbodoc.annotations.DubboApi;
import top.klw8.alita.dubbodoc.annotations.DubboApiModule;
import top.klw8.alita.dubbodoc.annotations.RequestBean;
import top.klw8.alita.dubbodoc.annotations.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboDocAnnotationScanner
 * @Description: 扫描并处理dubbo doc 注解
 * @date 2020/2/2 18:26
 */
@Slf4j
public class DubboDocAnnotationScanner implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        // TODO 注册doc需要的提供者的时候,看看 exportDubboService 方法里面很重的那个东西给能重复使用,如果可以那alita那边也要改,即: 用同一个实例重复执行下面的代码
        //        service.setInterface(serviceClass);
        //        service.setRef(serviceImplInstance);
        //        service.setAsync(true);
        //        service.export();// 暴露及注册服务
        // 并且是否异步根据Service注解里的来(在这里写死也可以, alita那边写死也可以,因为这样注册的就那几个服务而已)
        log.info("================= Dubbo Doc--开始扫描并处理Dubbo Doc注解 ================");

        Map<String, Object> apiModules = applicationContext.getBeansWithAnnotation(DubboApiModule.class);
        apiModules.forEach((key, apiModule) -> {
            System.out.println("===================模块: "
                    + apiModule.getClass().getAnnotation(DubboApiModule.class).value()
                    + "【" + apiModule.getClass().getName() + "】");
            Method[] apiModuleMethods = apiModule.getClass().getMethods();
            for(Method method : apiModuleMethods){
                if(method.isAnnotationPresent(DubboApi.class)){
                    DubboApi dubboApi = method.getAnnotation(DubboApi.class);
                    System.out.println("    接口: " + dubboApi.value() + "【" + method.getName() + "】");
                    Annotation[][] argsAnns = method.getParameterAnnotations();
                    Class<?>[] argsClass = method.getParameterTypes();
                    for(int i = 0; i < argsClass.length; i++){
                        Annotation[] argAnns = argsAnns[i];
                        String parameterAnnStr = null;
                        if(ArrayUtils.isNotEmpty(argAnns)){
                            for(int j = 0; j < argAnns.length; j++){
                                Annotation argAnn = argAnns[j];
                                if(argAnn instanceof RequestParam){
                                    RequestParam requestParam = (RequestParam)argAnn;
                                    parameterAnnStr = requestParam.value();
                                }
                            }
                        }
                        Class<?> argClass = argsClass[i];
                        if(parameterAnnStr != null){
                            System.out.println("        【第" + i + "个参数: " + argClass.getCanonicalName() + "】方法参数上的RequestParam:" + parameterAnnStr);
                        } else {
                            System.out.println("        【第" + i + "个参数: " + argClass.getCanonicalName() + "】方法参数上没有RequestParam");
                        }

                        if(argClass.isAnnotationPresent(RequestBean.class)){
                            List<Field> allFields = getAllFields(null, argClass);
                            // TODO 暂不处理类中的属性是java bean的情况,后面再说
                            allFields.forEach(field -> {
                                if(field.isAnnotationPresent(RequestParam.class)){
                                    RequestParam requestParam = field.getAnnotation(RequestParam.class);
                                    System.out.println("            @有注解@: " + field.getName()
                                            + "【" + field.getType().getName() + "】" + requestParam.value());
                                } else {
                                    System.out.println("            @没注解@: " + field.getName()
                                            + "【" + field.getType().getName() + "】");
                                }
                            });
                        }
                    }
                    System.out.println();
                }
            }
        });

        log.info("================= Dubbo Doc--Dubbo Doc注解扫描并处理完毕 ================");
    }

    /**
     * @param fieldList
     * @param classz
     * @return
     * @Title: getAllFields
     * @Description: 获取类中的所有属性
     */
    private List<Field> getAllFields(List<Field> fieldList, Class<?> classz) {
        if (classz == null) {
            return fieldList;
        }
        if (fieldList == null) {
            fieldList = new ArrayList<>(Arrays.asList(classz.getDeclaredFields()));  // 获得该类的所有字段,但不包括父类的
        } else {
            CollectionUtils.addAll(fieldList, classz.getDeclaredFields()); // 获得该类的所有字段,但不包括父类的
        }
        return getAllFields(fieldList, classz.getSuperclass());
    }

}
