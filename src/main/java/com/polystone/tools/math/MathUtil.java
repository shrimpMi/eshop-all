package com.polystone.tools.math;

import com.polystone.tools.validate.ParamUtil;
import java.math.BigDecimal;
import java.util.Random;

/**
 * 小数工具类
 *
 * @author jimmy
 * @version V1.0, 2017/7/11
 * @copyright
 */
public class MathUtil {

    private static final Random rand = new Random();

    /**
     * 取数值的1/2（四舍五入）
     *
     * @param num 数字
     * @return [说明]
     */
    public static int half(int num) {
        return num > 0 ? (int) Math.round(num / 2F) : 0;
    }

    /**
     * double的四舍五入取整
     *
     * @param num 小数
     * @return [说明]
     */
    public static long round(Double num) {
        return Math.round(num);
    }

    /**
     * double的上取整
     *
     * @param num 小数
     * @return [说明]
     */
    public static long ceil(Double num) {
        return (long) Math.ceil(num);
    }

    /**
     * 获取n_minNum～n_maxNum（包括n_minNum，n_maxNum） 区间内的随机数
     *
     * @return [说明]
     */
    public static int nextInt(int n_minNum, int n_maxNum) {
        return n_minNum + rand.nextInt(n_maxNum - n_minNum + 1);
    }

    /**
     * 获取两个数字之差的绝对值
     *
     * @return [说明]
     */
    public static int abs(int min, int max) {
        return min == max ? 0 : min > max ? min - max : max - min;
    }

    /**
     * @return [说明]
     */
    public static boolean comparator(String minVer, String maxVer) {
        String[] minNum = minVer.substring(1).split("\\.");
        String[] maxNum = maxVer.substring(1).split("\\.");
        int len;
        if ((len = minNum.length) != maxNum.length) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (ParamUtil.parseInt(minNum[i]) < ParamUtil.parseInt(maxNum[i])) {
                return true;
            } else if (i == len - 1 && ParamUtil.parseInt(minNum[i]) >= ParamUtil
                .parseInt(maxNum[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取一个或多个double值之和
     *
     * @return [说明]
     */
    public static double add(double... nums) {
        BigDecimal decimal = null;
        for (int i = 0, len = nums.length; i < len; i++) {
            if (0 == i) {
                decimal = new BigDecimal(nums[i]);
            } else {
                decimal = decimal.add(new BigDecimal(nums[i]));
            }
        }
        return null == decimal ? 0D : decimal.doubleValue();
    }

    /**
     * 获取两个double之差
     *
     * @return [说明]
     */
    public static double subtract(double num1, double num2) {
        BigDecimal decimal1 = new BigDecimal(num1);
        return decimal1.subtract(new BigDecimal(num2)).doubleValue();
    }

    /**
     * 获取double和int的乘积
     *
     * @return [说明]
     */
    public static double multiply(double num1, int num2) {
        BigDecimal decimal1 = new BigDecimal(num1);
        return decimal1.multiply(new BigDecimal(num2)).doubleValue();
    }

    /**
     * 获取两个double的乘积
     *
     * @return [说明]
     */
    public static double multiply(double num1, double num2) {
        BigDecimal decimal1 = new BigDecimal(num1);
        return decimal1.multiply(new BigDecimal(num2)).doubleValue();
    }

    /**
     * 获取double和int相除的结果
     *
     * @param newScale 小数精度
     * @param roundingMode 小数位的保留方式（四舍五入等）
     * @return [说明]
     */
    public static double divide(int newScale, int roundingMode, double num1, int num2) {
        BigDecimal decimal1 = new BigDecimal(num1);
        return decimal1.divide(new BigDecimal(num2), newScale, roundingMode).doubleValue();
    }

    /**
     * 获取两个double相除的结果
     *
     * @param newScale 小数精度
     * @param roundingMode 小数位的保留方式（四舍五入等）
     * @return [说明]
     */
    public static double divide(int newScale, int roundingMode, double num1, double num2) {
        BigDecimal decimal1 = new BigDecimal(num1);
        return decimal1.divide(new BigDecimal(num2), newScale, roundingMode).doubleValue();
    }

    /**
     * 四舍五入取小数点后len位
     *
     * @param num 小数
     * @param len 位数
     * @return 小数
     */
    public static String round(BigDecimal num, int len) {
        return num.setScale(len, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 四舍五入取小数点后2位
     *
     * @param num 数值
     * @return 返回值
     */
    public static BigDecimal round(BigDecimal num) {
        return num.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
