package com.polystone.tools.common;

import java.util.HashMap;
import java.util.Map;

/**
 * http请求
 *
 * @author jimmy
 * @version V1.0, 2018/7/4
 * @copyright
 */
public class CustomHttpRequest {

    private String url;
    private Map<String, String> params;


    public CustomHttpRequest(String url, Map<String, String> params) {
        this.url = url;
        this.params = params;
    }

    /**
     * 构建实例
     *
     * @param url 请求地址
     * @return 返回值
     */
    public static CustomHttpRequest build(String url) {
        CustomHttpRequest httpRequest = new CustomHttpRequest(url, new HashMap<>());
        return httpRequest;
    }

    /**
     * 添加参数
     *
     * @param name 参数名
     * @param val 参数值
     * @return 返回值
     */
    public CustomHttpRequest addParam(String name, String val) {
        this.params.put(name, val);
        return this;
    }

    /**
     * 添加参数
     *
     * @param param 参数
     * @return 返回值
     */
    public CustomHttpRequest addParam(Map<String, String> param) {
        this.params.putAll(param);
        return this;
    }

    /**
     * 执行post请求
     *
     * @return 返回值
     */
    public CustomHttpResponse doPost() {
        CustomHttpResponse response = check();
        if (response.isSuccess()) {
            String res = HttpUtil.doPost(url, params);
            response.setResponses(res);
        }
        return response;
    }


    /**
     * 执行get请求
     *
     * @return 返回值
     */
    public CustomHttpResponse doGet() {
        CustomHttpResponse response = check();
        if (response.isSuccess()) {
            String res = HttpUtil.doGet(url, params);
            response.setResponses(res);
        }
        return response;
    }

    private CustomHttpResponse check() {
        CustomHttpResponse response = new CustomHttpResponse();
        if (StringUtil.isEmptyTrim(url)) {
            response.setCode(1);
            response.setMsg("http url is empty");
        }
        return response;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
