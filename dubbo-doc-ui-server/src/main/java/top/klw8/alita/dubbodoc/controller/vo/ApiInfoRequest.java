package top.klw8.alita.dubbodoc.controller.vo;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ApiModuleListRequest
 * @Description: 获取api module列表, 获取接api参数信息接口的请求参数
 * @date 2020/3/1 18:48
 */
@Getter
@Setter
public class ApiInfoRequest {

    @ApiParam(value = "dubbo提供者的IP", required = true)
    private String dubboIp;

    @ApiParam(value = "dubbo提供者的端口", required = true)
    private String dubboPort;

    @ApiParam(value = "api完整名称(接口Class完整名.方法名),获取接api参数信息时必传")
    private String apiName;

}
