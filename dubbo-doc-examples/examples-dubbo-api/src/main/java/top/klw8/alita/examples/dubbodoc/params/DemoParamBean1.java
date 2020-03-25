package top.klw8.alita.examples.dubbodoc.params;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.dubbodoc.annotations.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DemoParamBean1
 * @Description: demo 请求参数
 * @date 2020/2/2 23:42
 */
@Getter
@Setter
public class DemoParamBean1 {

    @RequestParam("姓名")
    private String name;

    @RequestParam("年龄")
    private Integer age;

    private Boolean man;

    @RequestParam("====subBean")
    private List<DemoParamBean1SubBean1> subBean;

    @RequestParam("Map")
    private Map<String, DemoParamBean1SubBean1> subBean2;

    @RequestParam("数组")
    private String[] strArray;

    @RequestParam("数组2")
    private DemoParamBean1SubBean1[] strArray2;

    @RequestParam("测试个枚举")
    private TestEnum testEnum;

    private DemoParamBean1SubBean1 subBean3;

    @RequestParam("Map无泛型")
    private Map map1;

    @RequestParam("Map泛型Object")
    private Map<Object, Object> map2;

    @RequestParam("List无泛型")
    private List list1;

    @RequestParam("List泛型Object")
    private List<Object> list2;

    @RequestParam("Object")
    private Object obj1;

}
