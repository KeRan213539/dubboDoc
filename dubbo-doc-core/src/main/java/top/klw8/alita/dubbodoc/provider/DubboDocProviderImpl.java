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
package top.klw8.alita.dubbodoc.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import top.klw8.alita.dubbodoc.core.DubboDocCache;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboDocProviderImpl
 * @Description: The api implementation of Dubbo doc
 * @date 2020/3/1 14:20
 */
@Slf4j
@Service
public class DubboDocProviderImpl implements IDubboDocProvider {

    @Override
    public String apiModuleList() {
        return DubboDocCache.getAllApiModuleInfo();
    }

    @Override
    public String apiModuleInfo(String apiInterfaceClassName) {
        return DubboDocCache.getApiModuleStr(apiInterfaceClassName);
    }

    @Override
    public String apiParamsResponseInfo(String apiInterfaceClassNameMethodName) {
        return DubboDocCache.getApiParamsAndRespStr(apiInterfaceClassNameMethodName);
    }
}
