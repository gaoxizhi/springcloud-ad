package net.gaox.common.handler;

import lombok.extern.slf4j.Slf4j;
import net.gaox.common.api.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> 统一拦截应用 </p>
 *
 * @author gaox·Eric
 * @date 2019/12/16 19:36
 */
@Slf4j
@RestControllerAdvice
public class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * 系统异常拦截，指定Exception异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        log.error("异常类【{}】", e.getClass().toString());
        e.printStackTrace();
        return ApiResponse.fail().error("系统发生未知错误");
    }

    /**
     * 运行异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse errorMsgErrorHandler(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        log.error("异常类【{}】", e.getClass().toString());
        e.printStackTrace();
        return ApiResponse.fail().error("数据错误，请检查后重新提交操作！");
    }

    /**
     * 通用的接口映射异常处理方
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            return new ResponseEntity(
                    ApiResponse.fail().and("code", status.value())
                            .error(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), status);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
            logger.error("参数转换失败，方法：" + exception.getParameter().getMethod().getName() + "，参数：" + exception.getName()
                    + ",信息：" + exception.getLocalizedMessage());
            return new ResponseEntity(ApiResponse.fail().and("code", status.value()).error("参数转换失败"), status);
        }
        return new ResponseEntity(ApiResponse.fail().and("code", status.value()).error("参数转换失败2"), status);
    }
}