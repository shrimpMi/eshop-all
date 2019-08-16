package com.polystone.tools.validate;

import com.polystone.tools.common.StringUtil;
import org.apache.commons.collections4.MapUtils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 类型转换工具
 *
 * @author jimmy
 * @version V1.0, 2016年8月4日
 * @secret {秘密}
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ParamUtil {

    public static boolean parseBool(Object param) {
        return parseBool(param, false);
    }


    public static Object mapDefVal(Map<String,Object> map, String key, Object def){
        if(map==null)return def;
        Object obj = map.get(key);
        if(obj==null)return def;
        return obj;
    }

    public static String mapStrVal(Map<String,Object> map, String key,String def ){
        if(map==null)return def;
        Object obj = map.get(key);
        if(obj==null)return def;
        return obj.toString();
    }


    /**
     * 转换布尔值
     *
     * @param def 默认值
     * @return [说明]
     */
    public static boolean parseBool(Object param, boolean def) {
        if (null == param) {
            return def;
        }
        if (param instanceof Boolean) {
            return (Boolean) param;
        } else if (param instanceof Integer) {
            return 1 == Integer.parseInt(param.toString());
        }
        return def;
    }

    /**
     * string转换int类型
     *
     * @return [说明]
     */
    public static int parseInt(String param) {
        return parseInt(param, 0);
    }

    /**
     * string转换int类型（失败返回默认值）
     *
     * @param def 默认值
     * @return [说明]
     */
    public static int parseInt(String param, int def) {
        try {
            return StringUtil.isNotEmpty(param) ? Integer.parseInt(param) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * object转换int类型
     *
     * @return [说明]
     */
    public static int parseInt(Object param) {
        return parseInt(param, 0);
    }

    /**
     * object转换int类型 （失败返回默认值）
     *
     * @param def 默认值
     * @return [说明]
     */
    public static int parseInt(Object param, int def) {
        try {
            return null != param ? Integer.parseInt(param.toString()) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * string转换为float
     *
     * @return [说明]
     */
    public static float parseFloat(String param) {
        return parseFloat(param, 0F);
    }

    /**
     * string 转换为float（失败返回默认值）
     *
     * @param def 默认值
     * @return [说明]
     */
    public static float parseFloat(String param, float def) {
        try {
            return StringUtil.isNotEmpty(param) ? Float.parseFloat(param) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * object转换为float
     *
     * @return [说明]
     */
    public static float parseFloat(Object param) {
        return parseFloat(param, 0F);
    }

    /**
     * object转换为float（失败返回默认值）
     *
     * @param def 默认值
     * @return [说明]
     */
    public static float parseFloat(Object param, float def) {
        try {
            return null != param ? Float.parseFloat(param.toString()) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * string转换long
     *
     * @return [说明]
     */
    public static long parseLong(String param) {
        return parseLong(param, 0L);
    }

    /**
     * string转换long（失败返回默认值）
     *
     * @param def 默认值
     * @return [说明]
     */
    public static long parseLong(String param, long def) {
        try {
            return StringUtil.isNotEmpty(param) ? Long.parseLong(param) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * object转换long
     *
     * @return [说明]
     */
    public static long parseLong(Object param) {
        return parseLong(param, 0L);
    }

    /**
     * object转换long （失败返回默认值）
     *
     * @param def 默认值
     * @return [说明]
     */
    public static long parseLong(Object param, long def) {
        try {
            return null != param ? Long.parseLong(param.toString()) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * string转换double
     *
     * @return [说明]
     */
    public static double parseDouble(String param) {
        return parseDouble(param, 0.0D);
    }

    /**
     * string转换double（失败返回默认值）
     *
     * @param def 默认值
     * @return [说明]
     */
    public static double parseDouble(String param, double def) {
        try {
            return StringUtil.isNotEmpty(param) ? Double.parseDouble(param) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * object转换double
     *
     * @return [说明]
     */
    public static double parseDouble(Object param) {
        return parseDouble(param, 0.0D);
    }

    /**
     * object转换double（失败返回默认值）
     *
     * @param def 默认值
     * @return [说明]
     */
    public static double parseDouble(Object param, double def) {
        try {
            return null != param ? Double.parseDouble(param.toString()) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * object转换string
     *
     * @return [说明]
     */
    public static String parseString(Object param) {
        return parseString(param, null);
    }

    /**
     * 去空格
     *
     * @return [说明]
     */
    public static String parseTrimString(String param) {
        return null != param ? param.trim() : null;
    }

    /**
     * 去空格
     *
     * @return [说明]
     */
    public static String parseTrimString(Object param) {
        return null != param ? param.toString().trim() : null;
    }

    /**
     * object转换string（null返回默认值）
     *
     * @param def 默认值
     * @return [说明]
     */
    private static String parseString(Object param, String def) {
        return null != param ? param.toString() : def;
    }

    /**
     * 转换为BigDecimal
     *
     * @param param 参数
     * @return 返回值
     */
    public static BigDecimal parseBigDecimal(String param) {
        try {
            return StringUtil.isNotEmpty(param) ? new BigDecimal(param) : BigDecimal.ZERO;
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 转换为BigDecimal
     *
     * @param param 参数
     * @return 返回值
     */
    public static BigDecimal parseBigDecimal(Integer param) {
        try {
            return new BigDecimal(param).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
}
