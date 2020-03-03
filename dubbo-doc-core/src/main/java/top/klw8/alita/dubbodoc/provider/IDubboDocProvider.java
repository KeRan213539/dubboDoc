package top.klw8.alita.dubbodoc.provider;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: IDubboDocProvider
 * @Description: dubbo doc 使用的接口,获取解析后的dubbo doc
 * @date 2020/3/1 12:26
 */
public interface IDubboDocProvider {

    /**
     * @author klw(213539@qq.com)
     * @Description: 获取全部Module的基本信息,不包含api参数信息
     */
    String apiModuleList();

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据dubbo提供者接口完整类名获取模块信息
     */
    String apiModuleInfo(String apiInterfaceClassName);

    /**
     * @author klw(213539@qq.com)
     * @Description: 根据dubbo提供者接口完整类名+方法名获取方法参数和返回信息
     */
    String apiParamsResponseInfo(String apiInterfaceClassNameMethodName);

}
