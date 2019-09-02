package com.honor.common.base.exceptionHandle;

import com.honor.common.base.constant.ErrorConstant;
import com.honor.common.base.dto.CommonResultDto;
import com.honor.common.base.exception.CommonException;
import com.honor.common.base.exception.PlugException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 没有国际化统一异常处理类【！ i18n.enabled=true】
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResultDto<?> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("GlobalExceptionHandler.httpMessageNotReadableException.error={}", e);
        return CommonResultDto.builder().code(ErrorConstant.PARAMS_ERROR).msg("参数不能为空").build();
    }

    @ExceptionHandler(BindException.class)
    public CommonResultDto<?> bindException(BindException e) {
        log.error("GlobalExceptionHandler.bindException.error={}", e);
        return CommonResultDto.builder().code(ErrorConstant.PARAMS_ERROR).msg(e.getBindingResult().getFieldError().getDefaultMessage()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResultDto<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("GlobalExceptionHandler.methodArgumentNotValidException.error={}", e);
        return CommonResultDto.builder().code(ErrorConstant.PARAMS_ERROR).msg(e.getBindingResult().getFieldError().getDefaultMessage()).build();
    }

    @ExceptionHandler(PlugException.class)
    public CommonResultDto<?> plugException(PlugException e) {
        log.error("GlobalExceptionHandler.plugException.error={}", e.getMsg(), e);
        return CommonResultDto.builder().code(e.getCode()).msg(e.getMsg()).build();
    }

    @ExceptionHandler(CommonException.class)
    public CommonResultDto<?> plugException(CommonException e) {
        log.error("GlobalExceptionHandler.plugException.error={}", e.getMsg(), e);
        return CommonResultDto.builder().code(e.getCode()).msg(e.getMsg()).build();
    }

    @ExceptionHandler(Exception.class)
    public CommonResultDto<?> exceptionHandler(Exception e) {
        log.error("GlobalExceptionHandler.exceptionHandler", e);
        return CommonResultDto.builder().code(ErrorConstant.EXCEPTION_FAILURE).msg("系统异常 !").build();
    }
}
