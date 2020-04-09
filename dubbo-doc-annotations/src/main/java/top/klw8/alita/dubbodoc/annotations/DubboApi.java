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
package top.klw8.alita.dubbodoc.annotations;

import java.lang.annotation.*;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboApi
 * @Description: dubbo api annotation,use to label of api
 * @date 2020/1/31 22:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DubboApi {

    /**
     * @author klw(213539@qq.com)
     * @Description: api name
     */
    String value();

    /**
     * @author klw(213539@qq.com)
     * @Description: api description(HTML tags available)
     */
    String description() default "";

    /**
     * @author klw(213539@qq.com)
     * @Description: api version
     */
    String version() default "";

    /**
     * @author klw(213539@qq.com)
     * @Description: response class description
     */
    String responseClassDescription() default "";

}
