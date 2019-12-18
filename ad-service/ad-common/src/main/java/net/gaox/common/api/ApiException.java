package net.gaox.common.api;

/**
 * <p> API 异常类 </p>
 *
 * @author gaox·Eric
 * @date 2019/1/15 19:42
 */

public class ApiException extends Exception {

    public static final int ERR_RESULT_RESOLUTION = -2;

    public ApiException(String field) {
        this(ERR_RESULT_RESOLUTION, "Cannot resolve field " + field + " from oapi resonpse");
    }

    public ApiException(int errCode, String errMsg) {
        super("error code: " + errCode + ", error message: " + errMsg);
    }
}