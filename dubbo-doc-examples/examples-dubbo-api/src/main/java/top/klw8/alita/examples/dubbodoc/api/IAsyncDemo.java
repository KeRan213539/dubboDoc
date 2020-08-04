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
package top.klw8.alita.examples.dubbodoc.api;

import top.klw8.alita.examples.dubbodoc.params.DemoParamBean1;
import top.klw8.alita.examples.dubbodoc.params.DemoParamBean2;
import top.klw8.alita.examples.dubbodoc.responses.DemoRespBean1;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IAsyncDemo
 * @Description: asynchronous demo
 * @date 2020/3/16 9:30
 */
public interface IAsyncDemo {

    /**
     * @author klw(213539@qq.com)
     * @Description: request and response parameters are beans
     * @Date 2020/2/4 0:01
     * @param: param
     * @return top.klw8.alita.examples.dubbodoc.responses.DemoRespBean1
     */
    CompletableFuture<DemoRespBean1> demoApi1(DemoParamBean1 param1, DemoParamBean2 param2);

    /**
     * @author klw(213539@qq.com)
     * @Description: Map without generics
     */
    CompletableFuture<Map> demoApi6();

    /**
     * @author klw(213539@qq.com)
     * @Description: Map generic with Object
     */
    CompletableFuture<Map<Object, Object>> demoApi7();

    /**
     * @author klw(213539@qq.com)
     * @Description: List without generics
     */
    CompletableFuture<List> demoApi10();

    /**
     * @author klw(213539@qq.com)
     * @Description: List generic with Object
     */
    CompletableFuture<List<Object>> demoApi9();

    /**
     * @author klw(213539@qq.com)
     * @Description: Object
     */
    CompletableFuture<Object> demoApi8();

    /**
     * @author klw(213539@qq.com)
     * @Description: Integer
     */
    CompletableFuture<Integer> demoApi11();


    /**
     * @author klw(213539@qq.com)
     * @Description: many generics
     * @Date 2020/7/30 17:02
     * @param:
     * @return java.util.concurrent.CompletableFuture<java.util.List<java.util.List<java.lang.String>>>
     */
    CompletableFuture<List<List<String>>> demoApi12();

}
