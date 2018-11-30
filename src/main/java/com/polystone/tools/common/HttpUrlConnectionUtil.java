package com.polystone.tools.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.io.IOUtils;

/**
 * http请求
 *
 * @author jimmy
 * @version V1.0, 2018/11/19
 * @copyright
 */
public class HttpUrlConnectionUtil {

    private static final String HTTPS = "https://";
    private static final String HTTPS_UTF8 = "utf-8";
    private static final int CONNECT_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 30000;
    /**
     * 忽视证书HostName
     */
    private static HostnameVerifier ignoreHostnameVerifier = (String s, SSLSession sslsession) -> true;

    /**
     * Ignore Certification
     */
    private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {
        private X509Certificate[] certificates;

        @Override
        public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = certificates;
            }

        }

        @Override
        public void checkServerTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = ax509certificate;
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    /**
     * 获取UrlConn
     *
     * @param urlStr 参数
     * @return 返回值
     * @throws Exception 异常
     */
    public static URLConnection buildURLConnection(String urlStr)
        throws IOException, NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL(urlStr);
        if (urlStr.startsWith(HTTPS)) {
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection();

            // Prepare SSL Context
            TrustManager[] tm = {ignoreCertificationTrustManger};
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            httpsConnection.setSSLSocketFactory(ssf);

            return httpsConnection;
        } else {
            return url.openConnection();
        }
    }

    /**
     * 执行get请求
     *
     * @param url 参数
     * @param params 参数
     * @return 返回值
     * @throws Exception 异常
     */
    public static String doGet(String url, Map<String, String> params) throws Exception {
        return doGet(url + "?" + buildRequestData(params, HTTPS_UTF8), HTTPS_UTF8);
    }

    /**
     * 执行post请求
     *
     * @param url 参数
     * @param params 参数
     * @return 返回值
     * @throws Exception 异常
     */
    public static String doPost(String url, Map<String, String> params) throws Exception {
        return doPost(url, params, HTTPS_UTF8);
    }

    /**
     * 使用get请求方式请求数据
     *
     * @param url 请求数据的URL
     * @return 返回请求返回值
     */
    public static String doGet(String url, String encode) throws Exception {
        if (StringUtil.isTrimEmpty(url)) {
            return null;
        }
        InputStream inputStream = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) buildURLConnection(url);
            //设置连接设置
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                inputStream = conn.getInputStream();
                return IOUtils.toString(inputStream, encode);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }


    /**
     * 使用post 的方式进行数据的请求
     *
     * @param strUrlPath 请求数据路径
     * @param params 请求体参数
     * @param encode 编码格式
     * @return 返回String
     */
    public static String doPost(String strUrlPath, Map<String, String> params, String encode) throws Exception {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) buildURLConnection(strUrlPath);
            //设置连接超时时间
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoInput(true);
            //打开输出流，以便向服务器提交数据
            httpURLConnection.setDoOutput(true);
            //设置以Post方式提交数据
            httpURLConnection.setRequestMethod("POST");

            //获得输出流，向服务器写入数据
            outputStream = httpURLConnection.getOutputStream();
            IOUtils.write(buildRequestData(params, encode), outputStream, encode);

            //获得服务器的响应码
            if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()) {
                inputStream = httpURLConnection.getInputStream();
                //处理服务器的响应结果
                return IOUtils.toString(inputStream, encode);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }

    /**
     * 封装请求体信息
     *
     * @param params 请求体 参数
     * @param encode 编码格式
     * @return 返回封装好的StringBuffer
     */
    public static String buildRequestData(Map<String, String> params, String encode)
        throws UnsupportedEncodingException {
        //存储封装好的请求体信息
        StringBuffer stringBuffer = new StringBuffer();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey())
                .append("=")
                .append(URLEncoder.encode(entry.getValue(), encode))
                .append("&");
        }
        //删除最后的一个"&"
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("ip", "63.223.108.42");
//        map.put("pwd", "1992525");
//        map.put("userName", "18051523775");
        try {
            System.out.println(doGet("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15601508292", "gbk"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
