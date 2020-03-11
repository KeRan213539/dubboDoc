package top.klw8.alita.examples.dubbodoc.api;

import top.klw8.alita.examples.dubbodoc.params.DemoParamBean1;
import top.klw8.alita.examples.dubbodoc.params.DemoParamBean2;
import top.klw8.alita.examples.dubbodoc.responses.BaseResponse;
import top.klw8.alita.examples.dubbodoc.responses.DemoRespBean1;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IDemoApi
 * @Description: demo api 接口
 * @date 2020/2/2 23:41
 */
public interface IDemoApi {

    /**
     * @author klw(213539@qq.com)
     * @Description: 入参出参都是bean
     * @Date 2020/2/4 0:01
     * @param: param
     * @return top.klw8.alita.examples.dubbodoc.responses.DemoRespBean1
     */
    DemoRespBean1 demoApi1(DemoParamBean1 param1, DemoParamBean2 param2);

    /**
     * @author klw(213539@qq.com)
     * @Description: 入参出参都是String
     * @Date 2020/2/4 0:02
     * @param: prarm1
     * @param: prarm2
     * @return java.lang.String
     */
    String demoApi2(String prarm1, String prarm2);

    /**
     * @author klw(213539@qq.com)
     * @Description: 没有 dubbo doc 注解的方法,不会生成文档
     * @Date 2020/2/4 0:22
     * @param: prarm1
     * @return java.lang.String
     */
    String demoApi3(String prarm1);

    /**
     * @author klw(213539@qq.com)
     * @Description: 有 dubbo doc 注解的无参方法
     * @Date 2020/2/4 0:02
     * @param:
     * @return java.lang.String
     */
    String demoApi4();

    /**
     * @author klw(213539@qq.com)
     * @Description: 测试响应中使用泛型
     */
    BaseResponse<DemoRespBean1> demoApi5();

}
