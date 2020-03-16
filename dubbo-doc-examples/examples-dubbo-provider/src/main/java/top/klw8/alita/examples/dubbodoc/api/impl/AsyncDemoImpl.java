package top.klw8.alita.examples.dubbodoc.api.impl;

import org.apache.dubbo.config.annotation.Service;
import top.klw8.alita.dubbodoc.annotations.DubboApi;
import top.klw8.alita.dubbodoc.annotations.DubboApiModule;
import top.klw8.alita.examples.dubbodoc.api.IAsyncDemo;
import top.klw8.alita.examples.dubbodoc.params.DemoParamBean1;
import top.klw8.alita.examples.dubbodoc.params.DemoParamBean2;
import top.klw8.alita.examples.dubbodoc.responses.DemoRespBean1;

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
}
