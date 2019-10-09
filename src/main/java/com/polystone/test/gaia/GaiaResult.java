package com.polystone.test.gaia;

/**
 * @Author: Z.K
 * @FileName: GaiaResult
 * @DateTime: 2019/8/21 0021
 * @Version 1.0
 * @Description:
 */
public class GaiaResult<T> {

    private int code;
    private String message;
    private T data;

    public GaiaResult() {
    }
    public GaiaResult(int code,String message) {
        this.code = code;
        this.message = message;
    }
    public GaiaResult(int code,String message,T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
