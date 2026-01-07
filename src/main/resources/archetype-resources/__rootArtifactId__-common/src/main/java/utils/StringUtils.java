package ${package}.utils;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. 工具类必须是静态方法
 * 2. 方法必须进行参数校验
 * 3. 尽量使用Apache Commons Lang3或Spring的StringUtils
 *
 * @author ${author}
 * @since 1.0.0
 */
public class StringUtils extends org.springframework.util.StringUtils {

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    /**
     * 下划线
     */
    public static final char UNDERLINE = '_';

    /**
     * 中划线
     */
    public static final char HYPHEN = '-';

    /**
     * 点号
     */
    public static final char DOT = '.';

    /**
     * 逗号
     */
    public static final String COMMA = ",";

    /**
     * 手机号正则表达式
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    /**
     * 邮箱正则表达式
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    /**
     * 数字正则表达式
     */
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    private StringUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // ==================== 判断空 ====================

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return true-为空，false-不为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 字符串
     * @return true-不为空，false-为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为空白（包括null、空字符串、纯空格）
     *
     * @param str 字符串
     * @return true-为空白，false-不为空白
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断字符串是否不为空白
     *
     * @param str 字符串
     * @return true-不为空白，false-为空白
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return true-为空，false-不为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断数组是否为空
     *
     * @param array 数组
     * @return true-为空，false-不为空
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    // ==================== 字符串处理 ====================

    /**
     * 去除字符串两端空格
     *
     * @param str 字符串
     * @return 去除空格后的字符串
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 去除字符串两端空格，如果为空返回默认值
     *
     * @param str         字符串
     * @param defaultValue 默认值
     * @return 去除空格后的字符串或默认值
     */
    public static String trimToDefault(String str, String defaultValue) {
        str = trim(str);
        return isEmpty(str) ? defaultValue : str;
    }

    /**
     * 去除字符串两端空格，如果为空返回空字符串
     *
     * @param str 字符串
     * @return 去除空格后的字符串或空字符串
     */
    public static String trimToEmpty(String str) {
        return trimToDefault(str, EMPTY);
    }

    /**
     * 截取字符串
     *
     * @param str      字符串
     * @param start    开始位置
     * @param end      结束位置
     * @return 截取后的字符串
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return EMPTY;
        }
        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }
        if (start > end) {
            return EMPTY;
        }
        if (start < 0) {
            start = 0;
        }
        if (end > str.length()) {
            end = str.length();
        }
        return str.substring(start, end);
    }

    /**
     * 截取字符串（从开始位置到末尾）
     *
     * @param str   字符串
     * @param start 开始位置
     * @return 截取后的字符串
     */
    public static String substring(String str, int start) {
        if (str == null) {
            return EMPTY;
        }
        return substring(str, start, str.length());
    }

    /**
     * 字符串拼接
     *
     * @param strings 字符串数组
     * @return 拼接后的字符串
     */
    public static String append(String... strings) {
        if (isEmpty(strings)) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : strings) {
            if (str != null) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /**
     * 字符串拼接（使用分隔符）
     *
     * @param delimiter 分隔符
     * @param strings   字符串数组
     * @return 拼接后的字符串
     */
    public static String join(String delimiter, String... strings) {
        if (isEmpty(strings)) {
            return EMPTY;
        }
        if (delimiter == null) {
            delimiter = EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (i > 0) {
                sb.append(delimiter);
            }
            if (strings[i] != null) {
                sb.append(strings[i]);
            }
        }
        return sb.toString();
    }

    // ==================== 大小写转换 ====================

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 首字母大写后的字符串
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str 字符串
     * @return 首字母小写后的字符串
     */
    public static String uncapitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 转换为小写
     *
     * @param str 字符串
     * @return 小写字符串
     */
    public static String toLowerCase(String str) {
        return str == null ? null : str.toLowerCase();
    }

    /**
     * 转换为大写
     *
     * @param str 字符串
     * @return 大写字符串
     */
    public static String toUpperCase(String str) {
        return str == null ? null : str.toUpperCase();
    }

    // ==================== 驼峰转换 ====================

    /**
     * 驼峰转下划线（驼峰命名转下划线命名）
     *
     * @param camelCaseStr 驼峰字符串
     * @return 下划线字符串
     */
    public static String camelToUnderline(String camelCaseStr) {
        if (isEmpty(camelCaseStr)) {
            return EMPTY;
        }
        int len = camelCaseStr.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = camelCaseStr.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰（下划线命名转驼峰命名）
     *
     * @param underlineStr 下划线字符串
     * @return 驼峰字符串
     */
    public static String underlineToCamel(String underlineStr) {
        if (isEmpty(underlineStr)) {
            return EMPTY;
        }
        char[] chars = underlineStr.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (char c : chars) {
            if (c == UNDERLINE) {
                upperCase = true;
            } else {
                if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    // ==================== 正则验证 ====================

    /**
     * 验证手机号
     *
     * @param phone 手机号
     * @return true-有效，false-无效
     */
    public static boolean isPhone(String phone) {
        return isNotBlank(phone) && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 验证邮箱
     *
     * @param email 邮箱
     * @return true-有效，false-无效
     */
    public static boolean isEmail(String email) {
        return isNotBlank(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 验证数字
     *
     * @param str 字符串
     * @return true-是数字，false-不是数字
     */
    public static boolean isNumeric(String str) {
        return isNotBlank(str) && NUMERIC_PATTERN.matcher(str).matches();
    }

    /**
     * 验证正则表达式
     *
     * @param str    字符串
     * @param regex  正则表达式
     * @return true-匹配，false-不匹配
     */
    public static boolean matches(String str, String regex) {
        if (isBlank(str) || isBlank(regex)) {
            return false;
        }
        return Pattern.matches(regex, str);
    }

    // ==================== 其他工具 ====================

    /**
     * 生成UUID
     *
     * @return UUID字符串（去除横线）
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace(String.valueOf(HYPHEN), EMPTY);
    }

    /**
     * 生成UUID（带横线）
     *
     * @return UUID字符串
     */
    public static String uuidWithHyphen() {
        return UUID.randomUUID().toString();
    }

    /**
     * 字符串转字节数组（UTF-8）
     *
     * @param str 字符串
     * @return 字节数组
     */
    public static byte[] getBytes(String str) {
        return str == null ? null : str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 字节数组转字符串（UTF-8）
     *
     * @param bytes 字节数组
     * @return 字符串
     */
    public static String newString(byte[] bytes) {
        return bytes == null ? null : new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 判断字符串是否包含子串
     *
     * @param str       字符串
     * @param searchStr 子串
     * @return true-包含，false-不包含
     */
    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return str.contains(searchStr);
    }

    /**
     * 替换字符串
     *
     * @param str         字符串
     * @param searchText  被替换的字符串
     * @param replacement 替换的字符串
     * @return 替换后的字符串
     */
    public static String replace(String str, String searchText, String replacement) {
        if (str == null || searchText == null || replacement == null) {
            return str;
        }
        return str.replace(searchText, replacement);
    }

    /**
     * 替换所有匹配的字符串
     *
     * @param str         字符串
     * @param regex       正则表达式
     * @param replacement 替换的字符串
     * @return 替换后的字符串
     */
    public static String replaceAll(String str, String regex, String replacement) {
        if (str == null || regex == null || replacement == null) {
            return str;
        }
        return str.replaceAll(regex, replacement);
    }
}
