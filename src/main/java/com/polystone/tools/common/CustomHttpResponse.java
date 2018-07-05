package com.polystone.tools.common;

import com.alibaba.fastjson.JSON;
import java.util.List;

/**
 * http请求响应
 *
 * @author jimmy
 * @version V1.0, 2018/7/4
 * @copyright
 */
public class CustomHttpResponse {

    /**
     * 响应码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 响应信息
     */
    private String responses;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResponses() {
        return responses;
    }

    public void setResponses(String responses) {
        this.responses = responses;
    }

    public boolean isSuccess() {
        return this.code.equals(0);
    }

    /**
     * 获取响应的信息
     *
     * @param _class 类
     * @param <T> 类型
     * @return 返回值
     */
    public <T> T getResponseObject(Class<T> _class) {
        if (StringUtil.isNotEmpty(responses)) {
            return JSON.parseObject(responses, _class);
        }
        return null;
    }

    /**
     * 获取响应信息
     *
     * @param _class 类
     * @param <T> 类型
     * @return 返回值
     */
    public <T> List<T> getResponseArrary(Class<T> _class) {
        if (StringUtil.isNotEmpty(responses)) {
            return JSON.parseArray(responses, _class);
        }
        return null;
    }

    @Override
    public String toString() {
        return "CustomHttpResponse{" +
            "code=" + code +
            ", msg='" + msg + '\'' +
            ", responses='" + responses + '\'' +
            '}';
    }
}
