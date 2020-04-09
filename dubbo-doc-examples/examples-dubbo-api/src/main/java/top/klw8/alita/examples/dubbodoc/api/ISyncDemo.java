/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.examples.dubbodoc.api;

import top.klw8.alita.examples.dubbodoc.params.DemoParamBean1;
import top.klw8.alita.examples.dubbodoc.params.DemoParamBean2;
import top.klw8.alita.examples.dubbodoc.responses.BaseResponse;
import top.klw8.alita.examples.dubbodoc.responses.DemoRespBean1;

import java.util.List;
import java.util.Map;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ISyncDemo
 * @Description: synchronization demo
 * @date 2020/2/2 23:41
 */
public interface ISyncDemo {

    /**
     * @author klw(213539@qq.com)
     * @Description: request and response parameters are beans
     * @Date 2020/2/4 0:01
     * @param: param
     * @return top.klw8.alita.examples.dubbodoc.responses.DemoRespBean1
     */
    DemoRespBean1 demoApi1(DemoParamBean1 param1, DemoParamBean2 param2);

    /**
     * @author klw(213539@qq.com)
     * @Description: request and response parameters are Strings
     * @Date 2020/2/4 0:02
     * @param: prarm1
     * @param: prarm2
     * @return java.lang.String
     */
    String demoApi2(String prarm1, String prarm2);

    /**
     * @author klw(213539@qq.com)
     * @Description: Without Dubbo doc annotation, no document will be generated
     * @Date 2020/2/4 0:22
     * @param: prarm1
     * @return java.lang.String
     */
    String demoApi3(String prarm1);

    /**
     * @author klw(213539@qq.com)
     * @Description: Nonparametric method with Dubbo doc annotation
     * @Date 2020/2/4 0:02
     * @param:
     * @return java.lang.String
     */
    String demoApi4();

    /**
     * @author klw(213539@qq.com)
     * @Description: Use generics in response
     */
    BaseResponse<DemoRespBean1> demoApi5();

    /**
     * @author klw(213539@qq.com)
     * @Description: Map without generics
     */
    Map demoApi6();

    /**
     * @author klw(213539@qq.com)
     * @Description: Map generic with Object
     */
    Map<Object, Object> demoApi7();

    /**
     * @author klw(213539@qq.com)
     * @Description: List without generics
     */
    List demoApi10();

    /**
     * @author klw(213539@qq.com)
     * @Description: List generic with Object
     */
    List<Object> demoApi9();

    /**
     * @author klw(213539@qq.com)
     * @Description: Object
     */
    Object demoApi8();

}
