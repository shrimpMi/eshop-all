package com.polystone.tools.common;

import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 参数验证工具
 * 
 * @author johnliu
 * @version V1.0, May 16, 2016
 * @since [产品/模块版本]
 * @secret {秘密}
 */
public class ValidateUtil {

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "0?(13|14|15|16|18|17|19)[0-9]{9}";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    
    /**
     * 正则表达式： 验证办公电话和传真
     */
    public static final String REGEX_OFFICE_PHONE = "^\\d{3,4}-\\d{7,8}(-\\d{1,4}){0,2}$";
    

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]+$";

    /**
     * 正则表达式：验证非汉字
     */
    public static final String REGEX_UNCHINESE = "^[^\u4e00-\u9fa5]+$";
    
    /**
     * 身份证日期正则表达式
     */
    public static final String REGEX_ID_DATE = "\\d{2}((0[1-9])|(1[0-2]))((0[1-9])|([1-2]\\d)|(3[0-1]))";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "\\d{6}((((19)|(20))" + REGEX_ID_DATE + "\\d{3}[\\d|xX])|(" + REGEX_ID_DATE + "\\d{3}))";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 正则表达式：验证特殊字符串
     */
    public static final String REGEX_SPECIAL_STRING = "^[^`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]+$";
    
    /**
     * 正则表达式：日期
     */
    public static final String REGEX_DATE = "^[1-9]\\d{3}-[0-1]?\\d-[0-3]?\\d$";

    /**
     * 验证密码（6到20位必须字母或字母+数字）
     */
    public static final String REGEX_PASSWORD_0 = "^(?![0-9]*$)[a-zA-Z0-9]{6,20}$";
    
    /**
     * 性别选择范围
     * 1： 男
     * 2: 女
     * 3: 保密
     */
    public static final int[] SEXES = {1, 2, 3};
    
    /**
     * 身份证号加权因子
     */
    public static final int[] ID_WI = {
    		7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
    };
    
    /**
     * 身份证号 mod11,对应校验码字符值
     */
    public static final char[] ID_VALIDATE={ '1','0','X','9','8','7','6','5','4','3','2'};

    /**
     * 交易密码
     */
    public static final String  TRANSACTION_PASSWORD ="^\\d{6}$";
    
    

    /**
     * 判断Integer是否为null和大于0
     * 
     * @param soure
     * @return [说明]
     */
    public static boolean isIntegerGreaterThan0(Long soure) {
        return soure == null ? false : (soure > 0 ? true : false);
    }

    /**
     * 验证是否包含特殊字符串
     * 
     * @param str
     * @return [说明]
     */
    public static boolean isSpecialString(String str) {
        return !Pattern.matches(REGEX_SPECIAL_STRING, str);
    }

    /**
     * 校验密码
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isLoginPwd(String password) {
        return Pattern.matches(REGEX_PASSWORD_0, password);
    }

    /**
     * 校验交易密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isTradePwd(String password) {
        return Pattern.matches(TRANSACTION_PASSWORD, password);
    }

    /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     * 
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
    
    /**
     * 是否是办公电话或传真
     * @param officePhone
     * @return  [参数说明]
     */
    public static boolean isOfficePhone(String officePhone){
    	return Pattern.matches(REGEX_OFFICE_PHONE, officePhone);
    }

    /**
     * 校验汉字
     * 
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验非汉字
     * 
     * @param unchinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUnChinese(String unchinese) {
        if (StringUtil.isEmpty(unchinese)) {
            return true;
        }
        return Pattern.matches(REGEX_UNCHINESE, unchinese);
    }

    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        boolean flag = Pattern.matches(REGEX_ID_CARD, idCard);
        if(flag){
        	if(idCard.length() == 18){
        		//18位身份证
        		flag = validateIdentity(idCard);
        	}
        }
        return flag;
    }

    /**
     * 校验URL
     * 
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     * 
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 
     * 验证String参数非空以及长度
     * 
     * @param param
     * @param size
     * @param isEmpty
     *            true 验证非空，false 无需验证非空
     * @return [说明]
     */
    public static boolean validateString(String param, int size, boolean isEmpty) {
        if (StringUtils.length(param) <= size) {
            return isEmpty ? (StringUtils.length(param) > 0 ? true : false) : true;
        }
        return false;
    }

    /**
     * 
     * 验证Long参数非空以及长度
     *
     * @param isEmpty
     *            true 验证非空，false 无需验证非空
     * @return [说明]
     */
    public static boolean validateLong(Long param, boolean isEmpty) {
        if (isEmpty) {
            return param == null ? false : (param > 0 ? true : false);
        }
        return param == null ? true : (param > 0 ? true : false);

    }

    /**
     * 验证Integer参数非空
     * 
     * @param param
     * @param isEmpty
     *            true 验证非空，false 无需验证非空
     * @return [说明]
     */
    public static boolean validateInteger(Integer param, boolean isEmpty) {
        if (isEmpty) {
            return param == null ? false : (param > 0 ? true : false);
        }
        return param == null ? true : (param > 0 ? true : false);
    }
    
    /**
     * 参数1  是否大于参数2
     * @param param1
     * @param param2
     * @return [参数说明]
     */
    public static  boolean  validateLargeThan(Integer param1,Integer param2) {
         return (param1>=param2)?true:false;
    }
    

    /**
     * 验证Short参数非空
     * 
     * @param param
     * @param isEmpty
     *            true 验证非空，false 无需验证非空
     * @return [说明]
     */
    public static boolean validateShort(Short param, boolean isEmpty) {
        if (isEmpty) {
            return param == null ? false : (param > 0 ? true : false);
        }
        return param == null ? true : (param > 0 ? true : false);
    }

    /**
     * 验证性别是否有效
     * 
     * @param param
     * @param isEmpty
     *            true 验证非空，false 无需验证非空
     * @return [说明]
     */
    public static boolean validateSex(Integer param, boolean isEmpty){
    	 if (isEmpty) {
             return param == null ? false : (ArrayUtils.indexOf(SEXES, param) >= 0 ? true : false);
         }
    	 return param == null ? true : (ArrayUtils.indexOf(SEXES, param) >= 0  ? true : false);
    }
    
    /**
     * 校验日期
     * 
     * @param data
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isData(String data) {
        return Pattern.matches(REGEX_DATE, data);
    }
    
    /**
     * 获取身份证的校验码
     * @return  [参数说明]
     */
    private static char getValidateCode(String id17){
          int sum=0;
          int mode=0;
          for(int i=0;i<Math.min(17, id17.length());i++){
        	  sum=sum+Integer.parseInt(String.valueOf(id17.charAt(i)))*ID_WI[i];
          }
         mode=sum%11;
         return ID_VALIDATE[mode];
     }
    
    /**
     * 验证身份证号码
     * 
     * @param id
     * @return  [参数说明]
     */
    public static boolean validateIdentity(String id){
    	char code = getValidateCode(id);
    	char value = id.charAt(17);
    	return value == code;
    }
    
}
