package top.klw8.alita.examples.dubbodoc.params;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.dubbodoc.annotations.RequestParam;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoParamBean1SubBean1
 * @Description: DemoParamBean1 中的属性Bean
 * @date 2020/2/26 16:38
 */
@Getter
@Setter
public class DemoParamBean1SubBean1 {

    @RequestParam("子Bean姓名")
    private String subName;

    @RequestParam("子Bean年龄")
    private Integer subAge;

    private TestEnum testEnum;

    // 测试循环引用
//    @RequestParam("====bean")
//    private DemoParamBean1 bean;

}
