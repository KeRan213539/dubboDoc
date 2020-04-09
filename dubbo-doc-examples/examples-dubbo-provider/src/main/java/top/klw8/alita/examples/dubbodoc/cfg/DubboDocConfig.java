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
package top.klw8.alita.examples.dubbodoc.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import top.klw8.alita.dubbodoc.annotations.EnableDubboApiDoc;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboDocConfig
 * @Description: dubbo doc config
 * @date 2020/2/27 12:51
 */
@Configuration
// Enable dubbo api doc
@EnableDubboApiDoc
@Profile("dev")
public class DubboDocConfig {
}
