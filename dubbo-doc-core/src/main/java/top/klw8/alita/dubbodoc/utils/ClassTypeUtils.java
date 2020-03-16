package top.klw8.alita.dubbodoc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import top.klw8.alita.dubbodoc.annotations.RequestParam;
import top.klw8.alita.dubbodoc.annotations.ResponseProperty;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: JSONUtils
 * @Description: java class 工具类, dubbo doc专用
 * @date 2020/2/28 9:30
 */
@Slf4j
public class ClassTypeUtils {

    public static SerializerFeature[] FAST_JSON_FEATURES = {
            //是否输出值为null的字段,默认为false。
            SerializerFeature.WriteMapNullValue,
            //List字段如果为null,输出为[],而非null
            SerializerFeature.WriteNullListAsEmpty,
            //字符类型字段如果为null,输出为"",而非null
            SerializerFeature.WriteNullStringAsEmpty,
            //Boolean字段如果为null,输出为false,而非null
            SerializerFeature.WriteNullBooleanAsFalse,
            // 数字为null输出0
            SerializerFeature.WriteNullNumberAsZero,
            //消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
            SerializerFeature.DisableCircularReferenceDetect,
            // 使用 .name() 处理枚举
            SerializerFeature.WriteEnumUsingName
    };

    public static String calss2Json(Type genericType, Class<?> classType){
        Object obj = initClassTypeWithDefaultValue(genericType, classType, 0);
        return JSON.toJSONString(obj, FAST_JSON_FEATURES);
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据 Class 实例化
     */
    public static Object initClassTypeWithDefaultValue(Type genericType, Class<?> classType, int processCount) {
        if(processCount >= 10){
            log.warn("参数Bean的深度已超过10层,将忽略更深层!请修改参数结构或者检查Bean中是否有循环引用!");
            return null;
        }
        processCount++;

        if(Integer.class.isAssignableFrom(classType)){
            return 0;
        } else if(Byte.class.isAssignableFrom(classType)){
            return (byte)0;
        } else if(Long.class.isAssignableFrom(classType)){
            return 0L;
        } else if(Double.class.isAssignableFrom(classType)){
            return 0.0D;
        } else if(Float.class.isAssignableFrom(classType)){
            return 0.0F;
        } else if(String.class.isAssignableFrom(classType)){
            return "";
        } else if(Character.class.isAssignableFrom(classType)){
            return 'c';
        } else if(Short.class.isAssignableFrom(classType)){
            return (short)0;
        } else if(Boolean.class.isAssignableFrom(classType)){
            return false;
        } else if(Enum.class.isAssignableFrom(classType)){
            Object[] enumConstants = classType.getEnumConstants();
            StringBuilder sb = new StringBuilder("|");
            try {
                Method getName = classType.getMethod("name");
                for (Object obj : enumConstants){
                    sb.append(getName.invoke(obj)).append("|");
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                log.error("", e);
            }
            return sb.toString();
        } else if (classType.isArray()) {
            Class<?> arrType = classType.getComponentType();
            Object obj = initClassTypeWithDefaultValue(null, arrType, processCount);
            return new Object[]{obj};
        } else if (Collection.class.isAssignableFrom(classType)) {
            // 已经判断了是 集合 ,肯定有泛型
            if(genericType == null){
                return null;
            }
            ParameterizedType pt  = (ParameterizedType)genericType;
            Object obj = initClassTypeWithDefaultValue(null, (Class<?>) pt.getActualTypeArguments()[0], processCount);
            List<Object> list = new ArrayList<>(1);
            list.add(obj);
            return list;
        }  else if (CompletableFuture.class.isAssignableFrom(classType)) {
            // CompletableFuture
            if(genericType == null){
                return null;
            }
            ParameterizedType pt  = (ParameterizedType)genericType;
            String typeName = pt.getActualTypeArguments()[0].getTypeName();
            Class<?> typeClass;
            Class<?>[] subClassArray;
            try {
                if(typeName.indexOf("<") == -1){
                    // CompletableFuture 中的类没有泛型
                    typeClass = Class.forName(typeName);
                    return initClassTypeWithDefaultValue(null, typeClass, processCount);
                }
                // CompletableFuture 中的类有泛型
                typeClass = Class.forName(typeName.substring(0, typeName.indexOf("<")));
                String subTypeNames = typeName.substring((typeName.indexOf("<") + 1), (typeName.length() - 1));
                String[] subTypeNamesArray = subTypeNames.split(",");
                subClassArray = new Class[subTypeNamesArray.length];
                for(int i = 0; i < subTypeNamesArray.length; i++){
                    subClassArray[i] = Class.forName(subTypeNamesArray[i]);
                }
            } catch (ClassNotFoundException e) {
                log.warn("获取 CompletableFuture 中的泛型发生异常", e);
                return null;
            }
            ParameterizedType pt2 = ParameterizedTypeImpl.make(typeClass, subClassArray, null);
            return initClassTypeWithDefaultValue(pt2, typeClass, processCount);
        } else if (Map.class.isAssignableFrom(classType)) {
            // 已经判断了是 Map ,肯定有泛型
            if(genericType == null){
                return null;
            }
            ParameterizedType pt  = (ParameterizedType)genericType;
            Object objKey = initClassTypeWithDefaultValue(null, (Class<?>) pt.getActualTypeArguments()[0], processCount);
            Object objValue = initClassTypeWithDefaultValue(null, (Class<?>) pt.getActualTypeArguments()[1], processCount);
            Map<Object, Object> map = new HashMap<>(1);
            map.put(objKey, objValue);
            return map;
        }

        Map<String, Object> result = new HashMap<>(16);
        // 获取所有字段
        List<Field> allFields = getAllFields(null, classType);
        for (Field field2 : allFields) {
            if(String.class.isAssignableFrom(field2.getType())){
                if(field2.isAnnotationPresent(RequestParam.class)) {
                    RequestParam requestParam = field2.getAnnotation(RequestParam.class);
                    result.put(field2.getName(), requestParam.value());
                }else if(field2.isAnnotationPresent(ResponseProperty.class)){
                    ResponseProperty responseProperty = field2.getAnnotation(ResponseProperty.class);
                    StringBuilder strValue = new StringBuilder(responseProperty.value());
                    if(StringUtils.isNotBlank(responseProperty.example())){
                        strValue.append("【如: ").append(responseProperty.example()).append("】");
                    }
                    result.put(field2.getName(), strValue.toString());
                } else {
                    // 是string,但是没有注解
                    result.put(field2.getName(), initClassTypeWithDefaultValue(field2.getGenericType(), field2.getType(), processCount));
                }
            } else {
                // 检查该属性的类型是否是泛型
                if("T".equals(field2.getGenericType().getTypeName())) {
                    // 该属性的类型是泛型,从该属性所在的Class的定义中找泛型
                    ParameterizedType pt = (ParameterizedType) genericType;
                    Type[] actualTypeArguments = pt.getActualTypeArguments();
                    if(actualTypeArguments.length > 0) {
                        // 找到了
                        if(actualTypeArguments.length == 1) {
                            result.put(field2.getName(), initClassTypeWithDefaultValue(field2.getGenericType(), (Class<?>) pt.getActualTypeArguments()[0], processCount));
                        } else {
                            log.warn("{}#{}的泛型暂不支持,该属性将被忽略",classType.getName(), field2.getName());
                        }
                    } else {
                        // 找不到
                        result.put(field2.getName(), initClassTypeWithDefaultValue(field2.getGenericType(), field2.getType(), processCount));
                    }
                } else {
                    // 不是泛型
                    result.put(field2.getName(), initClassTypeWithDefaultValue(field2.getGenericType(), field2.getType(), processCount));
                }
            }
        }
        return result;
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 检查是否基础数据类型
     */
    public static boolean isBaseType(Class<?> clasz) {
        if (clasz.equals(java.lang.Integer.class) ||
                clasz.equals(java.lang.Byte.class) ||
                clasz.equals(java.lang.Long.class) ||
                clasz.equals(java.lang.Double.class) ||
                clasz.equals(java.lang.Float.class) ||
                clasz.equals(java.lang.Character.class) ||
                clasz.equals(java.lang.Short.class) ||
                clasz.equals(java.lang.Boolean.class) ||
                clasz.equals(java.lang.String.class)) {
            return true;
        }
        return false;
    }

    public static boolean isBaseType(Object o) {
        if (o instanceof java.lang.Integer ||
        o instanceof java.lang.Byte||
        o instanceof java.lang.Long ||
        o instanceof java.lang.Double ||
        o instanceof java.lang.Float ||
        o instanceof java.lang.Character ||
        o instanceof java.lang.Short ||
        o instanceof java.lang.Boolean ||
        o instanceof java.lang.String) {
            return true;
        }
        return false;
    }

    /**
     * @param fieldList
     * @param classz
     * @return
     * @Title: getAllFields
     * @Description: 获取类中的所有属性
     */
    public static List<Field> getAllFields(List<Field> fieldList, Class<?> classz) {
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
