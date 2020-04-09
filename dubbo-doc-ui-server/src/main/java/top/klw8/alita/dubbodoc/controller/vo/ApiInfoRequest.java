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
 * @ClassName: ApiModuleListRequest
 * @Description: Obtain the API module list and the request parameters of the API parameter information interface
 * @date 2020/3/1 18:48
 */
@Getter
@Setter
public class ApiInfoRequest {

    @ApiParam(value = "IP of Dubbo provider", required = true)
    private String dubboIp;

    @ApiParam(value = "Port of Dubbo provider", required = true)
    private String dubboPort;

    @ApiParam(value = "API full name (interface class full name. Method name), which must be passed when getting API parameter information")
    private String apiName;

}
