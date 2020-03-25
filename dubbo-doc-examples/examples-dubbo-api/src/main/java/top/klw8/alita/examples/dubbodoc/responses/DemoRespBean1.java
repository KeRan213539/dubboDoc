package top.klw8.alita.examples.dubbodoc.responses;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.dubbodoc.annotations.ResponseProperty;

import java.util.List;
import java.util.Map;

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

    @ResponseProperty(value = "返回消息2", example = "这是返回消息2")
    private String message2;

    @ResponseProperty("Map无泛型")
    private Map map1;

    @ResponseProperty("Map泛型Object")
    private Map<Object, Object> map2;

    @ResponseProperty("List无泛型")
    private List list1;

    @ResponseProperty("List泛型Object")
    private List<Object> list2;

    @ResponseProperty("Object")
    private Object obj1;

}
