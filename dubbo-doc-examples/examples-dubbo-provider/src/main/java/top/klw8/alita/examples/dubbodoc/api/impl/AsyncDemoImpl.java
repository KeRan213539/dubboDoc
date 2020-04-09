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
package top.klw8.alita.examples.dubbodoc.api.impl;

import org.apache.dubbo.config.annotation.Service;
import top.klw8.alita.dubbodoc.annotations.DubboApi;
import top.klw8.alita.dubbodoc.annotations.DubboApiModule;
import top.klw8.alita.examples.dubbodoc.api.IAsyncDemo;
import top.klw8.alita.examples.dubbodoc.params.DemoParamBean1;
import top.klw8.alita.examples.dubbodoc.params.DemoParamBean2;
import top.klw8.alita.examples.dubbodoc.responses.DemoRespBean1;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AsyncDemoImpl
 * @Description: Asynchronous demo implementation
 * @date 2020/3/16 9:37
 */
@Service(async = true)
@DubboApiModule(value = "Asynchronous demo", apiInterface = IAsyncDemo.class)
public class AsyncDemoImpl implements IAsyncDemo {

    public static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 40 * 3);

    @DubboApi("request and response parameters are beans")
    @Override
    public CompletableFuture<DemoRespBean1> demoApi1(DemoParamBean1 param1, DemoParamBean2 param2) {
        DemoRespBean1 result = new DemoRespBean1();
        result.setCode("123456789");
        result.setMessage("called demoApi1 msg1");
        result.setMessage2("called demoApi1 msg2");
        return CompletableFuture.supplyAsync(() -> result, executor);
    }

    @Override
    @DubboApi(value = "Map without generics", responseClassDescription="Map without generics")
    public CompletableFuture<Map> demoApi6() {
        return null;
    }

    @Override
    @DubboApi(value = "Map generic with Object", responseClassDescription="Map generic with Object")
    public CompletableFuture<Map<Object, Object>> demoApi7() {
        return null;
    }

    @Override
    @DubboApi(value = "List without generics", responseClassDescription="List without generics")
    public CompletableFuture<List> demoApi10() {
        return null;
    }

    @Override
    @DubboApi(value = "List generic with Object", responseClassDescription="List generic with Object")
    public CompletableFuture<List<Object>> demoApi9() {
        return null;
    }

    @Override
    @DubboApi(value = "Object", responseClassDescription="Object")
    public CompletableFuture<Object> demoApi8() {
        return null;
    }

    @Override
    @DubboApi(value = "Integer", responseClassDescription="Integer")
    public CompletableFuture<Integer> demoApi11() {
        return null;
    }
}
