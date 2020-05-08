/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.dubbodoc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import top.klw8.alita.dubbodoc.editor.CustomDateEditor;
import top.klw8.alita.dubbodoc.utils.DubboUtil;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboDocController
 * @Description: dubbo doc ui server api
 * @date 2019/9/19 17:21
 */
@Api(tags = {"alita-restful-API--demoAPI"})
@RestController
@Slf4j
@RequestMapping("/api")
public class DubboDocController {

    private static final SimplePropertyPreFilter CLASS_NAME_PRE_FILTER = new SimplePropertyPreFilter(HashMap.class);
    static {
        // Remove the "class" attribute from the returned result
        CLASS_NAME_PRE_FILTER.getExcludes().add("class");
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: retries for dubbo provider
     */
    @Value("${dubbo.consumer.retries:2}")
    private int retries;

    /**
     * @author klw(213539@qq.com)
     * @Description: timeout
     */
    @Value("${dubbo.consumer.timeout:1000}")
    private int timeout;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
        binder.registerCustomEditor(LocalDate.class, new CustomLocalDateEditor());
        binder.registerCustomEditor(LocalDateTime.class, new CustomLocalDateTimeEditor());
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: Set timeout and retries for {@link top.klw8.alita.dubbodoc.utils.DubboUtil}
     * @Date 2020/4/3 12:32
     * @param:
     * @return void
     */
    @PostConstruct
    public void setRetriesAndTimeout(){
        DubboUtil.setRetriesAndTimeout(retries, timeout);
    }

    @ApiOperation(value = "request dubbo api", notes = "request dubbo api", httpMethod = "POST", produces = "application/json")
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
                Object prarmValue = prarm.getPrarmValue();
                if(prarmValue != null && prarmValue instanceof String && StringUtils.isBlank((String) prarmValue)){
                    prarmValues[i] = null;
                } else {
                    this.emptyString2Null(prarm.getPrarmValue());
                    prarmValues[i] = prarm.getPrarmValue();
                }
            }
        }
        CompletableFuture<Object> future = DubboUtil.invoke(dubboCfg.getRegistryCenterUrl(), dubboCfg.getInterfaceClassName(),
                dubboCfg.getMethodName(), dubboCfg.isAsync(), prarmTypes, prarmValues);
        return Mono.fromFuture(future).map( o -> JSON.toJSONString(o, CLASS_NAME_PRE_FILTER));
    }

    private void emptyString2Null(Object prarmValue){
        if(null != prarmValue && prarmValue instanceof Map){
            Map<String, Object> tempMap = (Map<String, Object>) prarmValue;
            tempMap.forEach((k, v) -> {
                if(v != null && v instanceof String && StringUtils.isBlank((String)v)) {
                    tempMap.put(k, null);
                } else {
                    this.emptyString2Null(v);
                }
            });
        }
    }

    @ApiOperation(value = "Get basic information of all modules, excluding API parameter information", notes = "Get basic information of all modules, excluding API parameter information", httpMethod = "GET", produces = "application/json")
    @GetMapping("/apiModuleList")
    public Mono<String> apiModuleList(ApiInfoRequest apiInfoRequest){
        CallDubboServiceRequest req = new CallDubboServiceRequest();
        req.setRegistryCenterUrl("dubbo://" + apiInfoRequest.getDubboIp() + ":" + apiInfoRequest.getDubboPort());
        req.setInterfaceClassName("top.klw8.alita.dubbodoc.provider.IDubboDocProvider");
        req.setMethodName("apiModuleList");
        req.setAsync(false);
        return callDubboService(req, null);
    }

    @ApiOperation(value = "Get the parameter information of the specified API", notes = "Get the parameter information of the specified API", httpMethod = "GET", produces = "application/json")
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
