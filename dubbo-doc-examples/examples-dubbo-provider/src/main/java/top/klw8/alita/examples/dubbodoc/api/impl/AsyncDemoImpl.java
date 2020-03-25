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
 * @Description: 异步实现
 * @date 2020/3/16 9:37
 */
@Service(async = true)
@DubboApiModule(value = "异步demo", apiInterface = IAsyncDemo.class)
public class AsyncDemoImpl implements IAsyncDemo {

    public static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 40 * 3);

    @DubboApi("入参出参都是bean")
    @Override
    public CompletableFuture<DemoRespBean1> demoApi1(DemoParamBean1 param1, DemoParamBean2 param2) {
        DemoRespBean1 result = new DemoRespBean1();
        result.setCode("123456789");
        result.setMessage("called demoApi1");
        result.setMessage2("demoApi1 被打了");
        return CompletableFuture.supplyAsync(() -> result, executor);
    }

    @Override
    @DubboApi(value = "测试Map不带泛型", responseClassDescription="Map不带泛型")
    public CompletableFuture<Map> demoApi6() {
        return null;
    }

    @Override
    @DubboApi(value = "测试Map泛型为Object", responseClassDescription="Map泛型为Object")
    public CompletableFuture<Map<Object, Object>> demoApi7() {
        return null;
    }

    @Override
    @DubboApi(value = "测试List不带泛型", responseClassDescription="List不带泛型")
    public CompletableFuture<List> demoApi10() {
        return null;
    }

    @Override
    @DubboApi(value = "测试List泛型为Object", responseClassDescription="List泛型为Object")
    public CompletableFuture<List<Object>> demoApi9() {
        return null;
    }

    @Override
    @DubboApi(value = "测试 Object", responseClassDescription="Object")
    public CompletableFuture<Object> demoApi8() {
        return null;
    }

    @Override
    @DubboApi(value = "测试 Integer", responseClassDescription="Integer")
    public CompletableFuture<Integer> demoApi11() {
        return null;
    }
}
