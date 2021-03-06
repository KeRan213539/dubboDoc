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

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IDubboDocProvider
 * The api used by Dubbo doc, get the parsed API information
 * @date 2020/3/1 12:26
 */
public interface IDubboDocProvider {

    /**
     * @author klw(213539@qq.com)
     * Get basic information of all modules, excluding API parameter information
     */
    String apiModuleList();

    /**
     * @author klw(213539@qq.com)
     * Get module information according to the complete class name of Dubbo provider interface
     */
    String apiModuleInfo(String apiInterfaceClassName);

    /**
     * @author klw(213539@qq.com)
     * Get method parameters and return information according to the complete class name and method name of Dubbo provider interface
     */
    String apiParamsResponseInfo(String apiInterfaceClassNameMethodName);

}
