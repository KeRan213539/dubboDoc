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
package top.klw8.alita.dubbodoc.utils;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboUtil
 * Dubbo operation related tool class
 * @date 2019/9/19 17:31
 */
public class DubboUtil {

    /**
     * @author klw(213539@qq.com)
     * Current application information
     */
    private static ApplicationConfig application;

    /**
     * @author klw(213539@qq.com)
     * Registry information cache
     */
    private static Map<String, RegistryConfig> registryConfigCache;

    /**
     * @author klw(213539@qq.com)
     * Dubbo service interface proxy cache
     */
    private static Map<String, ReferenceConfig<GenericService>> referenceCache;

    private static final ExecutorService executor;

    /**
     * @author klw(213539@qq.com)
     * Default retries
     */
    private static int retries = 2;

    /**
     * @author klw(213539@qq.com)
     * Default timeout
     */
    private static int timeout = 1000;

    static{
        // T (number of threads) = N (number of server cores) * u (expected CPU utilization) * (1 + E (waiting time) / C (calculation time))
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 40 * (1 + 5 / 2));
        application = new ApplicationConfig();
        application.setName("alita-dubbo-debug-tool");
        registryConfigCache = new ConcurrentHashMap<>();
        referenceCache = new ConcurrentHashMap<>();
    }

    public static void setRetriesAndTimeout(int retries, int timeout){
        DubboUtil.retries = retries;
        DubboUtil.timeout = timeout;
    }

    /**
     * @author klw(213539@qq.com)
     * Get registry information
     * @Date 2019/9/19 17:39
     * @param: address Address of Registration Center
     * @return org.apache.dubbo.config.RegistryConfig
     */
    private static RegistryConfig getRegistryConfig(String address) {
        RegistryConfig registryConfig = registryConfigCache.get(address);
        if (null == registryConfig) {
            registryConfig = new RegistryConfig();
            registryConfig.setAddress(address);
            registryConfig.setRegister(false);
            registryConfigCache.put(address, registryConfig);
        }
        return registryConfig;
    }

    /**
     * @author klw(213539@qq.com)
     * Get proxy object for dubbo service
     * @Date 2019/9/19 17:43
     * @param: address  address Address of Registration Center
     * @param: interfaceName  Interface full package path
     * @return org.apache.dubbo.config.ReferenceConfig<org.apache.dubbo.rpc.service.GenericService>
     */
    private static ReferenceConfig<GenericService> getReferenceConfig(String address, String interfaceName) {
        ReferenceConfig<GenericService> referenceConfig = referenceCache.get(address + "/" + interfaceName);
        if (null == referenceConfig) {
            referenceConfig = new ReferenceConfig<>();
            referenceConfig.setRetries(retries);
            referenceConfig.setTimeout(timeout);
            referenceConfig.setApplication(application);
            if(address.startsWith("dubbo")){
                referenceConfig.setUrl(address);
            } else {
                referenceConfig.setRegistry(getRegistryConfig(address));
            }
            referenceConfig.setInterface(interfaceName);
            // Declared as a generic interface
            referenceConfig.setGeneric(true);
            referenceCache.put(address + "/" + interfaceName, referenceConfig);
        }
        return referenceConfig;
    }

    /**
     * @author klw(213539@qq.com)
     * Call duboo provider and return {@link CompletableFuture}
     * @Date 2020/3/1 14:55
     * @param: address
     * @param: interfaceName
     * @param: methodName
     * @param: async  Whether the provider is asynchronous is to directly return the {@link CompletableFuture}
     * returned by the provider, not to wrap it as {@link CompletableFuture}
     * @param: prarmTypes
     * @param: prarmValues
     * @return java.util.concurrent.CompletableFuture<java.lang.Object>
     */
    public static CompletableFuture<Object> invoke(String address, String interfaceName,
                                                  String methodName, boolean async, String[] prarmTypes,
                                                  Object[] prarmValues) {
        CompletableFuture future = null;
        ReferenceConfig<GenericService> reference = getReferenceConfig(address, interfaceName);
        if (null != reference) {
            GenericService genericService = reference.get();
            if (null != genericService) {
                if(async){
                    future = genericService.$invokeAsync(methodName, prarmTypes, prarmValues);
                } else {
                    future = CompletableFuture.supplyAsync(() -> genericService.$invoke(methodName, prarmTypes, prarmValues), executor);
                }
            }
        }
        return future;
    }

    /**
     * @author klw(213539@qq.com)
     * Synchronous call provider. The provider must provide synchronous api
     * @Date 2020/3/1 14:58
     * @param: address
     * @param: interfaceName
     * @param: methodName
     * @param: prarmTypes
     * @param: prarmValues
     * @return java.lang.Object
     */
    public static Object invokeSync(String address, String interfaceName,
                                                   String methodName, String[] prarmTypes,
                                                   Object[] prarmValues) {
        ReferenceConfig<GenericService> reference = getReferenceConfig(address, interfaceName);
        if (null != reference) {
            GenericService genericService = reference.get();
            if (null != genericService) {
                return genericService.$invoke(methodName, prarmTypes, prarmValues);
            }
        }
        return null;
    }

}
