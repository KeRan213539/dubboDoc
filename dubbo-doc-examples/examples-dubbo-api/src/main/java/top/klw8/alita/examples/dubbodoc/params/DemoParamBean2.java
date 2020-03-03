package top.klw8.alita.examples.dubbodoc.params;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.dubbodoc.annotations.RequestParam;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoParamBean2
 * @Description: demo 请求参数2
 * @date 2020/2/2 23:42
 */
@Getter
@Setter
public class DemoParamBean2 {

    @RequestParam("姓名2")
    private String name2;

    @RequestParam("年龄2")
    private Integer age2;

}
