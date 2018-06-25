package com.polystone.tools.common;

import java.util.Random;

/**
 * 随机码处理
 *
 * @author jimmy
 * @version V1.0, 2017/7/19
 * @copyright
 */
public class RandomUtil {

    private static final char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O',
        'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
        '2', '3', '4',
        '5', '6', '7', '8', '9'};
    private static final char[] numbers = {'0', '1', '2', '3', '4',
        '5', '6', '7', '8', '9'};

    private static Random rand = new Random(System.currentTimeMillis());

    /**
     * 生成随机字符串
     *
     * @param len 长度
     * @return 随机码
     */
    public static String randomChar(int len) {
        if (len < 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(chars[rand.nextInt(chars.length)]);
        }

        return builder.toString();
    }

    /**
     * 生成随机数字
     *
     * @param len 长度
     * @return 随机码
     */
    public static String randomNumber(int len) {
        if (len < 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(numbers[rand.nextInt(numbers.length)]);
        }

        return builder.toString();
    }

}
