package top.klw8.alita.dubbodoc.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import top.klw8.alita.dubbodoc.core.DubboDocCache;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DubboDocProviderImpl
 * @Description: dubbo doc 使用的接口实现
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
