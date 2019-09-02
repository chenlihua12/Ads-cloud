package com.honor.common.base.dto;

import com.honor.common.base.constant.ErrorConstant;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class CommonResultDto<T> {
    private Integer code;
    private String msg;
    private T data;

    public CommonResultDto() {
    }

    public CommonResultDto(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonResultDto(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回成功的结果
     *
     * @param data 需返回的结果
     * @param <T>
     * @return
     */
    public static <T> CommonResultDto<T> SUCCESS(T data) {
        return new CommonResultDto<>(ErrorConstant.SUCCESS, "OK", data);
    }

    /**
     * 返回成功的结果
     *
     * @param data 需返回的结果
     * @param <T>
     * @return
     */
    public static <T> CommonResultDto<T> SUCCESS_DATA(T data) {
        return new CommonResultDto<>(ErrorConstant.SUCCESS, "OK", data);
    }

    public static CommonResultDto SUCCESS_MAP(Map<String,Object> data) {
        return new CommonResultDto(ErrorConstant.SUCCESS, "OK", data);
    }

    /**
     * 返回成功的结果
     *
     * @param <T>
     * @return
     */
    public static <T> CommonResultDto<T> SUCCESS() {
        return new CommonResultDto<>(ErrorConstant.SUCCESS, "OK");
    }

    /**
     * 返回失败的结果 PS：返回"未知异常"
     *
     * @param <T>
     * @return
     */
    public static <T> CommonResultDto<T> SUCCESS(String msg) {
        return new CommonResultDto<>(ErrorConstant.SUCCESS, msg);
    }

    /**
     * 返回失败的结果 PS：返回"未知异常"
     *
     * @param <T>
     * @return
     */
    public static <T> CommonResultDto<T> FAILURE() {
        return new CommonResultDto<>(ErrorConstant.FAILURE, "FAILURE");
    }

    /**
     * 返回失败的结果 PS：返回"未知异常"
     *
     * @param <T>
     * @return
     */
    public static <T> CommonResultDto<T> FAILURE(String msg) {
        return new CommonResultDto<>(ErrorConstant.FAILURE, msg);
    }

    /**
     * 自定义错误码返回
     *
     * @param <T>
     * @return
     */
    public static <T> CommonResultDto<T> FAILURE(Integer code, String msg) {
        return new CommonResultDto<>(code, msg);
    }

}
