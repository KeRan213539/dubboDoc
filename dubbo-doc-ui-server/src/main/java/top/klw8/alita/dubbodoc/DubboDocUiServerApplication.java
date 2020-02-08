package top.klw8.alita.dubbodoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author klw
 * @ClassName: DubboDocUiServerApplication
 * @Description: 启动 dubbo doc 调试 服务
 * @date 2019-09-19 17:23:17
 */
@SpringBootApplication
@RestController
public class DubboDocUiServerApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DubboDocUiServerApplication.class);

//        ApplicationConfig application = new ApplicationConfig();
//        application.setName("alita-dubbo-debug-tool");
//
//        RegistryConfig registryConfig = new RegistryConfig();
//        registryConfig.setAddress("nacos://127.0.0.1:8848");
//        registryConfig.setRegister(false);
//
//        ReferenceConfig<GenericService> reference = new ReferenceConfig<>(); // 该类很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
//        reference.setApplication(application);
//        reference.setRegistry(registryConfig); // 多个注册中心可以用setRegistries()
//
//        reference.setInterface("top.klw8.alita.service.api.authority.IAlitaUserProvider");
//
//        // 声明为泛化接口
//        reference.setGeneric(true);
//
//
//        GenericService genericService = reference.get();
//
//        Map<String, Object> catlog = new HashMap<>();
//        catlog.put("catlogName", "test4");
//        catlog.put("showIndex", 1);
//        catlog.put("remark", "测试");
//
//        // 基本类型以及Date,List,Map等不需要转换，直接调用
////        Object result = genericService.$invoke("findUserById", new String[] {"java.lang.String"}, new Object[] {"d84c6b4ed9134d468e5a43d467036c46"});
////        CompletableFuture future = genericService.$invokeAsync("addCatlog", new String[] {"top.klw8.alita.entitys.authority.SystemAuthoritysCatlog"}, new Object[] {catlog});
//        CompletableFuture future = genericService.$invokeAsync("findUserById", new String[] {"java.lang.String"}, new Object[] {"d84c6b4ed9134d468e5a43d467036c46"});
//        future.whenComplete((r, ex) -> {
//            if(null != ex){
//                ((Exception)ex).printStackTrace();
//            } else {
//                System.out.println(r);
//            }
//        });

    }

}
