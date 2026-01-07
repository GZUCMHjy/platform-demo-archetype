package ${package}.handler;

import ${package}.enums.ErrorCode;
import ${package}.exception.BusinessException;
import ${package}.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. 使用@RestControllerAdvice统一处理异常
 * 2. 区分不同异常类型，返回明确的错误信息
 * 3. 记录异常日志，便于问题排查
 * 4. 敏感信息不直接返回给前端
 *
 * @author ${author}
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     *
     * @param e       业务异常
     * @param request HTTP请求
     * @return 响应结果
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.warn("业务异常: URI={}, Code={}, Message={}",
                request.getRequestURI(), e.getCode(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 参数校验异常处理（@Valid注解，RequestBody参数）
     *
     * @param e       参数校验异常
     * @param request HTTP请求
     * @return 响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                            HttpServletRequest request) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数校验异常: URI={}, Errors={}", request.getRequestURI(), errorMsg);
        return Result.error(ErrorCode.PARAM_VALID_ERROR.getCode(), errorMsg);
    }

    /**
     * 参数绑定异常处理（模型绑定）
     *
     * @param e       参数绑定异常
     * @param request HTTP请求
     * @return 响应结果
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e, HttpServletRequest request) {
        String errorMsg = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数绑定异常: URI={}, Errors={}", request.getRequestURI(), errorMsg);
        return Result.error(ErrorCode.PARAM_VALID_ERROR.getCode(), errorMsg);
    }

    /**
     * 参数校验异常处理（@Validated注解，RequestParam/PathVariable参数）
     *
     * @param e       约束违反异常
     * @param request HTTP请求
     * @return 响应结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e,
                                                        HttpServletRequest request) {
        String errorMsg = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数校验异常: URI={}, Errors={}", request.getRequestURI(), errorMsg);
        return Result.error(ErrorCode.PARAM_VALID_ERROR.getCode(), errorMsg);
    }

    /**
     * 非法参数异常处理
     *
     * @param e       非法参数异常
     * @param request HTTP请求
     * @return 响应结果
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> handleIllegalArgumentException(IllegalArgumentException e,
                                                   HttpServletRequest request) {
        log.warn("非法参数异常: URI={}, Message={}", request.getRequestURI(), e.getMessage());
        return Result.error(ErrorCode.PARAM_ERROR.getCode(), e.getMessage());
    }

    /**
     * 非法状态异常处理
     *
     * @param e       非法状态异常
     * @param request HTTP请求
     * @return 响应结果
     */
    @ExceptionHandler(IllegalStateException.class)
    public Result<?> handleIllegalStateException(IllegalStateException e,
                                                HttpServletRequest request) {
        log.warn("非法状态异常: URI={}, Message={}", request.getRequestURI(), e.getMessage());
        return Result.error(ErrorCode.OPERATION_ERROR.getCode(), e.getMessage());
    }

    /**
     * 运行时异常处理
     *
     * @param e       运行时异常
     * @param request HTTP请求
     * @return 响应结果
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.error("运行时异常: URI={}, Message={}", request.getRequestURI(), e.getMessage(), e);
        return Result.error(ErrorCode.SYSTEM_ERROR.getCode(), "系统异常，请稍后再试");
    }

    /**
     * 其他异常处理
     *
     * @param e       异常
     * @param request HTTP请求
     * @return 响应结果
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常: URI={}, Message={}", request.getRequestURI(), e.getMessage(), e);
        return Result.error(ErrorCode.SYSTEM_ERROR.getCode(), "系统异常，请稍后再试");
    }
}
