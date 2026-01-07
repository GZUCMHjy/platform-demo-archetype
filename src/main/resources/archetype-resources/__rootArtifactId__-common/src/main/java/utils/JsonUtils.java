package ${package}.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * JSON工具类
 * <p>
 * 基于FastJSON2实现，提供JSON序列化和反序列化功能
 * 遵循阿里巴巴Java开发规范：
 * 1. 工具类必须是静态方法
 * 2. 方法必须进行参数校验
 * 3. 异常必须进行处理或转换
 *
 * @author ${author}
 * @since 1.0.0
 */
public class JsonUtils {

    private JsonUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 对象转JSON字符串
     *
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        return JSON.toJSONString(obj);
    }

    /**
     * 对象转JSON字符串（格式化）
     *
     * @param obj 对象
     * @return 格式化的JSON字符串
     */
    public static String toJsonStringPretty(Object obj) {
        if (obj == null) {
            return null;
        }
        return JSON.toJSONString(obj, JSONWriter.Feature.PrettyFormat);
    }

    /**
     * JSON字符串转对象
     *
     * @param jsonString JSON字符串
     * @param clazz      目标类型
     * @param <T>        泛型
     * @return 对象
     */
    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            return JSON.parseObject(jsonString, clazz);
        } catch (Exception e) {
            throw new RuntimeException("JSON解析失败: " + e.getMessage(), e);
        }
    }

    /**
     * JSON字符串转对象（支持泛型）
     *
     * @param jsonString JSON字符串
     * @param typeReference 类型引用
     * @param <T>        泛型
     * @return 对象
     */
    public static <T> T parseObject(String jsonString, TypeReference<T> typeReference) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            return JSON.parseObject(jsonString, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("JSON解析失败: " + e.getMessage(), e);
        }
    }

    /**
     * JSON字符串转List
     *
     * @param jsonString JSON字符串
     * @param clazz      List元素类型
     * @param <T>        泛型
     * @return List
     */
    public static <T> List<T> parseList(String jsonString, Class<T> clazz) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            return JSON.parseArray(jsonString, clazz);
        } catch (Exception e) {
            throw new RuntimeException("JSON解析失败: " + e.getMessage(), e);
        }
    }

    /**
     * 对象转Map
     *
     * @param obj 对象
     * @return Map
     */
    public static Map<String, Object> toMap(Object obj) {
        if (obj == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(obj));
    }

    /**
     * JSON字符串转Map
     *
     * @param jsonString JSON字符串
     * @return Map
     */
    public static Map<String, Object> parseMap(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            return JSON.parseObject(jsonString);
        } catch (Exception e) {
            throw new RuntimeException("JSON解析失败: " + e.getMessage(), e);
        }
    }

    /**
     * 判断是否为有效的JSON字符串
     *
     * @param jsonString 字符串
     * @return true-有效，false-无效
     */
    public static boolean isValidJson(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return false;
        }
        try {
            JSON.parse(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 对象转换（将源对象转换为目标类型对象）
     *
     * @param source      源对象
     * @param targetClass 目标类型
     * @param <T>         泛型
     * @return 目标对象
     */
    public static <T> T convertValue(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(source), targetClass);
    }
}
