package top.klw8.alita.examples.dubbodoc.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import top.klw8.alita.dubbodoc.annotations.DubboApi;
import top.klw8.alita.dubbodoc.annotations.DubboApiModule;
import top.klw8.alita.dubbodoc.annotations.RequestParam;
import top.klw8.alita.examples.dubbodoc.api.IDemoApi;
import top.klw8.alita.examples.dubbodoc.params.DemoParamBean1;
import top.klw8.alita.examples.dubbodoc.params.DemoParamBean2;
import top.klw8.alita.examples.dubbodoc.params.DemoRespBean1;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoApiImpl
 * @Description: demo api 接口实现
 * @date 2020/2/3 2:03
 */
@Slf4j
@Service(async=true)
@DubboApiModule(value = "demo", apiInterface = IDemoApi.class)
public class DemoApiImpl implements IDemoApi {

    @DubboApi("入参出参都是bean")
    @Override
    public DemoRespBean1 demoApi1(DemoParamBean1 param1, DemoParamBean2 param2) {
        log.info(" called demoApi1");
        return null;
    }

    @DubboApi(value = "入参出参都是String", responseClassDescription="一串字符串")
    @Override
    public String demoApi2(@RequestParam("参数1") String prarm1, String prarm2) {
        log.info(" called demoApi2");
        return "demoApi2";
    }

    @Override
    public String demoApi3(String prarm1) {
        return null;
    }

    @DubboApi(value = "无参接口", responseClassDescription="一串字符串")
    @Override
    public String demoApi4() {
        return null;
    }
}
