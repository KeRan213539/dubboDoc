package top.klw8.alita.examples.dubbodoc.params;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.dubbodoc.annotations.RequestBean;
import top.klw8.alita.dubbodoc.annotations.RequestParam;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoParamBean1
 * @Description: demo 请求参数
 * @date 2020/2/2 23:42
 */
@Getter
@Setter
@RequestBean
public class DemoParamBean1 {

    @RequestParam("姓名")
    private String name;

    @RequestParam("年龄")
    private Integer age;

    private Boolean man;

}
