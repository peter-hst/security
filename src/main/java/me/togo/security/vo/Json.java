package me.togo.security.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Json implements Serializable {
    private boolean isError;
    private Integer code;
    private String msg;
    private Object data;

    public static Json ok() {
        return Json.builder().isError(false).build();
    }

    public static Json ok(String msg) {
        return Json.builder().isError(false).msg(msg).build();
    }

    public static Json ok(Object data) {
        return Json.builder().isError(false).data(data).build();
    }

    public static Json fail(String msg) {
        return Json.builder().isError(true).msg(msg).build();
    }

    public static Json fail(String msg, Integer code) {
        return Json.builder().isError(true).code(code).msg(msg).build();
    }

    public static Json fail(String msg, Integer code, Object data) {
        return Json.builder().isError(true).code(code).msg(msg).data(data).build();
    }
}
