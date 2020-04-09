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
package top.klw8.alita.examples.dubbodoc.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: BaseResponse
 * @Description: BaseResponse
 * @date 2020/3/11 9:27
 */
@Getter
@Setter
@ToString
public class BaseResponse<T> implements java.io.Serializable {

    /**
     * @author klw(213539@qq.com)
     * @Description: response code
     */
    private String code;

    /**
     * @author klw(213539@qq.com)
     * @Description: response message
     */
    private String message;

    /**
     * @author klw(213539@qq.com)
     * @Description: response data
     */
    private T data;

}
