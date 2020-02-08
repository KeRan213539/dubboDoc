package top.klw8.alita.examples.dubbodoc;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import top.klw8.alita.dubbodoc.annotations.EnableDubboApiDoc;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ExampleApplication
 * @Description: example dubbo 提供者服务启动器
 * @date 2020/2/3 2:02
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = {"top.klw8.alita.examples.dubbodoc.api"})
@EnableDubboApiDoc
public class ExampleApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ExampleApplication.class)
                // 非 Web 应用
                .web(WebApplicationType.NONE)
                .run(args);
    }

}
