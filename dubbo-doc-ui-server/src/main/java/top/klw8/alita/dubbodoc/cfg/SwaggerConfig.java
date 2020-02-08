package top.klw8.alita.dubbodoc.cfg;


import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

/**
 * @ClassName: SwaggerConfig
 * @Description: SwaggerConfig
 * @author klw
 * @date 2018年9月14日 09:54:14
 */
@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfig {

    public static final String VERSION = "1.0.0";

    protected ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Dubbo调试工具API").description("&nbsp;")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("").version(VERSION)
                .contact(new Contact("klw", "", "213539@qq.com")).build();
    }

    @Bean
    public Docket customImplementationDevHelper() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("【Dubbo调试工具】")
                .select()
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .apiInfo(apiInfo());
    }
    
}
