package com.wushiyii.model;

import com.wushiyii.utils.JacksonUtil;
import lombok.Data;

import java.util.concurrent.TimeoutException;

@Data
public class ErrorResponse {

    private Integer code;

    private String msg;

    public ErrorResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private static final Integer ERROR = -1;



    public static ErrorResponse fail(Throwable e) {

        if (e instanceof TimeoutException) {
            return new ErrorResponse(ERROR, "timeout");
        }

        return new ErrorResponse(ERROR, e.getMessage());
    }

    public String toJSON() {
        return JacksonUtil.toJSON(this);
    }

}
