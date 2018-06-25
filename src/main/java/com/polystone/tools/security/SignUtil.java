package com.polystone.tools.security;

import com.alibaba.fastjson.JSON;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * url参数防篡改
 *
 * @author jimmy
 * @version V1.0, 2017/7/14
 * @copyright
 */
public class SignUtil {

    private static Logger LOG = LoggerFactory.getLogger(SignUtil.class);

    /**
     * 支付平台MD5签名用的key
     */
    private static final String PAYMENT_MD5_KEY = "AsguYkh451Qx4J9AQFLZ8gK8HPFPNf";
    /**
     * 用户签名key
     */
    private static final String USER_SIGN = "sign";

    /**
     * 参数签名
     *
     * @param params url参数
     * @return 签名
     */
    public static String _signature(Map<String, String> params) {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<>(params);

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder baseString = new StringBuilder();
        for (Entry<String, String> param : sortedParams.entrySet()) {
            baseString.append(param.getKey()).append("=").append(param.getValue());
        }
        baseString.append(PAYMENT_MD5_KEY);
        LOG.info("签名参数==" + baseString.toString());
        // 使用MD5对待签名串求签
        return SecurityUtil.getInstance().md5(baseString.toString());
    }


    /**
     * 验证参数签名
     *
     * @param params 请求参数
     * @return 签名是否正确
     */
    public static boolean signature(Map<String, String> params) {
        LOG.info(JSON.toJSONString(params));
        if (!validate(params)) {
            return false;
        }
        String sign = params.get(USER_SIGN);
        params.remove(USER_SIGN);
        String mad5Sign = _signature(params);
        LOG.info("签名sign" + mad5Sign);
        LOG.info("sign参数" + sign);
        return sign.equals(mad5Sign);
    }

    /**
     * 验证参数
     *
     * @param params 参数
     */
    private static boolean validate(Map<String, String> params) {
        //判断是否包含签名要素
        if (!params.containsKey(USER_SIGN)) {
            return false;
        }
//        if (!params.containsKey(Constants.USER_SIGN_TIME)) {
//            throw new I18NIllegalArgumentException("unsign");
//        }
        return true;
    }

    /**
     * rsa签名验证
     *
     * @param params 参数
     * @param privateKey 私钥
     * @param publicKey 公钥
     * @return 是否通过
     */
    public static boolean signatureRSA(Map<String, String> params, String privateKey, String publicKey) {
        if (!validate(params)) {
            return false;
        }
        String sign = params.get(USER_SIGN);
        params.remove(USER_SIGN);
        String check = _signature(params);
        byte[] data = SecurityUtil.getInstance()
            .sign(check.getBytes(Charset.forName(SecurityUtil.UTF8)), privateKey);

        return SecurityUtil.getInstance().verify(data, publicKey, sign);
    }


}
