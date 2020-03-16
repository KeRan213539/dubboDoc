package top.klw8.alita.dubbodoc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import top.klw8.alita.dubbodoc.controller.vo.ApiInfoRequest;
import top.klw8.alita.dubbodoc.controller.vo.CallDubboServiceRequest;
import top.klw8.alita.dubbodoc.controller.vo.CallDubboServiceRequestInterfacePrarm;
import top.klw8.alita.dubbodoc.utils.DubboUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboDocController
 * @Description: dubbo doc接口
 * @date 2019/9/19 17:21
 */
@Api(tags = {"alita-restful-API--demoAPI"})
@RestController
@Slf4j
@RequestMapping("/api")
public class DubboDocController {

    private static final SimplePropertyPreFilter CLASS_NAME_PRE_FILTER = new SimplePropertyPreFilter(HashMap.class);
    static {
        CLASS_NAME_PRE_FILTER.getExcludes().add("class");
    }

    @ApiOperation(value = "请求dubbo接口", notes = "请求dubbo接口", httpMethod = "POST", produces = "application/json")
    @PostMapping("/requestDubbo")
    public Mono<String> callDubboService(CallDubboServiceRequest dubboCfg, @RequestBody List<CallDubboServiceRequestInterfacePrarm> methodPrarms){
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
        return Mono.fromFuture(future).map( o -> {
            // 去除dubbo泛化调用附加的"class"属性
            return JSON.toJSONString(o, CLASS_NAME_PRE_FILTER);
        });
    }

    @ApiOperation(value = "获取全部Module的基本信息,不包含api参数信息", notes = "获取全部Module的基本信息,不包含api参数信息", httpMethod = "GET", produces = "application/json")
    @GetMapping("/apiModuleList")
    public Mono<String> apiModuleList(ApiInfoRequest apiInfoRequest){
        CallDubboServiceRequest req = new CallDubboServiceRequest();
        req.setRegistryCenterUrl("dubbo://" + apiInfoRequest.getDubboIp() + ":" + apiInfoRequest.getDubboPort());
        req.setInterfaceClassName("top.klw8.alita.dubbodoc.provider.IDubboDocProvider");
        req.setMethodName("apiModuleList");
        req.setAsync(false);
        return callDubboService(req, null);
    }

    @ApiOperation(value = "获取指定API的参数信息", notes = "获取指定API的参数信息", httpMethod = "GET", produces = "application/json")
    @GetMapping("/apiParamsResp")
    public Mono<String> apiParamsResp(ApiInfoRequest apiInfoRequest){
        CallDubboServiceRequest req = new CallDubboServiceRequest();
        req.setRegistryCenterUrl("dubbo://" + apiInfoRequest.getDubboIp() + ":" + apiInfoRequest.getDubboPort());
        req.setInterfaceClassName("top.klw8.alita.dubbodoc.provider.IDubboDocProvider");
        req.setMethodName("apiParamsResponseInfo");
        req.setAsync(false);

        List<CallDubboServiceRequestInterfacePrarm> methodPrarms = new ArrayList<>(1);
        CallDubboServiceRequestInterfacePrarm prarm = new CallDubboServiceRequestInterfacePrarm();
        prarm.setPrarmType(String.class.getName());
        prarm.setPrarmValue(apiInfoRequest.getApiName());
        methodPrarms.add(prarm);
        return callDubboService(req, methodPrarms);
    }
}
