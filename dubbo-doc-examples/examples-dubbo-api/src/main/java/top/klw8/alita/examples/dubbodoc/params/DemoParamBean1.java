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

import java.util.List;
import java.util.Map;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoParamBean1
 * demo request bean
 * @date 2020/2/2 23:42
 */
@Getter
@Setter
public class DemoParamBean1 {

    @RequestParam("Name")
    private String name;

    @RequestParam("Age")
    private Integer age;

    private Boolean man;

    @RequestParam("====subBean")
    private List<DemoParamBean1SubBean1> subBean;

    @RequestParam("Map")
    private Map<String, DemoParamBean1SubBean1> subBean2;

    @RequestParam("Array")
    private String[] strArray;

    @RequestParam("Array 2")
    private DemoParamBean1SubBean1[] strArray2;

    @RequestParam("Enumeration for test")
    private TestEnum testEnum;

    private DemoParamBean1SubBean1 subBean3;

    @RequestParam("Map without generics")
    private Map map1;

    @RequestParam("Map generic with Object")
    private Map<Object, Object> map2;

    @RequestParam("List without generics")
    private List list1;

    @RequestParam("List generic with Object")
    private List<Object> list2;

    @RequestParam("Object")
    private Object obj1;

}
