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
package top.klw8.alita.dubbodoc.controller.vo;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;


/**
 * @author klw(213539 @ qq.com)
 * @ClassName: CallDubboServiceRequest
 * @Description: Call Dubbo api to request parameters
 * @date 2019/9/20 9:12
 */
@Getter
@Setter
public class CallDubboServiceRequest {

    @ApiParam(value = "Address of registration center, such as: nacos://127.0.0.1:8848", required = true)
    private String registryCenterUrl;

    @ApiParam(value = "Dubbo interface full package path", required = true)
    private String interfaceClassName;

    @ApiParam(value = "Method name of the service", required = true)
    private String methodName;

    @ApiParam(value = "Whether to call asynchronously, false by default")
    private boolean async = false;

}
