package top.klw8.alita.dubbodoc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import top.klw8.alita.dubbodoc.controller.vo.ApiInfoRequest;
import top.klw8.alita.dubbodoc.controller.vo.CallDubboServiceRequest;
import top.klw8.alita.dubbodoc.controller.vo.CallDubboServiceRequestInterfacePrarm;
import top.klw8.alita.dubbodoc.editor.CustomLocalDateEditor;
import top.klw8.alita.dubbodoc.editor.CustomLocalDateTimeEditor;
import top.klw8.alita.dubbodoc.editor.MyCustomDateEditor;
import top.klw8.alita.dubbodoc.utils.DubboUtil;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
        // 去除返回结果中的 class 属性
        CLASS_NAME_PRE_FILTER.getExcludes().add("class");
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 默认重试次数
     */
    @Value("${dubbo.consumer.retries:2}")
    private int retries;

    /**
     * @author klw(213539@qq.com)
     * @Description: 默认超时
     */
    @Value("${dubbo.consumer.timeout:1000}")
    private int timeout;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new MyCustomDateEditor());
        binder.registerCustomEditor(LocalDate.class, new CustomLocalDateEditor());
        binder.registerCustomEditor(LocalDateTime.class, new CustomLocalDateTimeEditor());
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 为 DubboUtil 设置超时和重试次数
     * @Date 2020/4/3 12:32
     * @param:
     * @return void
     */
    @PostConstruct
    public void setRetriesAndTimeout(){
        DubboUtil.setRetriesAndTimeout(retries, timeout);
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
