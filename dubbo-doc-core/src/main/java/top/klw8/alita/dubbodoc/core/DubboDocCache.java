package top.klw8.alita.dubbodoc.core;

import com.alibaba.fastjson.JSON;
import top.klw8.alita.dubbodoc.utils.ClassTypeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboDocCache
 * @Description: dubbo doc 缓存
 * @date 2020/2/29 19:46
 */
public class DubboDocCache {

    // 模块缓存
    private static Map<String, Map<String, Object>> apiModulesCache = new ConcurrentHashMap<>(16);
    private static Map<String, String> apiModulesStrCache = new ConcurrentHashMap<>(16);

    // 模块中的接口详细信息缓存
    private static Map<String, Map<String, Object>> apiParamsAndRespCache = new ConcurrentHashMap<>(16);
    private static Map<String, String> apiParamsAndRespStrCache = new ConcurrentHashMap<>(16);

    private static String allApiModuleInfo = null;

    public static void addApiModule(String key, Map<String, Object> moduleCacheItem){
        apiModulesCache.put(key, moduleCacheItem);
    }

    public static void addApiParamsAndResp(String key, Map<String, Object> apiParamsAndResp){
        apiParamsAndRespCache.put(key, apiParamsAndResp);
    }

    public static Map<String, Object> getApiModule(String key){
        return apiModulesCache.get(key);
    }

    public static String getApiModuleStr(String key){
        String result = apiModulesStrCache.get(key);
        if(result == null){
            Map<String, Object> temp = apiModulesCache.get(key);
            if(temp != null) {
                result = JSON.toJSONString(temp, ClassTypeUtils.FAST_JSON_FEATURES);
                apiModulesStrCache.put(key, result);
            }
        }
        return result;
    }

    public static Map<String, Object> getApiParamsAndResp(String key){
        return apiParamsAndRespCache.get(key);
    }

    public static String getApiParamsAndRespStr(String key){
        String result = apiParamsAndRespStrCache.get(key);
        if(result == null){
            Map<String, Object> temp = apiParamsAndRespCache.get(key);
            if(temp != null) {
                result = JSON.toJSONString(temp, ClassTypeUtils.FAST_JSON_FEATURES);
                apiParamsAndRespStrCache.put(key, result);
            }
        }
        return result;
    }

    public static String getAllApiModuleInfo(){
        if(allApiModuleInfo == null){
            List<Map<String, Object>> tempList = new ArrayList<>(apiModulesCache.size());
            apiModulesCache.forEach((k, v) -> {
                tempList.add(v);
            });
            allApiModuleInfo = JSON.toJSONString(tempList, ClassTypeUtils.FAST_JSON_FEATURES);
        }
        return allApiModuleInfo;
    }

}
