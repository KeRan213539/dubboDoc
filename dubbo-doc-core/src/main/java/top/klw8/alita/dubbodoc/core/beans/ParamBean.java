package top.klw8.alita.dubbodoc.core.beans;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: ParamBean
 * @Description: 参数信息Bean
 * @date 2020/2/27 18:33
 */
@Getter
@Setter
public class ParamBean {

    /**
     * @author klw(213539@qq.com)
     * @Description: 参数名称
     */
    private String name;

    /**
     * @author klw(213539@qq.com)
     * @Description: 参数中文名称
     */
    private String nameCh;

    /**
     * @author klw(213539@qq.com)
     * @Description: 是否必须
     */
    private Boolean required;

    /**
     * @author klw(213539@qq.com)
     * @Description: 描述
     */
    private String description;

    /**
     * @author klw(213539@qq.com)
     * @Description: 示例
     */
    private String example;

    /**
     * @author klw(213539@qq.com)
     * @Description: 默认值
     */
    private String defaultValue;

    /**
     * @author klw(213539@qq.com)
     * @Description: 参数的java类型
     */
    private String javaType;

    /**
     * @author klw(213539@qq.com)
     * @Description: 该参数在页面上应该以什么html元素显示
     */
    private HtmlTypeEnum htmlType;

    /**
     * @author klw(213539@qq.com)
     * @Description: 允许的值
     */
    private String[] allowableValues;

    /**
     * @author klw(213539@qq.com)
     * @Description: 如果一个RequestBean中的属性不是基本数据类型, 则此属性会有值,由于html表单不好展示此参数,将以文本域方式展示,填入该参数的json
     */
    @JSONField(serialize = false)
    private List<ParamBean> subParams;

    /**
     * @author klw(213539@qq.com)
     * @Description: 对应 subParams 的json, subParams 可能为空
     */
    private String subParamsJson;

}
