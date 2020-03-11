package top.klw8.alita.examples.dubbodoc.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: BaseResponse
 * @Description: BaseResponse
 * @date 2020/3/11 9:27
 */
@Getter
@Setter
@ToString
public class BaseResponse<T> implements java.io.Serializable {

    /**
     * @author klw(213539@qq.com)
     * @Description: 响应状态码
     */
    private String code;

    /**
     * @author klw(213539@qq.com)
     * @Description: 响应消息
     */
    private String message;

    /**
     * @author klw(213539@qq.com)
     * @Description: 响应数据
     */
    private T data;

}
