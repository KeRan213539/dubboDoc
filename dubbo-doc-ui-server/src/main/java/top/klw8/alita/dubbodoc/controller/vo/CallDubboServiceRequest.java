package top.klw8.alita.dubbodoc.controller.vo;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;


/**
 * @author klw(213539 @ qq.com)
 * @ClassName: CallDubboServiceRequest
 * @Description: 调用dubbo接口请求参数
 * @date 2019/9/20 9:12
 */
@Getter
@Setter
public class CallDubboServiceRequest {

    @ApiParam(value = "注册中心地址,如: nacos://127.0.0.1:8848", required = true)
    private String registryCenterUrl;

    @ApiParam(value = "服务接口完整包路径", required = true)
    private String interfaceClassName;

    @ApiParam(value = "服务的方法名", required = true)
    private String methodName;

    @ApiParam(value = "是否异步调用,默认false")
    private boolean async = false;

}
