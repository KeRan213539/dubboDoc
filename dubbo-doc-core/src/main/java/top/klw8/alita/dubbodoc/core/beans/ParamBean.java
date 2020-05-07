/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.dubbodoc.core.beans;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ParamBean
 * @Description: Parameter bean corresponding to {@link top.klw8.alita.dubbodoc.annotations.RequestParam}, for caching
 * @date 2020/2/27 18:33
 */
@Getter
@Setter
public class ParamBean {

    /**
     * @author klw(213539@qq.com)
     * @Description: parameter name
     */
    private String name;

    /**
     * @author klw(213539@qq.com)
     * @Description: parameter name, for display
     */
    private String nameCh;

    /**
     * @author klw(213539@qq.com)
     * @Description: required
     */
    private Boolean required;

    /**
     * @author klw(213539@qq.com)
     * @Description: description
     */
    private String description;

    /**
     * @author klw(213539@qq.com)
     * @Description: example
     */
    private String example;

    /**
     * @author klw(213539@qq.com)
     * @Description: default value
     */
    private String defaultValue;

    /**
     * @author klw(213539@qq.com)
     * @Description: java type of parameter
     */
    private String javaType;

    /**
     * @author klw(213539@qq.com)
     * @Description: What HTML elements should this parameter display
     */
    private HtmlTypeEnum htmlType;

    /**
     * @author klw(213539@qq.com)
     * @Description: allowed values
     */
    private String[] allowableValues;

    /**
     * @author klw(213539@qq.com)
     * @Description: If the parameter in a request bean is not a basic data type,
     * the {@link subParams} will have a value.
     * Because the HTML form is not easy to display this parameter,
     * it will be displayed as a text area, and the JSON string of this parameter will be filled in
     */
    @JSONField(serialize = false)
    private List<ParamBean> subParams;

    /**
     * @author klw(213539@qq.com)
     * @Description: JSON string corresponding to {@link subParams}
     */
    private String subParamsJson;

}
