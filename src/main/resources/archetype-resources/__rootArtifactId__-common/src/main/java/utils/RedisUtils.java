package ${package}.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * <p>
 * 基于Spring Data Redis实现，提供常用Redis操作
 * 遵循阿里巴巴Java开发规范：
 * 1. 所有方法必须进行参数校验
 * 2. 异常必须进行捕获和处理
 * 3. 使用泛型支持不同数据类型
 *
 * @author ${author}
 * @since 1.0.0
 */
@Component
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ==================== 通用操作 ====================

    /**
     * 设置过期时间
     *
     * @param key     键
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return true-成功，false-失败
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        try {
            if (StringUtils.isEmpty(key) || timeout <= 0) {
                return false;
            }
            return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
        } catch (Exception e) {
            throw new RuntimeException("设置过期时间失败: " + e.getMessage(), e);
        }
    }

    /**
     * 设置过期时间（日期）
     *
     * @param key  键
     * @param date 过期日期
     * @return true-成功，false-失败
     */
    public boolean expireAt(String key, Date date) {
        try {
            if (StringUtils.isEmpty(key) || date == null) {
                return false;
            }
            return Boolean.TRUE.equals(redisTemplate.expireAt(key, date));
        } catch (Exception e) {
            throw new RuntimeException("设置过期时间失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取过期时间
     *
     * @param key 键
     * @return 过期时间（秒），-1表示永不过期，-2表示键不存在
     */
    public long getExpire(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return -2;
            }
            Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            return expire != null ? expire : -2;
        } catch (Exception e) {
            throw new RuntimeException("获取过期时间失败: " + e.getMessage(), e);
        }
    }

    /**
     * 判断键是否存在
     *
     * @param key 键
     * @return true-存在，false-不存在
     */
    public boolean hasKey(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return false;
            }
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            throw new RuntimeException("判断键是否存在失败: " + e.getMessage(), e);
        }
    }

    /**
     * 删除键
     *
     * @param keys 键集合
     * @return 删除数量
     */
    public long del(String... keys) {
        try {
            if (keys == null || keys.length == 0) {
                return 0;
            }
            return redisTemplate.delete(List.of(keys));
        } catch (Exception e) {
            throw new RuntimeException("删除键失败: " + e.getMessage(), e);
        }
    }

    // ==================== String操作 ====================

    /**
     * 获取值
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new RuntimeException("获取值失败: " + e.getMessage(), e);
        }
    }

    /**
     * 设置值
     *
     * @param key   键
     * @param value 值
     * @return true-成功，false-失败
     */
    public boolean set(String key, Object value) {
        try {
            if (StringUtils.isEmpty(key)) {
                return false;
            }
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("设置值失败: " + e.getMessage(), e);
        }
    }

    /**
     * 设置值并设置过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return true-成功，false-失败
     */
    public boolean set(String key, Object value, long timeout, TimeUnit unit) {
        try {
            if (StringUtils.isEmpty(key) || timeout <= 0) {
                return false;
            }
            redisTemplate.opsForValue().set(key, value, timeout, unit);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("设置值失败: " + e.getMessage(), e);
        }
    }

    /**
     * 递增
     *
     * @param key 键
     * @return 递增后的值
     */
    public long incr(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                throw new IllegalArgumentException("键不能为空");
            }
            Long increment = redisTemplate.opsForValue().increment(key);
            return increment != null ? increment : 0;
        } catch (Exception e) {
            throw new RuntimeException("递增失败: " + e.getMessage(), e);
        }
    }

    /**
     * 递增指定步长
     *
     * @param key   键
     * @param delta 步长
     * @return 递增后的值
     */
    public long incrBy(String key, long delta) {
        try {
            if (StringUtils.isEmpty(key)) {
                throw new IllegalArgumentException("键不能为空");
            }
            Long increment = redisTemplate.opsForValue().increment(key, delta);
            return increment != null ? increment : 0;
        } catch (Exception e) {
            throw new RuntimeException("递增失败: " + e.getMessage(), e);
        }
    }

    /**
     * 递减
     *
     * @param key 键
     * @return 递减后的值
     */
    public long decr(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                throw new IllegalArgumentException("键不能为空");
            }
            Long decrement = redisTemplate.opsForValue().decrement(key);
            return decrement != null ? decrement : 0;
        } catch (Exception e) {
            throw new RuntimeException("递减失败: " + e.getMessage(), e);
        }
    }

    // ==================== Hash操作 ====================

    /**
     * 获取Hash中的值
     *
     * @param key  键
     * @param item 项
     * @return 值
     */
    public Object hGet(String key, String item) {
        try {
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(item)) {
                return null;
            }
            return redisTemplate.opsForHash().get(key, item);
        } catch (Exception e) {
            throw new RuntimeException("获取Hash值失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取Hash中所有值
     *
     * @param key 键
     * @return Map
     */
    public Map<Object, Object> hGetAll(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            throw new RuntimeException("获取Hash所有值失败: " + e.getMessage(), e);
        }
    }

    /**
     * 设置Hash值
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true-成功，false-失败
     */
    public boolean hSet(String key, String item, Object value) {
        try {
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(item)) {
                return false;
            }
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("设置Hash值失败: " + e.getMessage(), e);
        }
    }

    /**
     * 批量设置Hash值
     *
     * @param key 键
     * @param map Map
     * @return true-成功，false-失败
     */
    public boolean hSet(String key, Map<String, Object> map) {
        try {
            if (StringUtils.isEmpty(key) || CollectionUtils.isEmpty(map)) {
                return false;
            }
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("批量设置Hash值失败: " + e.getMessage(), e);
        }
    }

    /**
     * 删除Hash中的值
     *
     * @param key  键
     * @param item 项
     * @return true-成功，false-失败
     */
    public long hDel(String key, Object... item) {
        try {
            if (StringUtils.isEmpty(key) || item == null || item.length == 0) {
                return 0;
            }
            return redisTemplate.opsForHash().delete(key, item);
        } catch (Exception e) {
            throw new RuntimeException("删除Hash值失败: " + e.getMessage(), e);
        }
    }

    /**
     * 判断Hash中是否存在该项
     *
     * @param key  键
     * @param item 项
     * @return true-存在，false-不存在
     */
    public boolean hHasKey(String key, String item) {
        try {
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(item)) {
                return false;
            }
            return redisTemplate.opsForHash().hasKey(key, item);
        } catch (Exception e) {
            throw new RuntimeException("判断Hash中是否存在该项失败: " + e.getMessage(), e);
        }
    }

    // ==================== Set操作 ====================

    /**
     * 获取Set中的所有成员
     *
     * @param key 键
     * @return Set
     */
    public Set<Object> sMembers(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            throw new RuntimeException("获取Set成员失败: " + e.getMessage(), e);
        }
    }

    /**
     * 向Set中添加成员
     *
     * @param key     键
     * @param members 成员
     * @return 添加数量
     */
    public long sAdd(String key, Object... members) {
        try {
            if (StringUtils.isEmpty(key) || members == null || members.length == 0) {
                return 0;
            }
            Long count = redisTemplate.opsForSet().add(key, members);
            return count != null ? count : 0;
        } catch (Exception e) {
            throw new RuntimeException("添加Set成员失败: " + e.getMessage(), e);
        }
    }

    /**
     * 判断Set中是否存在该成员
     *
     * @param key    键
     * @param member 成员
     * @return true-存在，false-不存在
     */
    public boolean sIsMember(String key, Object member) {
        try {
            if (StringUtils.isEmpty(key) || member == null) {
                return false;
            }
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, member));
        } catch (Exception e) {
            throw new RuntimeException("判断Set中是否存在该成员失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取Set的成员数量
     *
     * @param key 键
     * @return 成员数量
     */
    public long sSize(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return 0;
            }
            Long size = redisTemplate.opsForSet().size(key);
            return size != null ? size : 0;
        } catch (Exception e) {
            throw new RuntimeException("获取Set成员数量失败: " + e.getMessage(), e);
        }
    }

    /**
     * 删除Set中的成员
     *
     * @param key     键
     * @param members 成员
     * @return 删除数量
     */
    public long sRemove(String key, Object... members) {
        try {
            if (StringUtils.isEmpty(key) || members == null || members.length == 0) {
                return 0;
            }
            Long count = redisTemplate.opsForSet().remove(key, members);
            return count != null ? count : 0;
        } catch (Exception e) {
            throw new RuntimeException("删除Set成员失败: " + e.getMessage(), e);
        }
    }

    // ==================== ZSet操作 ====================

    /**
     * 向ZSet中添加成员
     *
     * @param key   键
     * @param value 成员
     * @param score 分数
     * @return true-成功，false-失败
     */
    public boolean zAdd(String key, Object value, double score) {
        try {
            if (StringUtils.isEmpty(key) || value == null) {
                return false;
            }
            return Boolean.TRUE.equals(redisTemplate.opsForZSet().add(key, value, score));
        } catch (Exception e) {
            throw new RuntimeException("添加ZSet成员失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取ZSet中指定范围的成员（按分数升序）
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @return Set
     */
    public Set<Object> zRange(String key, long start, long end) {
        try {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException("获取ZSet成员失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取ZSet中指定范围的成员（按分数降序）
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @return Set
     */
    public Set<Object> zReverseRange(String key, long start, long end) {
        try {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException("获取ZSet成员失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取ZSet中成员的分数
     *
     * @param key   键
     * @param value 成员
     * @return 分数
     */
    public double zScore(String key, Object value) {
        try {
            if (StringUtils.isEmpty(key) || value == null) {
                return 0;
            }
            Double score = redisTemplate.opsForZSet().score(key, value);
            return score != null ? score : 0;
        } catch (Exception e) {
            throw new RuntimeException("获取ZSet成员分数失败: " + e.getMessage(), e);
        }
    }

    /**
     * 删除ZSet中的成员
     *
     * @param key    键
     * @param values 成员
     * @return 删除数量
     */
    public long zRemove(String key, Object... values) {
        try {
            if (StringUtils.isEmpty(key) || values == null || values.length == 0) {
                return 0;
            }
            Long count = redisTemplate.opsForZSet().remove(key, values);
            return count != null ? count : 0;
        } catch (Exception e) {
            throw new RuntimeException("删除ZSet成员失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取ZSet的成员数量
     *
     * @param key 键
     * @return 成员数量
     */
    public long zSize(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return 0;
            }
            Long size = redisTemplate.opsForZSet().size(key);
            return size != null ? size : 0;
        } catch (Exception e) {
            throw new RuntimeException("获取ZSet成员数量失败: " + e.getMessage(), e);
        }
    }

    // ==================== List操作 ====================

    /**
     * 获取List中指定范围的元素
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @return List
     */
    public List<Object> lRange(String key, long start, long end) {
        try {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException("获取List元素失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取List的长度
     *
     * @param key 键
     * @return 长度
     */
    public long lSize(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return 0;
            }
            Long size = redisTemplate.opsForList().size(key);
            return size != null ? size : 0;
        } catch (Exception e) {
            throw new RuntimeException("获取List长度失败: " + e.getMessage(), e);
        }
    }

    /**
     * 向List左侧插入元素
     *
     * @param key   键
     * @param value 值
     * @return 插入后的长度
     */
    public long lLeftPush(String key, Object value) {
        try {
            if (StringUtils.isEmpty(key) || value == null) {
                return 0;
            }
            Long size = redisTemplate.opsForList().leftPush(key, value);
            return size != null ? size : 0;
        } catch (Exception e) {
            throw new RuntimeException("向List左侧插入元素失败: " + e.getMessage(), e);
        }
    }

    /**
     * 向List右侧插入元素
     *
     * @param key   键
     * @param value 值
     * @return 插入后的长度
     */
    public long lRightPush(String key, Object value) {
        try {
            if (StringUtils.isEmpty(key) || value == null) {
                return 0;
            }
            Long size = redisTemplate.opsForList().rightPush(key, value);
            return size != null ? size : 0;
        } catch (Exception e) {
            throw new RuntimeException("向List右侧插入元素失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从List左侧弹出元素
     *
     * @param key 键
     * @return 元素
     */
    public Object lLeftPop(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            throw new RuntimeException("从List左侧弹出元素失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从List右侧弹出元素
     *
     * @param key 键
     * @return 元素
     */
    public Object lRightPop(String key) {
        try {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            throw new RuntimeException("从List右侧弹出元素失败: " + e.getMessage(), e);
        }
    }

    /**
     * 根据索引获取List中的元素
     *
     * @param key   键
     * @param index 索引
     * @return 元素
     */
    public Object lIndex(String key, long index) {
        try {
            if (StringUtils.isEmpty(key)) {
                return null;
            }
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            throw new RuntimeException("获取List元素失败: " + e.getMessage(), e);
        }
    }

    /**
     * 删除List中指定的元素
     *
     * @param key   键
     * @param count 删除数量
     * @param value 值
     * @return 删除数量
     */
    public long lRemove(String key, long count, Object value) {
        try {
            if (StringUtils.isEmpty(key) || value == null) {
                return 0;
            }
            Long removeCount = redisTemplate.opsForList().remove(key, count, value);
            return removeCount != null ? removeCount : 0;
        } catch (Exception e) {
            throw new RuntimeException("删除List元素失败: " + e.getMessage(), e);
        }
    }
}
