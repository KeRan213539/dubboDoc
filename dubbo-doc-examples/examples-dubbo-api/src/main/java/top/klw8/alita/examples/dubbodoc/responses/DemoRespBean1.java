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
package top.klw8.alita.examples.dubbodoc.responses;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.dubbodoc.annotations.ResponseProperty;

import java.util.List;
import java.util.Map;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoRespBean1
 * @Description: demo response bean 1
 * @date 2020/2/3 0:00
 */
@Getter
@Setter
public class DemoRespBean1 {

    private String code;

    @ResponseProperty("Response message")
    private String message;

    @ResponseProperty(value = "Response message 2", example = "This is response message 2")
    private String message2;

    @ResponseProperty("Map without generics")
    private Map map1;

    @ResponseProperty("Map generic with Object")
    private Map<Object, Object> map2;

    @ResponseProperty("List without generics")
    private List list1;

    @ResponseProperty("List generic with Object")
    private List<Object> list2;

    @ResponseProperty("Object")
    private Object obj1;

}
