package xyz.hhang.boot.utils;

import java.util.Random;

/**
 * 验证码工具类
 */
public class VerifyCodeUtil {

    private static final String BASE = "0123456789abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

    public static String getCode(int len){
        int baseLength = BASE.length();

        // 生成 0 - (baseLength - 1) 之间的随机数
        char[] chars = new char[len];
        Random random = new Random();
        for(int i=0; i<len; i++){
            char c = BASE.charAt(random.nextInt(baseLength));
            chars[i] = c;
        }
        return new String(chars);
    }

}
