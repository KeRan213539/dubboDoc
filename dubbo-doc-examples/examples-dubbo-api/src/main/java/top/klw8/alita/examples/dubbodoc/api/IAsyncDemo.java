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
 * @Description: 异步demo
 * @date 2020/3/16 9:30
 */
public interface IAsyncDemo {

    /**
     * @author klw(213539@qq.com)
     * @Description: 入参出参都是bean
     * @Date 2020/2/4 0:01
     * @param: param
     * @return top.klw8.alita.examples.dubbodoc.responses.DemoRespBean1
     */
    CompletableFuture<DemoRespBean1> demoApi1(DemoParamBean1 param1, DemoParamBean2 param2);

    /**
     * @author klw(213539@qq.com)
     * @Description: 测试Map不带泛型
     */
    CompletableFuture<Map> demoApi6();

    /**
     * @author klw(213539@qq.com)
     * @Description: 测试Map泛型为Object
     */
    CompletableFuture<Map<Object, Object>> demoApi7();

    /**
     * @author klw(213539@qq.com)
     * @Description: 测试List不带泛型
     */
    CompletableFuture<List> demoApi10();

    /**
     * @author klw(213539@qq.com)
     * @Description: 测试List泛型为Object
     */
    CompletableFuture<List<Object>> demoApi9();

    /**
     * @author klw(213539@qq.com)
     * @Description: 测试 Object
     */
    CompletableFuture<Object> demoApi8();

    /**
     * @author klw(213539@qq.com)
     * @Description: 测试 Integer
     */
    CompletableFuture<Integer> demoApi11();

}
