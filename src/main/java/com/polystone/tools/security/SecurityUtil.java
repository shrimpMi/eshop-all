package com.polystone.tools.security;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密类
 *
 * @author jimmy
 * @version V1.0, 2017/7/11
 * @copyright
 */
public class SecurityUtil {

    private static Logger LOG = LoggerFactory.getLogger(SecurityUtil.class);

    private static SecurityUtil instance;
    public static final String UTF8 = "UTF-8";
    private final String MD5 = "MD5";
    private final String SHA_1 = "SHA-1";
    private final String AES = "AES";
    private final String SHA1PRNG = "SHA1PRNG";
    private final String KEY_ALGORITHM = "RSA";
    private final String SIGNATURE_ALGORITHM = "MD5withRSA";
    //RSA最大加密明文大小
    private final int MAX_ENCRYPT_BLOCK = 117;
    //RSA最大解密密文大小
    private final int MAX_DECRYPT_BLOCK = 128;


    private SecurityUtil() {

    }

    public static SecurityUtil getInstance() {
        return null == instance ? instance = new SecurityUtil() : instance;
    }


    /**
     * AES加密
     */
    public String encryptAES(String str, String key) {
        try {
            byte[] result = initAESCipher(key, Cipher.ENCRYPT_MODE).doFinal(str.getBytes(UTF8));
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * AES解密
     */
    public String decryptAES(String str, String key) {
        try {
            byte[] result = initAESCipher(key, Cipher.DECRYPT_MODE).doFinal(Base64.decodeBase64(str));
            return new String(result, UTF8);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * md5 加密
     *
     * @param str 字符串
     * @return 字符串
     */
    public String md5(String str) {
        try {
            return md5(str, UTF8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 双次md5机密
     *
     * @return [参数说明]
     */
    public String doubleMd5(String str) {
        try {
            return md5(md5(str, UTF8), UTF8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public String doubleMd5(String str, String salt) {
        try {
            return md5(md5(str, UTF8) + salt, UTF8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }


    /**
     * md5 16位
     *
     * @return [参数说明]
     */
    public String md5_16(String str) {
        try {
            return md5(str, UTF8).substring(8, 24);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * md5 加密
     *
     * @param str 字符串
     * @param charset 编码格式
     * @return 字符串
     */
    public String md5(String str, String charset) throws UnsupportedEncodingException {
        return md5(str.getBytes(charset));
    }

    /**
     * md5 加密
     *
     * @param bytes byte数组
     * @return 字符串
     */
    public String md5(byte[] bytes) {
        return getInstance().toHex(MD5, bytes);
    }


    /**
     * sha1 加密
     *
     * @param str 字符串
     * @return 字符串
     */
    public String sha1(String str) {
        try {
            return sha1(str, UTF8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * sha1 加密
     *
     * @param str 字符串
     * @param charset 编码格式
     * @return 字符串
     */
    public String sha1(String str, String charset) throws UnsupportedEncodingException {
        return sha1(str.getBytes(charset));
    }

    /**
     * sha1 加密
     *
     * @param bytes byte数组
     * @return 字符串
     */
    public String sha1(byte[] bytes) {
        return getInstance().toHex(SHA_1, bytes);
    }

    /**
     * sha1 加密
     *
     * @param bytes byte数组
     * @return 加密后数据
     */
    public String sha1_36(byte[] bytes) {
        return getInstance().toHex(SHA_1, bytes, 36);
    }

    /**
     * 初始化 AES Cipher
     *
     * @throws Exception [参数1] [参数1说明]
     */
    public Cipher initAESCipher(String sKey, int cipherMode) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        // 防止linux下 随机生成key
        SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG);
        secureRandom.setSeed(sKey.getBytes());

        keyGenerator.init(128, secureRandom);
        Cipher cipher = Cipher.getInstance(AES);
        // 初始化
        cipher.init(cipherMode, new SecretKeySpec(keyGenerator.generateKey().getEncoded(), AES));
        return cipher;
    }

    /**
     * 转换为16进制
     */
    private String toHex(String algorithm, byte[] bytes) {
        return toHex(algorithm, bytes, 16);
    }

    private String toHex(String algorithm, byte[] bytes, int radix) {
        try {
            return toHex(encrypt(algorithm, bytes), radix);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 加密
     */
    private byte[] encrypt(String algorithm, byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(bytes);
        return md.digest();
    }

    /**
     * bytes 转 Hex
     *
     * @param bytes byte数组
     * @return 字符串
     */
    private String toHex(byte[] bytes, int radix) {
        StringBuilder buff = new StringBuilder();
        String str;
        for (int i = 0, len = bytes.length; i < len; i++) {
            str = Integer.toString(bytes[i] & 0xFF, radix);
            buff.append(str.length() == 1 ? 0 : "").append(str);
        }
        return buff.toString();
    }


    /**
     * 用私钥对信息生成数字签名
     *
     * @param data 已加密数据
     * @param privateKey 私钥(BASE64编码)
     */
    public byte[] sign(byte[] data, String privateKey) {
        return sign(data,privateKey,SIGNATURE_ALGORITHM);
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data 已加密数据
     * @param privateKey 私钥(BASE64编码)
     */
    public byte[] sign(byte[] data, String privateKey,String signMethod) {
        try {
            byte[] keyBytes = Base64.decodeBase64(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature signature = Signature.getInstance(signMethod);
            signature.initSign(privateK);
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            LOG.error("sign is error", e);
        }
        return null;
    }


    /**
     * 校验数字签名
     *
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     */
    public boolean verify(byte[] data, String publicKey, String sign) {
        try {
            byte[] keyBytes = Base64.decodeBase64(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicK = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicK);
            signature.update(data);
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            LOG.error("verify is error", e);
        }
        return false;
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return 解密后数据
     */
    public String decryptByPrivateKey(String encryptedData, String privateKey) {
        try {
            byte[] result = decryptByPrivateKey(Base64.decodeBase64(encryptedData), privateKey);
            return new String(result, UTF8);
        } catch (Exception e) {
            LOG.error("decryptByPrivateKey is error", e);
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     */
    public byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) {
        try {
            byte[] keyBytes = Base64.decodeBase64(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateK);

            return _doFinal(cipher, encryptedData, MAX_DECRYPT_BLOCK);
        } catch (Exception e) {
            LOG.error("decryptByPrivateKey is error", e);
        }
        return null;
    }

    /**
     * 处理数据
     *
     * @param cipher cipher
     * @param encryptedData 数据
     * @return 处理后数据
     */
    private byte[] _doFinal(Cipher cipher, byte[] encryptedData, int LIMIT) {
        ByteArrayOutputStream out = null;
        try {
            int inputLen = encryptedData.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > LIMIT) {
                    cache = cipher.doFinal(encryptedData, offSet, LIMIT);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * LIMIT;
            }
            return out.toByteArray();
        } catch (Exception e) {
            LOG.error("_doFinal is error", e);
            return null;
        } finally {
            IOUtils.closeQuietly(out);
        }

    }

    /**
     * 公钥解密
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return 解密后数据
     */
    public String decryptByPublicKey(String encryptedData, String publicKey) {
        try {
            byte[] result = decryptByPublicKey(Base64.decodeBase64(encryptedData), publicKey);
            return new String(result, UTF8);
        } catch (Exception e) {
            LOG.error("decryptByPublicKey is error", e);
        }
        return null;
    }

    /**
     * 公钥解密
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     */
    public byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) {
        try {
            byte[] keyBytes = Base64.decodeBase64(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicK);

            return _doFinal(cipher, encryptedData, MAX_DECRYPT_BLOCK);
        } catch (Exception e) {
            LOG.error("decryptByPublicKey is error", e);
        }
        return null;
    }


    /**
     * 公钥加密
     *
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     */
    public String encryptByPublicKey(String data, String publicKey) {
        try {
            byte[] result = encryptByPublicKey(Base64.decodeBase64(data), publicKey);
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            LOG.error("encryptByPublicKey is error", e);
        }
        return null;
    }

    /**
     * 公钥加密
     *
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     */
    public byte[] encryptByPublicKey(byte[] data, String publicKey) {
        try {
            byte[] keyBytes = Base64.decodeBase64(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicK);

            return _doFinal(cipher, data, MAX_ENCRYPT_BLOCK);
        } catch (Exception e) {
            LOG.error("encryptByPublicKey is error", e);
        }
        return null;
    }

    /**
     * 私钥加密
     *
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return 加密后数据
     */
    public String encryptByPrivateKey(String data, String privateKey) {
        try {
            byte[] result = encryptByPrivateKey(Base64.decodeBase64(data), privateKey);
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            LOG.error("encryptByPrivateKey is error", e);
        }
        return null;
    }

    /**
     * 私钥加密
     *
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     */
    public byte[] encryptByPrivateKey(byte[] data, String privateKey) {
        try {
            byte[] keyBytes = Base64.decodeBase64(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateK);

            return _doFinal(cipher, data, MAX_ENCRYPT_BLOCK);
        } catch (Exception e) {
            LOG.error("encryptByPrivateKey is error", e);
        }
        return null;
    }

    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param str 加密后的报文
     */
    public String getSHA256Str(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            LOG.error("getSHA256Str is error", e);
        } catch (UnsupportedEncodingException e) {
            LOG.error("getSHA256Str is error", e);
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     */
    public String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static String hexStringToBytes(String hexString,String chartset) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        try {
            return new String(d,chartset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
