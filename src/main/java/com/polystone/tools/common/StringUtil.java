package com.polystone.tools.common;

import com.polystone.tools.security.SecurityUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

/**
 * String工具类
 *
 * @author garry
 * @version V1.0, 2016年8月4日
 * @secret {秘密}
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class StringUtil {

    /**
     * 判断为空
     *
     * @return [说明]
     */
    public static boolean isEmpty(String param) {
        return null == param || param.length() == 0;
    }

    public static boolean isEmptyTrim(String str) {
        return null == str || str.trim().length() == 0;
    }

    public static boolean isEmpty(Object param) {
        return null == param;
    }

    /**
     * 为空的则给出默认值
     *
     * @return [参数说明]
     */
    public static String isDefaultString(Object param, String pattern) {
        String str = "";
        if (isEmpty(param)) {
            str = (pattern == null ? "" : pattern);
        } else {
            str = param.toString();
        }
        return str;
    }

    /**
     * 判断非空
     *
     * @return [说明]
     */
    public static boolean isNotEmpty(String param) {
        return !isEmpty(param);
    }

    /**
     * 剔除空字符串后是否为空
     *
     * @param s_param 字符串
     */
    public static boolean isTrimEmpty(String s_param) {
        return null == s_param || s_param.trim().length() == 0;
    }

    /**
     * 剔除空字符串后是否不为空
     *
     * @param s_param 字符串
     */
    public static boolean isNotTrimEmpty(String s_param) {
        return !isTrimEmpty(s_param);
    }

    /**
     * 是否匹配
     *
     * @param s_param 字符串
     */
    public static boolean isPattern(String regex, String s_param) {
        return null != s_param && Pattern.matches(regex, s_param);
    }

    /**
     * 格式化姓名 王**
     *
     * @return [说明]
     */
    public static String nameFormat(String param) {
        if (isTrimEmpty(param)) {
            return param;
        }
        StringBuilder buff = new StringBuilder(param.substring(0, 1));
        buff.append("***");

        return buff.toString();
    }

    /**
     * 手机号格式化 例如；135****5132
     *
     * @return [说明]
     */
    public static String phoneFormat(String param) {
        int len = param.length();
        if (len != 11) {
            return param;
        }
        StringBuilder buff = new StringBuilder(param.substring(0, 3));
        for (int i = 0; i < 4; i++) {
            buff.append('*');
        }
        buff.append(param.substring(7, 11));
        return buff.toString();
    }

    /**
     * 右补0 例如 ：0005
     *
     * @return [说明]
     */
    public static String formatZore(int num, int len) {
        char[] dest = new char[len];
        char[] src = Integer.toString(num).toCharArray();
        int pos = len - src.length;
        System.arraycopy(src, 0, dest, pos, len - pos);
        for (int i = 0; i < pos; i++) {
            dest[i] = '0';
        }
        return new String(dest);
    }

    /**
     * 使用utf-8对参数进行URL编码
     *
     * @return [说明]
     */
    public static String urlEncode(String param) {
        try {
            return URLEncoder.encode(param, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return param;
        }
    }

    /**
     * 使用utf-8对参数进行URL解码
     *
     * @return [说明]
     */
    public static String urlDecode(String param) {
        try {
            return URLDecoder.decode(param, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return param;
        }
    }

    /**
     * 转码
     *
     * @param param 字符串
     * @return 字节
     */
    public static byte[] encode(String param) {
        return param.getBytes(Charset.forName("utf-8"));
    }

    /**
     * 字节转为字符串
     *
     * @param param 字节
     * @return 字符串
     */
    public static String decode(byte[] param) throws UnsupportedEncodingException {
        return new String(param, "utf-8");
    }


    /**
     * 获取uuid
     *
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * base64位
     */
    public static String decodeBase64(final byte[] bytes) {
        String str = new String(Base64.decodeBase64(bytes));
        return str;
    }

    /**
     * 二进制数据编码为BASE64字符串
     */
    public static String encodeBase64(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    /**
     * 将字符串转成unicode
     *
     * @param str 待转字符串
     * @return unicode字符串
     */
    public static String convertToUnicode(String str) {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            sb.append("\\u");
            j = (c >>> 8); // 取出高8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
            j = (c & 0xFF); // 取出低8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);

        }
        return (new String(sb));
    }

    /**
     * 将unicode 字符串
     *
     * @param str 待转字符串
     * @return 普通字符串
     */
    public static String unicodeToString(String str) {
        str = (str == null ? "" : str);
        if (str.indexOf("\\u") == -1) {// 如果不是unicode码则原样返回
            return str;
        }
        StringBuffer sb = new StringBuffer(1000);

        for (int i = 0; i < str.length() - 6; ) {
            String strTemp = str.substring(i, i + 6);
            String value = strTemp.substring(2);
            int c = 0;
            for (int j = 0; j < value.length(); j++) {
                char tempChar = value.charAt(j);
                int t = 0;
                switch (tempChar) {
                    case 'a':
                        t = 10;
                        break;
                    case 'b':
                        t = 11;
                        break;
                    case 'c':
                        t = 12;
                        break;
                    case 'd':
                        t = 13;
                        break;
                    case 'e':
                        t = 14;
                        break;
                    case 'f':
                        t = 15;
                        break;
                    default:
                        t = tempChar - 48;
                        break;
                }

                c += t * ((int) Math.pow(16, (value.length() - j - 1)));
            }
            sb.append((char) c);
            i = i + 6;
        }
        return sb.toString();
    }

    /**
     * 对字符串处理:将指定位置到指定位置的字符以星号代替
     *
     * @param content 传入的字符串
     * @param begin 开始位置
     * @param end 结束位置
     */
    public static String getStarString(String content, int begin, int end) {

        if (begin >= content.length() || begin < 0) {
            return content;
        }
        if (end >= content.length() || end < 0) {
            return content;
        }
        if (begin >= end) {
            return content;
        }
        String starStr = "";
        for (int i = begin; i < end; i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, begin) + starStr + content.substring(end, content.length());

    }

    /**
     * 下划线转驼峰式
     *
     * @return user_name --> userName
     */
    public static String toCamelCase(String key) {
        if (isEmptyTrim(key)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int flag = 0;
        for (int i = 0; i < key.length(); i++) {
            char a = key.charAt(i);
            if (a == '_') {
                flag = 1;
            } else {
                if (flag == 1 && a >= 'a') {
                    a = (char) (a - 32);
                }
                sb.append(a);
                flag = 0;
            }
        }
        return sb.toString();
    }

    /**
     * 获取用户token
     *
     * @param str 字符串
     * @return token
     */
    public static String createUserToken(String str) {
        return SecurityUtil.getInstance().md5_16(str + System.currentTimeMillis());
    }

    /**
     * 异或
     *
     * @param content 传入的参数
     * @param key 异或秘钥 1024
     */
    public static String obfuscate(String content, int key) throws IOException {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < content.length(); i++) {
            int chDate = content.charAt(i);
            if (i % 2 == 1) {
                chDate = chDate ^ key;
            }
            result.append((char) (chDate));
        }

        BASE64Decoder decoder = new BASE64Decoder();
        return new String(decoder.decodeBuffer(result.toString()), "utf-8");
    }

    /**
     * 生成16位流水号
     *
     * @return 返回值
     */
    public static String generateSerialNum() {
        return System.currentTimeMillis() + RandomUtil.randomNumber(3);
    }
}
