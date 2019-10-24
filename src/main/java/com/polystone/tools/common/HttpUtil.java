package com.polystone.tools.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 *
 * @author jimmy
 * @version V1.0, 2017/7/24
 * @copyright
 */
public class HttpUtil {

    private final static Logger Log = LoggerFactory.getLogger(HttpUtil.class);
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 30 * 1000;
    private static final int MAX_TOTAL = 50;
    private static final int SLEEP_TIME = 3000;
    private static final String UTF8 = "UTF-8";
    private static HttpClientBuilder httpsClientBuilder;
    private static HttpClientBuilder httpRetryClientBuilder;


    static {
        Registry<ConnectionSocketFactory> registry = null;
        try {
            registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", new SSLConnectionSocketFactory(
                    SSLContextBuilder.create().loadTrustMaterial((X509Certificate[] var1, String var2) -> {
                        return true;
                    }).build()))
                .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager(registry);
        // 设置连接池大小
        connMgr.setMaxTotal(MAX_TOTAL);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        requestConfig = configBuilder.build();
    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     */
    public static String doGet(String url, Map<String, String> params) {
        String apiUrl = url;
        if (!isEmpty(params)) {
            try {
                apiUrl = apiUrl + buildQueryString(params);
            } catch (UnsupportedEncodingException e) {
                apiUrl = url;
                Log.error("doGet==>" + e);
            }
        }
        HttpGet httpGet = new HttpGet(apiUrl);
        return execute(httpGet, buildHttpClient());
    }


    /**
     * 发送 GET 请求（HTTP），K-V形式
     * 设置header
     */
    public static String doGet(String url, Map<String, String> params, Map<String, Object> headers) {
        String apiUrl = url;
        if (!isEmpty(params)) {
            try {
                apiUrl = apiUrl + buildQueryString(params);
            } catch (UnsupportedEncodingException e) {
                apiUrl = url;
                Log.error("doGet==>" + e);
            }
        }
        HttpGet httpGet = new HttpGet(apiUrl);
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            httpGet.setHeader(entry.getKey(), entry.getValue() + "");
        }
        return execute(httpGet, buildHttpClient());
    }

    /**
     * 构建get的请求参数
     *
     * @param params 参数
     * @return querystring
     */
    private static String buildQueryString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0) {
                param.append("?");
            } else {
                param.append("&");
            }
            param.append(key).append("=").append(URLEncoder.encode(params.get(key), UTF8));
            i++;
        }
        return param.toString();
    }

    /**
     * 执行请求并返回结果
     *
     * @param requestBase 执行请求
     * @param httpclient httpclien
     * @return 返回结果
     */
    private static String _execute(HttpRequestBase requestBase, CloseableHttpClient httpclient, String charset) {
        HttpResponse response = null;
        try {
            response = httpclient.execute(requestBase);
            if (response.getEntity() != null) {
                return EntityUtils.toString(response.getEntity(), charset);
            }
        } catch (IOException e) {
            //异常输出
            Log.error("execute is error", e);
        } finally {
            if (null != response) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        }
        return null;
    }

    /**
     * 执行请求并返回结果
     *
     * @param requestBase 执行请求
     * @param httpclient httpclien
     * @return 返回结果
     */
    private static String execute(HttpRequestBase requestBase, CloseableHttpClient httpclient) {
        return _execute(requestBase, httpclient, UTF8);
    }


    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     */
    public static String doPost(String apiUrl, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setConfig(requestConfig);
        if (!isEmpty(params)) {
            httpPost.setEntity(new UrlEncodedFormEntity(transferParam(params), Charset.forName(UTF8)));
        }

        return execute(httpPost, buildHttpClient());
    }

    /**
     * 转换请求参数
     *
     * @param params 参数
     * @return 返回request参数
     */
    private static List<NameValuePair> transferParam(Map<String, String> params) {
        List<NameValuePair> pairList = new ArrayList<>(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                .getValue());
            pairList.add(pair);
        }
        return pairList;
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     */
    public static String doRetryPost(String apiUrl, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setConfig(requestConfig);
        if (!isEmpty(params)) {
            httpPost.setEntity(new UrlEncodedFormEntity(transferParam(params), Charset.forName(UTF8)));
        }

        return execute(httpPost, buildRetryHttpClient());
    }


    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     */
    public static String doPostPayKee(String apiUrl, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setConfig(requestConfig);
        if (!isEmpty(params)) {
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue() + "");
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(UTF8)));
        }

        return execute(httpPost, buildHttpClient());
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param json json对象
     */
    public static String doPost(String apiUrl, String json) {
        return doPost(apiUrl, json, UTF8);
    }

    /**
     * 发送 POST 请求，JSON形式
     *
     * @param apiUrl 请求地址
     * @param json 参数或数据
     * @param charset 编码格式
     * @return 请求响应
     */
    public static String doPost(String apiUrl, String json, String charset) {
        return doPost(apiUrl,json,charset,"application/json");
    }

    /**
     * 发送 POST 请求，JSON形式
     * @param apiUrl 请求地址
     * @param json 参数或数据
     * @param charset 编码格式
     * @return 请求响应
     */
    public static String doPost(String apiUrl, String json, String charset ,String contentType) {
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setConfig(requestConfig);
        //解决中文乱码问题
        StringEntity stringEntity = new StringEntity(json, charset);
        stringEntity.setContentEncoding(charset);
        stringEntity.setContentType(contentType);
        httpPost.setEntity(stringEntity);
        return execute(httpPost, buildHttpClient());
    }


    /**
     * post请求
     * 带请求头
     *
     * @return [参数说明]
     */
    public static String doPost(String apiUrl, Map<String, String> headers, String json) {
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setConfig(requestConfig);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }
        //解决中文乱码问题
        if (StringUtil.isNotTrimEmpty(json)) {
            StringEntity stringEntity = new StringEntity(json, UTF8);
            stringEntity.setContentEncoding(UTF8);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
        }
        return execute(httpPost, buildHttpClient());
    }


    /**
     * post请求
     * 带请求头
     *
     * @return [参数说明]
     */
    public static String doPost(String apiUrl, Map<String, String> headers, Map<String, Object> params) {
        if (isEmpty(params)) {
            return null;
        }
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setConfig(requestConfig);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }
        List<NameValuePair> pairList = new ArrayList<>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue() + "");
            pairList.add(pair);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(UTF8)));
        return execute(httpPost, buildHttpClient());
    }


    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param params json对象
     */
    public static String doPost(String apiUrl, List<NameValuePair> params) {
        if (isEmpty(params)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setConfig(requestConfig);
            //解决中文乱码问题
            UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(params, UTF8);
            httpPost.setEntity(urlEntity);
            return execute(httpPost, buildHttpClient());
        } catch (UnsupportedEncodingException e) {
            Log.error("http request post is error ", e);
        }
        return null;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     * @param params json对象
     */
    public static String doPostFiles(String apiUrl, Map<String, String> headers, Map<String, File> params) {
        if (isEmpty(params)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setConfig(requestConfig);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            for(String key :params.keySet()){
                builder.addBinaryBody(key,params.get(key));
            }
            HttpEntity entity =  builder.build();
            httpPost.setEntity(entity);
            return execute(httpPost, buildHttpClient());
        } catch (Exception e) {
            Log.error("http request post is error ", e);
        }
        return null;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     * @param params json对象
     */
    public static String doPostFiles(String apiUrl, Map<String, File> params) {
        if (isEmpty(params)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setConfig(requestConfig);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            for(String key :params.keySet()){
                builder.addBinaryBody(key,params.get(key));
            }
            HttpEntity entity =  builder.build();
            httpPost.setEntity(entity);
            return execute(httpPost, buildHttpClient());
        } catch (Exception e) {
            Log.error("http request post is error ", e);
        }
        return null;
    }

    /**
     * 创建http请求
     */
    public static CloseableHttpClient buildHttpClient() {
        if (null == httpsClientBuilder) {
            httpsClientBuilder = HttpClients.custom().setConnectionManager(connMgr);
        }
        return httpsClientBuilder.build();
    }

    /**
     * 判断是否为空
     *
     * @param map 参数
     * @return 返回值
     */
    private static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断是否为空
     *
     * @param list 参数
     * @return 返回值
     */
    private static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 创建http请求
     */
    public static CloseableHttpClient buildRetryHttpClient() {
        if (null == httpRetryClientBuilder) {
            httpRetryClientBuilder = HttpClients.custom().setConnectionManager(connMgr)
                .setRetryHandler((exception, executionCount, context) -> {
                    if (executionCount > 3) {
                        Log.warn("Maximum tries reached for client http pool ");
                        return false;
                    }
                    //NoHttpResponseException 重试
                    //连接超时重试
                    if (exception instanceof NoHttpResponseException
                        || exception instanceof ConnectTimeoutException) {
                        //              || exception instanceof SocketTimeoutException    //响应超时不重试，避免造成业务数据不一致
                        try {
                            Thread.sleep(SLEEP_TIME);
                        } catch (InterruptedException e) {
                            Log.error("", e);
                        }
                        Log.warn("NoHttpResponseException/ConnectTimeoutException on " + executionCount + " call");
                        return true;
                    }
                    return false;
                });
        }
        return httpRetryClientBuilder.build();
    }

}