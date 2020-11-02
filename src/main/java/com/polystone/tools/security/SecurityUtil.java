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

    public static void main(String[] args) {
        //{"hmt":"1497866842"}
        String str = getInstance().decryptAES("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","9IJCgtVU94d00nBD");
        System.out.println(str);
        str = getInstance().encryptAES("{\"hmt\":\"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCLHcu4MkRGVnLUr7g0wsGwMMz6e8tt7DOZQP1Y2k2qIxHm1cl1H4W9Brkg83ugdy6heXOsU80MUzgFFfKHUdVXV7ckYT5jyLBaCJdY4Mm+BOrnFEFCkWLnvQMKyuE/0kVZ1WMhUPreJWX6hniFJ+ogu9Pzfi+8vxDjKZQFgNSWEJgasmAEtqMTCsU20DYExf/peH1jmwLvCOLy5kXHkweuwNTjDK6wHoXso97kMQdLbXpmqlD28/Cbf56PrHANY+rn+q7tzZtayEdL2QZ8t1o2kzbghCYVEyF7RgLDpCazilS49DW6eeerU+6uPc4rLg3AI+Iq6hJCUfLiku5nc4XxAgMBAAECggEAF898u8UWvHc1ZkPBIn6nChIAM2sW0XSZDET8V8Xdfx5LhRfYSEyh5k2NSB+EbF2ZxJd1/evF77MapnwwK4sNGcrwlLB0Lj3+ORq5VMP8o6JR1kU4Oj2KESHfsGwzC2pg5lRIiD5FAdqizrNPU3MWdo3PZCCX6RVGvaqzJBH90MU7t+vMFKXftMYqZhrAayD3Z7vGerk9qtfzGFQ6PVhv3SUR61zknDvvf8KDEpE70+sw2aWOSWP+qCIXlcLyj9jybWizF//UQBTgw53fZxR2UrJPefUaZ1IxKnE7LMivsuBQAW3jFK3H2YgH7DZVGZb0/ip7WXwssS2Q2yJ8ZXkm5QKBgQC/gT6hE2/9a1XAHxpIes3BHOG750ds2bXuPFgvMQvcRPMiBqz2GWaHhiahFqd/QQWp302VOZWF0zRIps7+K5UvlevB6+XzR/5g7uNA+Y6jWW+AWAWiux9lmmbg6qnQuaNDBlx7Yb+98lfFgmF7fgTR4YDolYILacGV5BNaRQASJwKBgQC599WQJW0p1AgFeXS8IYZwrFYpy8FUrA12ebnHxNHFhTcADzoKraprosNg+HWZLUcjVR7iyk2BKxk/IthHkuYG+BD35pC4TV8AtjoltdP87N9g5aR1a4MGeH7IyzgVH+bdzh57r5fB5VrLncjk1V9+Ey+eRYQAyjm6b7CuOLxuJwKBgDRv1k9SvDJ+5m8B1TeNcLN1DhZegL88acV+/oKFgoLg2lYxz8BfOiTXxdIMGvcMPdnCzEKVDu0i5MkT68/K7UGnYnOp/T/9MZknMFDFRJpTzeUttPVXGYS/n3Xsq2z8t8iNgFtEa7U+G2x4g+rFmdpCTgLhlUc8wQYi+m2YQSm3AoGAN0vhHcZzKi/Y3r/ZnSMe1Zv41t62MtYh/qt9OVBly6Sr0QG2EXzIIs835zqZxR04fOIPmAHtf83v6N0UkFu2uDXE3eGaAKNI724/5fyl9xv749UEzMFWKXNkx5HdsDJXNHbYi5CPwGDRWaAmHxzvEw7jZt1mvdFjzvLtBzDpnpsCgYAdokhPzQEqgy4Up3No2yLj/z1a4WYW/3n5BIQo1fHOwP7D7S0gu+wm6Hmhk1fsjRSX9i8FjVl0yeM7jGPqTDHLIy9Cd0XWGdDi56avoqGL2NSz7pSZDn5uQ73VLandY62PNgu8zxpM2x4s8FqwVj+jTeFCICFoXWNETM+4HN0SQw==\",\"comm\":\"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCLHcu4MkRGVnLUr7g0wsGwMMz6e8tt7DOZQP1Y2k2qIxHm1cl1H4W9Brkg83ugdy6heXOsU80MUzgFFfKHUdVXV7ckYT5jyLBaCJdY4Mm+BOrnFEFCkWLnvQMKyuE/0kVZ1WMhUPreJWX6hniFJ+ogu9Pzfi+8vxDjKZQFgNSWEJgasmAEtqMTCsU20DYExf/peH1jmwLvCOLy5kXHkweuwNTjDK6wHoXso97kMQdLbXpmqlD28/Cbf56PrHANY+rn+q7tzZtayEdL2QZ8t1o2kzbghCYVEyF7RgLDpCazilS49DW6eeerU+6uPc4rLg3AI+Iq6hJCUfLiku5nc4XxAgMBAAECggEAF898u8UWvHc1ZkPBIn6nChIAM2sW0XSZDET8V8Xdfx5LhRfYSEyh5k2NSB+EbF2ZxJd1/evF77MapnwwK4sNGcrwlLB0Lj3+ORq5VMP8o6JR1kU4Oj2KESHfsGwzC2pg5lRIiD5FAdqizrNPU3MWdo3PZCCX6RVGvaqzJBH90MU7t+vMFKXftMYqZhrAayD3Z7vGerk9qtfzGFQ6PVhv3SUR61zknDvvf8KDEpE70+sw2aWOSWP+qCIXlcLyj9jybWizF//UQBTgw53fZxR2UrJPefUaZ1IxKnE7LMivsuBQAW3jFK3H2YgH7DZVGZb0/ip7WXwssS2Q2yJ8ZXkm5QKBgQC/gT6hE2/9a1XAHxpIes3BHOG750ds2bXuPFgvMQvcRPMiBqz2GWaHhiahFqd/QQWp302VOZWF0zRIps7+K5UvlevB6+XzR/5g7uNA+Y6jWW+AWAWiux9lmmbg6qnQuaNDBlx7Yb+98lfFgmF7fgTR4YDolYILacGV5BNaRQASJwKBgQC599WQJW0p1AgFeXS8IYZwrFYpy8FUrA12ebnHxNHFhTcADzoKraprosNg+HWZLUcjVR7iyk2BKxk/IthHkuYG+BD35pC4TV8AtjoltdP87N9g5aR1a4MGeH7IyzgVH+bdzh57r5fB5VrLncjk1V9+Ey+eRYQAyjm6b7CuOLxuJwKBgDRv1k9SvDJ+5m8B1TeNcLN1DhZegL88acV+/oKFgoLg2lYxz8BfOiTXxdIMGvcMPdnCzEKVDu0i5MkT68/K7UGnYnOp/T/9MZknMFDFRJpTzeUttPVXGYS/n3Xsq2z8t8iNgFtEa7U+G2x4g+rFmdpCTgLhlUc8wQYi+m2YQSm3AoGAN0vhHcZzKi/Y3r/ZnSMe1Zv41t62MtYh/qt9OVBly6Sr0QG2EXzIIs835zqZxR04fOIPmAHtf83v6N0UkFu2uDXE3eGaAKNI724/5fyl9xv749UEzMFWKXNkx5HdsDJXNHbYi5CPwGDRWaAmHxzvEw7jZt1mvdFjzvLtBzDpnpsCgYAdokhPzQEqgy4Up3No2yLj/z1a4WYW/3n5BIQo1fHOwP7D7S0gu+wm6Hmhk1fsjRSX9i8FjVl0yeM7jGPqTDHLIy9Cd0XWGdDi56avoqGL2NSz7pSZDn5uQ73VLandY62PNgu8zxpM2x4s8FqwVj+jTeFCICFoXWNETM+4HN0SQw==\"}","9IJCgtVU94d00nBD");
        System.out.println(str);
        str = getInstance().decryptAES(str,"9IJCgtVU94d00nBD");
        System.out.println(str);
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
     * 校验数字签名
     *
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     */
    public boolean verify(byte[] data, String publicKey, String sign ,String codeMethod) {
        try {
            byte[] keyBytes = Base64.decodeBase64(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicK = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(codeMethod);
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
