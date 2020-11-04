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
package top.klw8.alita.examples.dubbodoc.params;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.dubbodoc.annotations.RequestParam;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoParamBean2
 * demo request bean 2
 * @date 2020/2/2 23:42
 */
@Getter
@Setter
public class DemoParamBean2 {

    @RequestParam(value = "Name 2", allowableValues = {"San Zhang", "Si Li"})
    private String name2;

    @RequestParam("Age 2")
    private Double age2;

}
