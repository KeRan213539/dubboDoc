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
 * @ClassName: CallDubboServiceRequestInterfacePrarm
 * @Description: Parameters passed to duboo service api
 * @date 2019/9/20 15:02
 */
@Getter
@Setter
public class CallDubboServiceRequestInterfacePrarm {

    @ApiParam(value = "Parameter type (full package path), such as: java.lang.String", required = true)
    private String prarmType;

    @ApiParam(value = "Parameter value", required = true)
    private Object prarmValue;

}
