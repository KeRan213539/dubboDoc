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
package top.klw8.alita.dubbodoc.core.beans;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: HtmlTypeEnum
 * @Description: html type enum
 * @date 2020/2/28 17:41
 */
public enum HtmlTypeEnum {

    /**
     * @author klw(213539@qq.com)
     * @Description: Textbox
     */
    TEXT,

    /**
     * @author klw(213539@qq.com)
     * @Description: Textbox, This type will be converted to byte before calling dubbo API
     */
    TEXT_BYTE,

    /**
     * @author klw(213539@qq.com)
     * @Description: Textbox, will be limited to one character. This type will be converted to char before calling dubbo API
     */
    TEXT_CHAR,

    /**
     * @author klw(213539@qq.com)
     * @Description: Numeric input box, integer
     */
    NUMBER_INTEGER,

    /**
     * @author klw(213539@qq.com)
     * @Description: Numeric input box, decimal
     */
    NUMBER_DECIMAL,

    /**
     * @author klw(213539@qq.com)
     * @Description: Drop down selection box
     */
    SELECT,

    /**
     * @author klw(213539@qq.com)
     * @Description: Text area, which is generally used to show the JSON string of the Java Bean contained in the parameter
     */
    TEXT_AREA,
    ;

}
