package ${package}.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 集合工具类
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. 工具类必须是静态方法
 * 2. 方法必须进行参数校验
 * 3. 尽量使用Java 8 Stream API处理集合
 *
 * @author ${author}
 * @since 1.0.0
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // ==================== 判断空 ====================

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
     * 判断集合是否不为空
     *
     * @param collection 集合
     * @return true-不为空，false-为空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断Map是否为空
     *
     * @param map Map
     * @return true-为空，false-不为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断Map是否不为空
     *
     * @param map Map
     * @return true-不为空，false-为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    // ==================== 集合创建 ====================

    /**
     * 创建ArrayList
     *
     * @param <T> 元素类型
     * @return ArrayList
     */
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * 创建ArrayList（指定初始容量）
     *
     * @param initialCapacity 初始容量
     * @param <T>             元素类型
     * @return ArrayList
     */
    public static <T> ArrayList<T> newArrayList(int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }

    /**
     * 创建ArrayList（指定元素）
     *
     * @param elements 元素数组
     * @param <T>      元素类型
     * @return ArrayList
     */
    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... elements) {
        ArrayList<T> list = new ArrayList<>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }

    /**
     * 创建空List（不可变）
     *
     * @param <T> 元素类型
     * @return 空List
     */
    public static <T> List<T> emptyList() {
        return Collections.emptyList();
    }

    /**
     * 创建空Set（不可变）
     *
     * @param <T> 元素类型
     * @return 空Set
     */
    public static <T> Set<T> emptySet() {
        return Collections.emptySet();
    }

    /**
     * 创建空Map（不可变）
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 空Map
     */
    public static <K, V> Map<K, V> emptyMap() {
        return Collections.emptyMap();
    }

    // ==================== List操作 ====================

    /**
     * 获取List的第一个元素
     *
     * @param list List
     * @param <T>  元素类型
     * @return 第一个元素
     */
    public static <T> T getFirst(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取List的最后一个元素
     *
     * @param list List
     * @param <T>  元素类型
     * @return 最后一个元素
     */
    public static <T> T getLast(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /**
     * 将List转为Map
     *
     * @param list         List
     * @param keyMapper    键映射函数
     * @param valueMapper  值映射函数
     * @param <T>          元素类型
     * @param <K>          键类型
     * @param <V>          值类型
     * @return Map
     */
    public static <T, K, V> Map<K, V> toMap(List<T> list, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        if (isEmpty(list)) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper, (v1, v2) -> v1));
    }

    /**
     * 将List转为Map（使用元素本身作为值）
     *
     * @param list      List
     * @param keyMapper 键映射函数
     * @param <T>       元素类型
     * @param <K>       键类型
     * @return Map
     */
    public static <T, K> Map<K, T> toMap(List<T> list, Function<T, K> keyMapper) {
        return toMap(list, keyMapper, Function.identity());
    }

    /**
     * 从List中提取字段形成新的List
     *
     * @param list        List
     * @param mapper      映射函数
     * @param <T>         原元素类型
     * @param <R>         新元素类型
     * @return 新的List
     */
    public static <T, R> List<R> extract(List<T> list, Function<T, R> mapper) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 过滤List
     *
     * @param list     List
     * @param predicate 过滤条件
     * @param <T>      元素类型
     * @return 过滤后的List
     */
    public static <T> List<T> filter(List<T> list, java.util.function.Predicate<T> predicate) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    // ==================== 数组操作 ====================

    /**
     * 判断数组是否为空
     *
     * @param array 数组
     * @return true-为空，false-不为空
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断数组是否不为空
     *
     * @param array 数组
     * @return true-不为空，false-为空
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组转List
     *
     * @param array 数组
     * @param <T>   元素类型
     * @return List
     */
    @SafeVarargs
    public static <T> List<T> toList(T... array) {
        if (isEmpty(array)) {
            return Collections.emptyList();
        }
        List<T> list = new ArrayList<>(array.length);
        Collections.addAll(list, array);
        return list;
    }

    // ==================== Join操作 ====================

    /**
     * 将List转为字符串（使用逗号分隔）
     *
     * @param list List
     * @param <T>  元素类型
     * @return 字符串
     */
    public static <T> String join(List<T> list) {
        return join(list, ",");
    }

    /**
     * 将List转为字符串（使用指定分隔符）
     *
     * @param list       List
     * @param delimiter  分隔符
     * @param <T>        元素类型
     * @return 字符串
     */
    public static <T> String join(List<T> list, String delimiter) {
        if (isEmpty(list)) {
            return "";
        }
        return list.stream().map(String::valueOf).collect(Collectors.joining(delimiter));
    }

    /**
     * 将数组转为字符串（使用逗号分隔）
     *
     * @param array 数组
     * @param <T>   元素类型
     * @return 字符串
     */
    public static <T> String join(T[] array) {
        return join(array, ",");
    }

    /**
     * 将数组转为字符串（使用指定分隔符）
     *
     * @param array     数组
     * @param delimiter 分隔符
     * @param <T>       元素类型
     * @return 字符串
     */
    public static <T> String join(T[] array, String delimiter) {
        if (isEmpty(array)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(delimiter);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    // ==================== 其他工具 ====================

    /**
     * 获取集合大小
     *
     * @param collection 集合
     * @return 大小，如果为null返回0
     */
    public static int size(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    /**
     * 获取数组大小
     *
     * @param array 数组
     * @return 大小，如果为null返回0
     */
    public static int size(Object[] array) {
        return array == null ? 0 : array.length;
    }

    /**
     * 判断集合是否包含元素
     *
     * @param collection 集合
     * @param element    元素
     * @return true-包含，false-不包含
     */
    public static boolean contains(Collection<?> collection, Object element) {
        return collection != null && collection.contains(element);
    }

    /**
     * 添加元素到集合（如果集合为null，不添加）
     *
     * @param collection 集合
     * @param element    元素
     * @param <T>        元素类型
     * @return true-添加成功，false-添加失败
     */
    public static <T> boolean add(Collection<T> collection, T element) {
        return collection != null && collection.add(element);
    }

    /**
     * 从集合中移除元素
     *
     * @param collection 集合
     * @param element    元素
     * @param <T>        元素类型
     * @return true-移除成功，false-移除失败
     */
    public static <T> boolean remove(Collection<T> collection, Object element) {
        return collection != null && collection.remove(element);
    }

    /**
     * 清空集合
     *
     * @param collection 集合
     */
    public static void clear(Collection<?> collection) {
        if (collection != null) {
            collection.clear();
        }
    }
}
