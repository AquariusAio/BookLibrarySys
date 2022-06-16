package com.example.demo.result;


/**
 * @author Evan
 * @date 2019/4
 */
public class ResultFactory {

    public static Result1 buildSuccessResult(Object data) {
        return buildResult(ResultCode.SUCCESS, "成功", data);
    }

    public static Result1 buildFailResult(String message) {
        return buildResult(ResultCode.FAIL, message, null);
    }

    public static Result1 buildResult(ResultCode resultCode, String message, Object data) {
        return buildResult(resultCode.code, message, data);
    }

    public static Result1 buildResult(int resultCode, String message, Object data) {
        return new Result1(resultCode, message, data);
    }
}
