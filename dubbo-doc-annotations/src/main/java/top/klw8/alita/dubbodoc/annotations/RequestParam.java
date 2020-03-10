package top.klw8.alita.dubbodoc.annotations;

import java.lang.annotation.*;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: RequestParam
 * @Description: 标注请求参数
 * @date 2020/2/2 13:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Documented
@Inherited  // 子类可以继承父类的注解
public @interface RequestParam {

    /**
     * @author klw(213539@qq.com)
     * @Description: 参数名(可使用html标签)
     */
    String value();

    /**
     * @author klw(213539@qq.com)
     * @Description: 是否必须
     */
    boolean required() default false;

    /**
     * @author klw(213539@qq.com)
     * @Description: 描述(可使用html标签)
     */
    String description() default "";

    /**
     * @author klw(213539@qq.com)
     * @Description: 示例(可使用html标签)
     */
    String example() default "";

    /**
     * @author klw(213539@qq.com)
     * @Description: 参数默认值
     */
    String defaultValue() default "";

    /**
     * @author klw(213539@qq.com)
     * @Description: 允许的值,设置该属性后界面上将对参数生成下拉列表 <br />
     * 注:使用该属性后将生成下拉选择框<br />
     * 1. boolean 类型的参数不用设置该属性,将默认生成 true/false 的下拉列表<br />
     * 2. 枚举类型的参数会自动生成下拉列表,如果不想开放全部的枚举值,可以单独设置此属性.
     */
    String[] allowableValues() default {};

}
