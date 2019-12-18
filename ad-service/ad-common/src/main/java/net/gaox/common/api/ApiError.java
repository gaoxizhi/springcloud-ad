package net.gaox.common.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>  API错误信息描述 </p>
 *
 * @author gaox·Eric
 * @date 2019/1/15 19:42
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    /**
     * 异常代码
     */
    Integer code;
    /**
     * 异常信息
     */
    private String msg;
}