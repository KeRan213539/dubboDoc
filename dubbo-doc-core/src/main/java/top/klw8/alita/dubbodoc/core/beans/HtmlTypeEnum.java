package top.klw8.alita.dubbodoc.core.beans;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: HtmlTypeEnum
 * @Description: html type 枚举
 * @date 2020/2/28 17:41
 */
public enum HtmlTypeEnum {

    /**
     * @author klw(213539@qq.com)
     * @Description: 文本框
     */
    TEXT,

    /**
     * @author klw(213539@qq.com)
     * @Description: 文本框, 该类型在调用dubbo接口前会被转为 byte
     */
    TEXT_BYTE,

    /**
     * @author klw(213539@qq.com)
     * @Description: 文本框,将被限制只能输入一个字符. 该类型在调用dubbo接口前会被转为 char
     */
    TEXT_CHAR,

    /**
     * @author klw(213539@qq.com)
     * @Description: 数字输入框,整数
     */
    NUMBER_INTEGER,

    /**
     * @author klw(213539@qq.com)
     * @Description: 数字输入框,小数
     */
    NUMBER_DECIMAL,

    /**
     * @author klw(213539@qq.com)
     * @Description: 下拉选择框
     */
    SELECT,

    /**
     * @author klw(213539@qq.com)
     * @Description: 文本域,一般用来展示参数中包含的java bean的json字符串
     */
    TEXT_AREA,
    ;

}
