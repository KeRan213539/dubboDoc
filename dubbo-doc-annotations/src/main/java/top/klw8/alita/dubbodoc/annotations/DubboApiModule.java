package top.klw8.alita.dubbodoc.annotations;

import java.lang.annotation.*;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboApi
 * @Description: dubbo 接口模块注解,用于标注一个接口类模块的用途
 * @date 2020/1/31 22:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface DubboApiModule {

    /**
     * @author klw(213539@qq.com)
     * @Description: 模块名称
     */
    String value();

    /**
     * @author klw(213539@qq.com)
     * @Description: dubbo api 的接口类
     */
    Class<?> apiInterface();

    /**
     * @author klw(213539@qq.com)
     * @Description: 版本
     */
    String version() default "";

}
