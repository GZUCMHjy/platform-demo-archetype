package ${package}.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
/**
 * 加密解密工具类
 * <p>
 * 提供常用的加密解密功能
 * 遵循阿里巴巴Java开发规范：
 * 1. 工具类必须是静态方法
 * 2. 加密算法使用标准Java实现
 * 3. 敏感信息处理需要谨慎
 *
 * @author ${author}
 * @since 1.0.0
 */
public class EncryptUtils {

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private EncryptUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // ==================== Base64编码/解码 ====================

    /**
     * Base64编码
     *
     * @param str 字符串
     * @return Base64编码后的字符串
     */
    public static String base64Encode(String str) {
        if (str == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64编码（字节数组）
     *
     * @param bytes 字节数组
     * @return Base64编码后的字符串
     */
    public static String base64Encode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base64解码
     *
     * @param str Base64编码的字符串
     * @return 解码后的字符串
     */
    public static String base64Decode(String str) {
        if (str == null) {
            return null;
        }
        byte[] bytes = Base64.getDecoder().decode(str);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Base64解码（返回字节数组）
     *
     * @param str Base64编码的字符串
     * @return 解码后的字节数组
     */
    public static byte[] base64DecodeToBytes(String str) {
        if (str == null) {
            return null;
        }
        return Base64.getDecoder().decode(str);
    }

    // ==================== MD5加密 ====================

    /**
     * MD5加密（32位小写）
     *
     * @param str 字符串
     * @return MD5加密后的字符串
     */
    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        return md5(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * MD5加密（字节数组）
     *
     * @param bytes 字节数组
     * @return MD5加密后的字符串
     */
    public static String md5(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return encodeHex(md.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不支持", e);
        }
    }

    // ==================== SHA加密 ====================

    /**
     * SHA-1加密
     *
     * @param str 字符串
     * @return SHA-1加密后的字符串
     */
    public static String sha1(String str) {
        if (str == null) {
            return null;
        }
        return sha1(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * SHA-1加密（字节数组）
     *
     * @param bytes 字节数组
     * @return SHA-1加密后的字符串
     */
    public static String sha1(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return encodeHex(md.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1算法不支持", e);
        }
    }

    /**
     * SHA-256加密
     *
     * @param str 字符串
     * @return SHA-256加密后的字符串
     */
    public static String sha256(String str) {
        if (str == null) {
            return null;
        }
        return sha256(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * SHA-256加密（字节数组）
     *
     * @param bytes 字节数组
     * @return SHA-256加密后的字符串
     */
    public static String sha256(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return encodeHex(md.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256算法不支持", e);
        }
    }

    /**
     * SHA-512加密
     *
     * @param str 字符串
     * @return SHA-512加密后的字符串
     */
    public static String sha512(String str) {
        if (str == null) {
            return null;
        }
        return sha512(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * SHA-512加密（字节数组）
     *
     * @param bytes 字节数组
     * @return SHA-512加密后的字符串
     */
    public static String sha512(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            return encodeHex(md.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512算法不支持", e);
        }
    }

    // ==================== 字节数组转十六进制字符串 ====================

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String encodeHex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        char[] chars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int byteValue = bytes[i] & 0xFF;
            chars[i * 2] = HEX_DIGITS[byteValue >>> 4];
            chars[i * 2 + 1] = HEX_DIGITS[byteValue & 0x0F];
        }
        return new String(chars);
    }

    /**
     * 十六进制字符串转字节数组
     *
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    public static byte[] decodeHex(String hexString) {
        if (hexString == null) {
            return null;
        }
        char[] chars = hexString.toCharArray();
        if ((chars.length & 0x01) != 0) {
            throw new IllegalArgumentException("十六进制字符串长度必须是偶数");
        }
        byte[] bytes = new byte[chars.length / 2];
        for (int i = 0; i < chars.length; i += 2) {
            int high = Character.digit(chars[i], 16);
            int low = Character.digit(chars[i + 1], 16);
            if (high == -1 || low == -1) {
                throw new IllegalArgumentException("无效的十六进制字符: " + hexString);
            }
            bytes[i / 2] = (byte) ((high << 4) | low);
        }
        return bytes;
    }

    // ==================== XOR加密 ====================

    /**
     * XOR加密（简单加密，不适用于安全性要求高的场景）
     *
     * @param str 字符串
     * @param key 密钥
     * @return 加密后的字符串
     */
    public static String xor(String str, String key) {
        if (str == null || key == null) {
            return null;
        }
        char[] chars = str.toCharArray();
        char[] keyChars = key.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= keyChars[i % keyChars.length];
        }
        return new String(chars);
    }

    // ==================== BCrypt加密（推荐用于密码加密）====================

    private static final BCryptPasswordEncoder BCRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /**
     * BCrypt加密（推荐用于密码加密）
     *
     * @param str 字符串
     * @return 加密后的字符串
     */
    public static String bcrypt(String str) {
        return BCRYPT_PASSWORD_ENCODER.encode(str);
    }

    /**
     * BCrypt校验
     *
     * @param str       原字符串
     * @param encrypted 加密后的字符串
     * @return true-匹配，false-不匹配
     */
    public static boolean bcryptMatch(String str, String encrypted) {
        return BCRYPT_PASSWORD_ENCODER.matches(str, encrypted);
    }
}
