package top.klw8.alita.examples.dubbodoc.params;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.dubbodoc.annotations.ResponseProperty;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoRespBean1
 * @Description: demo 响应bean
 * @date 2020/2/3 0:00
 */
@Getter
@Setter
public class DemoRespBean1 {

    private String code;

    @ResponseProperty("返回消息")
    private String message;

}
