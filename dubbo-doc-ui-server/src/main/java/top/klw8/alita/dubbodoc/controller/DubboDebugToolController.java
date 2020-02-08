package top.klw8.alita.dubbodoc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.klw8.alita.dubbodoc.controller.vo.CallDubboServiceRequest;
import top.klw8.alita.dubbodoc.controller.vo.CallDubboServiceRequestInterfacePrarm;
import top.klw8.alita.dubbodoc.utils.DubboUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboDebugToolController
 * @Description: dubbo调试工具接口
 * @date 2019/9/19 17:21
 */
@Api(tags = {"alita-restful-API--demoAPI"})
@RestController
@Slf4j
public class DubboDebugToolController {

    @ApiOperation(value = "请求dubbo接口", notes = "请求dubbo接口", httpMethod = "POST", produces = "application/json")
    @PostMapping("/requestDubbo")
    public Mono<Object> callDubboService(CallDubboServiceRequest dubboCfg, @RequestBody List<CallDubboServiceRequestInterfacePrarm> methodPrarms){
        String[] prarmTypes = null;
        Object[] prarmValues = null;
        if(CollectionUtils.isNotEmpty(methodPrarms)){
            prarmTypes = new String[methodPrarms.size()];
            prarmValues = new Object[methodPrarms.size()];
            for(int i = 0; i < methodPrarms.size(); i++){
                CallDubboServiceRequestInterfacePrarm prarm = methodPrarms.get(i);
                prarmTypes[i] = prarm.getPrarmType();
                prarmValues[i] = prarm.getPrarmValue();
            }
        }
        CompletableFuture<Object> future = DubboUtil.invoke(dubboCfg.getRegistryCenterUrl(), dubboCfg.getInterfaceClassName(),
                dubboCfg.getMethodName(), dubboCfg.isAsync(), prarmTypes, prarmValues);
        return Mono.fromFuture(future);
    }

}
