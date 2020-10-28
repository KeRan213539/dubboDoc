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

import java.util.List;
import java.util.Map;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoApiImpl
 * @Description: Synchronous demo implementation
 * @date 2020/2/3 2:03
 */
@Slf4j
@Service
@DubboApiModule(value = "Synchronous demo", apiInterface = ISyncDemo.class)
public class SyncDemoImpl implements ISyncDemo {

    @DubboApi("request and response parameters are beans")
    @Override
    public DemoRespBean1 demoApi1(DemoParamBean1 param1, DemoParamBean2 param2) {
        log.info("called demoApi1");
        DemoRespBean1 result = new DemoRespBean1();
        result.setCode("123456789");
        result.setMessage("called demoApi1 msg1");
        result.setMessage2("called demoApi1 msg2");
        return result;
    }

    @DubboApi(value = "request and response parameters are Strings", responseClassDescription="A string")
    @Override
    public String demoApi2(@RequestParam(value = "Parameter 1", required = true) String prarm1, String prarm2) {
        log.info(" called demoApi2");
        return "demoApi2";
    }

    @Override
    public String demoApi3(String prarm1) {
        return null;
    }

    @DubboApi(value = "Nonparametric method with Dubbo doc annotation", responseClassDescription="A string")
    @Override
    public String demoApi4() {
        return "asdfasdfsdafds";
    }

    @DubboApi(value = " Use generics in response", responseClassDescription=" Use generics in response")
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

    @Override
    @DubboApi(value = "Map without generics", responseClassDescription="Map without generics")
    public Map demoApi6() {
        return null;
    }

    @Override
    @DubboApi(value = "Map generic with Object", responseClassDescription="Map generic with Object")
    public Map<Object, Object> demoApi7() {
        return null;
    }

    @Override
    @DubboApi(value = "List without generics", responseClassDescription="List without generics")
    public List demoApi10() {
        return null;
    }

    @Override
    @DubboApi(value = "List generic with Object", responseClassDescription="List generic with Object")
    public List<Object> demoApi9() {
        return null;
    }

    @Override
    @DubboApi(value = "Object", responseClassDescription="Object")
    public Object demoApi8() {
        return null;
    }
}
