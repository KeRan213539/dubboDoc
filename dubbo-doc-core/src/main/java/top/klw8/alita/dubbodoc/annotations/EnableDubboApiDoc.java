package top.klw8.alita.dubbodoc.annotations;

import org.springframework.context.annotation.Import;
import top.klw8.alita.dubbodoc.core.DubboDocAnnotationScanner;

import java.lang.annotation.*;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: EnableDubboApiDoc
 * @Description: 开启dubbo api doc
 * @date 2020/2/2 18:21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited  // 子类可以继承父类的注解
@Import({DubboDocAnnotationScanner.class})
public @interface EnableDubboApiDoc {
}
