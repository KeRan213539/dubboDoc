package top.klw8.alita.dubbodoc.annotations;

import java.lang.annotation.*;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: RequestBean
 * @Description: 标注dubbo接口的参数bean, 有此注解的bean才会被解析
 * @date 2020/2/3 23:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited  // 子类可以继承父类的注解
public @interface RequestBean {
}
