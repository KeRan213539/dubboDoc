/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * @date 2018-09-14 09:54:14
 */
@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfig {

    public static final String VERSION = "1.0.0";

    protected ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Dubbo debug tool").description("&nbsp;")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("").version(VERSION)
                .contact(new Contact("klw", "", "213539@qq.com")).build();
    }

    @Bean
    public Docket customImplementationDevHelper() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("【Dubbo debug tool】")
                .select()
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .apiInfo(apiInfo());
    }
    
}
