package top.klw8.alita.examples.dubbodoc;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ExampleApplication
 * @Description: example dubbo 提供者服务启动器
 * @date 2020/2/3 2:02
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = {"top.klw8.alita.examples.dubbodoc.api"})
public class ExampleApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ExampleApplication.class)
                // 非 Web 应用
                .web(WebApplicationType.NONE)
                .run(args);

//        List<Field> fieldList = ClassTypeUtils.getAllFields(null, TestBean.class);
//        for(Field field : fieldList) {
//            System.out.println(ClassTypeUtils.calss2Json(field, field.getType()));
//        }


//        SerializerFeature[] features = {
//                //是否输出值为null的字段,默认为false。
//                SerializerFeature.WriteMapNullValue,
//                //List字段如果为null,输出为[],而非null
//                SerializerFeature.WriteNullListAsEmpty,
//                //字符类型字段如果为null,输出为"",而非null
//                SerializerFeature.WriteNullStringAsEmpty,
//                //Boolean字段如果为null,输出为false,而非null
//                SerializerFeature.WriteNullBooleanAsFalse,
//                // 数字为null输出0
//                SerializerFeature.WriteNullNumberAsZero,
//                //消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
//                SerializerFeature.DisableCircularReferenceDetect,
//        };

//        System.out.println(JSON.toJSONString(new DemoParamBean2()));
//        System.out.println(JSON.toJSONString(new DemoParamBean2(), features));

//        List<String> list = new ArrayList<>();
//
//        System.out.println(List.class.isAssignableFrom(list.getClass()));
//        System.out.println(Collection.class.isAssignableFrom(list.getClass()));
//        System.out.println(List.class.isInstance(list));
//        System.out.println(Collection.class.isInstance(list.getClass()));
//
//        String[] strArr = new String[2];
//
//        System.out.println(strArr.getClass().getComponentType());
//        System.out.println(list.getClass().getComponentType());

    }

}
