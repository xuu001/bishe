package xyz.hhang.boot.utils;

import com.alibaba.druid.util.HexBin;
import sun.java2d.pipe.SolidTextRenderer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String encode(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] digest = messageDigest.digest((ConstantUtil.PASS_SALT + password).getBytes());
            return HexBin.encode(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static boolean chack(String original,String encoded){
        String inputEncoded = encode(original);
        return encoded.equals(inputEncoded);
    }
}
