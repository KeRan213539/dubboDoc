package top.klw8.alita.dubbodoc.annotations;

import java.lang.annotation.*;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ResponseProperty
 * @Description: 标注响应参数
 * @date 2020/2/2 13:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Inherited  // 子类可以继承父类的注解
public @interface ResponseProperty {

    /**
     * @author klw(213539@qq.com)
     * @Description: 参数名
     */
    String value();

    /**
     * @author klw(213539@qq.com)
     * @Description: 示例
     */
    String example() default "";

}
