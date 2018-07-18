package com.polystone.tools.common;

import com.alibaba.fastjson.JSON;
import com.polystone.tools.security.SecurityUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http请求
 *
 * @author jimmy
 * @version V1.0, 2018/7/4
 * @copyright
 */
public class CustomHttpRequest {

    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求参数
     */
    private Map<String, String> params;
    /**
     * 编码方式
     */
    private String charset;


    public CustomHttpRequest(String url, Map<String, String> params) {
        this.url = url;
        this.params = params;
        this.charset = "UTF-8";
    }

    /**
     * 构建实例
     *
     * @param url 请求地址
     * @return 返回值
     */
    public static CustomHttpRequest build(String url) {
        CustomHttpRequest httpRequest = new CustomHttpRequest(url, new HashMap<>());
        httpRequest.setCharset("UTF-8");
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
     * 设置编码方式
     *
     * @return 返回值
     */
    public CustomHttpRequest setCharset(String charset) {
        this.charset = charset;
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
     * 添加签名
     *
     * @param signKey 参数名
     * @param keys 签名参数
     * @return 返回值
     */
    public CustomHttpRequest addSign(String signKey, List<String> keys) {
        this.params.put(signKey, buildSign(keys));
        return this;
    }

    /**
     * 添加签名
     *
     * @param signKey 参数名
     * @return 返回值
     */
    public CustomHttpRequest addSign(String signKey) {
        this.params.put(signKey, buildSign(new ArrayList<>(params.keySet())));
        return this;
    }

    /**
     * 添加签名
     *
     * @param signKey 参数名
     * @param keys 签名参数
     * @return 返回值
     */
    public CustomHttpRequest addSign(String signKey, String... keys) {
        this.params.put(signKey, buildSign(keys));
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
     * 执行post请求
     *
     * @return 返回值
     */
    public CustomHttpResponse doPostJson() {
        CustomHttpResponse response = check();
        if (response.isSuccess()) {
            String res = HttpUtil.doPost(url, JSON.toJSONString(params), charset);
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

    /**
     * 检查请求地址
     *
     * @return 返回值
     */
    private CustomHttpResponse check() {
        CustomHttpResponse response = new CustomHttpResponse();
        if (StringUtil.isEmptyTrim(url)) {
            response.setCode(1);
            response.setMsg("http url is empty");
        } else {
            response.setCode(0);
        }
        return response;
    }

    /**
     * 签名
     *
     * @param keys 参数
     * @return 返回值
     */
    private String buildSign(List<String> keys) {
        StringBuilder sb = new StringBuilder();
        Collections.sort(keys);
        for (String key : keys) {
            if (!params.containsKey(key)) {
                continue;
            }
            sb.append(params.get(key));
        }
        return SecurityUtil.getInstance().md5(sb.toString());
    }

    /**
     * 签名
     *
     * @param keys 参数
     * @return 返回值
     */
    private String buildSign(String... keys) {
        return buildSign(Arrays.asList(keys));
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
