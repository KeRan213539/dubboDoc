package top.klw8.alita.examples.dubbodoc.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import top.klw8.alita.dubbodoc.annotations.DubboApi;
import top.klw8.alita.dubbodoc.annotations.DubboApiModule;
import top.klw8.alita.dubbodoc.annotations.RequestParam;
import top.klw8.alita.examples.dubbodoc.api.ISyncDemo;
import top.klw8.alita.examples.dubbodoc.params.DemoParamBean1;
import top.klw8.alita.examples.dubbodoc.params.DemoParamBean2;
import top.klw8.alita.examples.dubbodoc.responses.BaseResponse;
import top.klw8.alita.examples.dubbodoc.responses.DemoRespBean1;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoApiImpl
 * @Description: 同步demo实现
 * @date 2020/2/3 2:03
 */
@Slf4j
@Service
@DubboApiModule(value = "同步demo", apiInterface = ISyncDemo.class)
public class SyncDemoImpl implements ISyncDemo {

    @DubboApi("入参出参都是bean")
    @Override
    public DemoRespBean1 demoApi1(DemoParamBean1 param1, DemoParamBean2 param2) {
        log.info("called demoApi1");
        DemoRespBean1 result = new DemoRespBean1();
        result.setCode("123456789");
        result.setMessage("called demoApi1");
        result.setMessage2("demoApi1 被打了");
        return result;
    }

    @DubboApi(value = "入参出参都是String", responseClassDescription="一串字符串")
    @Override
    public String demoApi2(@RequestParam(value = "参数1", required = true) String prarm1, String prarm2) {
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
        return "asdfasdfsdafds";
    }

    @DubboApi(value = "响应带泛型", responseClassDescription="响应带泛型")
    public BaseResponse<DemoRespBean1> demoApi5(){
        BaseResponse<DemoRespBean1> response = new BaseResponse<>();
        DemoRespBean1 responseData = new DemoRespBean1();
        responseData.setCode("2222");
        responseData.setMessage("msg1");
        responseData.setMessage2("msg2");
        response.setData(responseData);
        response.setCode("1111");
        response.setMessage("msg");
        return response;
    }
}
