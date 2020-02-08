package top.klw8.alita.dubbodoc.controller.vo;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: CallDubboServiceRequestInterfacePrarm
 * @Description: 传递给duboo服务接口参数
 * @date 2019/9/20 15:02
 */
@Getter
@Setter
public class CallDubboServiceRequestInterfacePrarm {

    @ApiParam(value = "参数类型,完整包路径", required = true)
    private String prarmType;

    @ApiParam(value = "参数值", required = true)
    private Object prarmValue;

}
