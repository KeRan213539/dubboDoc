package top.klw8.alita.dubbodoc.annotations;

import java.lang.annotation.*;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboApi
 * @Description: dubbo 接口注解,用于标注一个接口的用途
 * @date 2020/1/31 22:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DubboApi {

    /**
     * @author klw(213539@qq.com)
     * @Description: 接口名称
     */
    String value();

    /**
     * @author klw(213539@qq.com)
     * @Description: 版本
     */
    String version() default "";

    /**
     * @author klw(213539@qq.com)
     * @Description: 响应的数据的描述
     */
    String responseClassDescription() default "";

}
