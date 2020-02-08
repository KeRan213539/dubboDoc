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
 * @Description: dubbo操作相关工具类
 * @date 2019/9/19 17:31
 */
public class DubboUtil {

    /**
     * @author klw(213539@qq.com)
     * @Description: 当前应用的信息
     */
    private static ApplicationConfig application;

    /**
     * @author klw(213539@qq.com)
     * @Description: 注册中心信息缓存
     */
    private static Map<String, RegistryConfig> registryConfigCache;

    /**
     * @author klw(213539@qq.com)
     * @Description: dubbo服务接口代理缓存
     */
    private static Map<String, ReferenceConfig<GenericService>> referenceCache;

    private static final ExecutorService executor;

    static{
        // T(线程数) = N(服务器内核数) * u(期望cpu利用率) * （1 + E(等待时间)/C(计算时间));
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 40 * (1 + 5 / 2));
        application = new ApplicationConfig();
        application.setName("alita-dubbo-debug-tool");
        registryConfigCache = new ConcurrentHashMap<>();
        referenceCache = new ConcurrentHashMap<>();
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取注册中心信息
     * @Date 2019/9/19 17:39
     * @param: address 注册中心地址
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
     * @Description: 获取服务的代理对象
     * @Date 2019/9/19 17:43
     * @param: address  注册中心地址
     * @param: interfaceName  接口完整包路径
     * @return org.apache.dubbo.config.ReferenceConfig<org.apache.dubbo.rpc.service.GenericService>
     */
    private static ReferenceConfig<GenericService> getReferenceConfig(String address, String interfaceName) {
        ReferenceConfig<GenericService> referenceConfig = referenceCache.get(interfaceName);
        if (null == referenceConfig) {
            referenceConfig = new ReferenceConfig<>();
            referenceConfig.setApplication(application);
            referenceConfig.setRegistry(getRegistryConfig(address));
            referenceConfig.setInterface(interfaceName);
            // 声明为泛化接口
            referenceConfig.setGeneric(true);
            referenceCache.put(interfaceName, referenceConfig);
        }
        return referenceConfig;
    }

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

}
