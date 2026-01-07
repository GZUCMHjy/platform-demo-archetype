package ${package}.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis分布式锁工具类
 * <p>
 * 基于Redisson实现，提供分布式锁功能
 * 遵循阿里巴巴Java开发规范：
 * 1. 锁必须设置过期时间
 * 2. 锁释放必须在finally块中
 * 3. 锁等待时间应该合理设置
 *
 * @author ${author}
 * @since 1.0.0
 */
@Component
public class RedisLockUtils {

    private final RedissonClient redissonClient;

    public RedisLockUtils(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 获取锁（默认等待10秒，锁30秒）
     *
     * @param lockKey 锁的键
     * @return true-获取成功，false-获取失败
     */
    public boolean lock(String lockKey) {
        return lock(lockKey, 10L, 30L);
    }

    /**
     * 获取锁（自定义等待时间和锁定时间）
     *
     * @param lockKey   锁的键
     * @param waitTime  等待时间（秒）
     * @param leaseTime 锁定时间（秒）
     * @return true-获取成功，false-获取失败
     */
    public boolean lock(String lockKey, long waitTime, long leaseTime) {
        try {
            if (StringUtils.isEmpty(lockKey)) {
                return false;
            }
            RLock lock = redissonClient.getLock(lockKey);
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("获取锁失败: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("获取锁失败: " + e.getMessage(), e);
        }
    }

    /**
     * 释放锁
     *
     * @param lockKey 锁的键
     */
    public void unlock(String lockKey) {
        try {
            if (StringUtils.isEmpty(lockKey)) {
                return;
            }
            RLock lock = redissonClient.getLock(lockKey);
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        } catch (Exception e) {
            throw new RuntimeException("释放锁失败: " + e.getMessage(), e);
        }
    }

    /**
     * 尝试获取锁（非阻塞）
     *
     * @param lockKey   锁的键
     * @param leaseTime 锁定时间（秒）
     * @return true-获取成功，false-获取失败
     */
    public boolean tryLock(String lockKey, long leaseTime) {
        try {
            if (StringUtils.isEmpty(lockKey)) {
                return false;
            }
            RLock lock = redissonClient.getLock(lockKey);
            return lock.tryLock(0, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } catch (Exception e) {
            throw new RuntimeException("获取锁失败: " + e.getMessage(), e);
        }
    }

    /**
     * 判断锁是否被锁定
     *
     * @param lockKey 锁的键
     * @return true-已锁定，false-未锁定
     */
    public boolean isLocked(String lockKey) {
        try {
            if (StringUtils.isEmpty(lockKey)) {
                return false;
            }
            RLock lock = redissonClient.getLock(lockKey);
            return lock.isLocked();
        } catch (Exception e) {
            throw new RuntimeException("判断锁状态失败: " + e.getMessage(), e);
        }
    }

    /**
     * 判断锁是否被当前线程持有
     *
     * @param lockKey 锁的键
     * @return true-被当前线程持有，false-不被当前线程持有
     */
    public boolean isHeldByCurrentThread(String lockKey) {
        try {
            if (StringUtils.isEmpty(lockKey)) {
                return false;
            }
            RLock lock = redissonClient.getLock(lockKey);
            return lock.isHeldByCurrentThread();
        } catch (Exception e) {
            throw new RuntimeException("判断锁持有状态失败: " + e.getMessage(), e);
        }
    }

    /**
     * 强制释放锁
     *
     * @param lockKey 锁的键
     */
    public void forceUnlock(String lockKey) {
        try {
            if (StringUtils.isEmpty(lockKey)) {
                return;
            }
            RLock lock = redissonClient.getLock(lockKey);
            lock.forceUnlock();
        } catch (Exception e) {
            throw new RuntimeException("强制释放锁失败: " + e.getMessage(), e);
        }
    }

    /**
     * 执行带锁的任务
     *
     * @param lockKey 锁的键
     * @param task    任务
     * @return true-执行成功，false-执行失败
     */
    public boolean executeWithLock(String lockKey, Runnable task) {
        return executeWithLock(lockKey, 10L, 30L, task);
    }

    /**
     * 执行带锁的任务（自定义等待时间和锁定时间）
     *
     * @param lockKey   锁的键
     * @param waitTime  等待时间（秒）
     * @param leaseTime 锁定时间（秒）
     * @param task      任务
     * @return true-执行成功，false-执行失败
     */
    public boolean executeWithLock(String lockKey, long waitTime, long leaseTime, Runnable task) {
        boolean locked = false;
        try {
            locked = lock(lockKey, waitTime, leaseTime);
            if (!locked) {
                return false;
            }
            task.run();
            return true;
        } finally {
            if (locked) {
                unlock(lockKey);
            }
        }
    }

    /**
     * 执行带锁的任务（有返回值）
     *
     * @param lockKey 锁的键
     * @param task    任务
     * @param <T>     返回值类型
     * @return 执行结果
     */
    public <T> T executeWithLock(String lockKey, java.util.concurrent.Callable<T> task) {
        return executeWithLock(lockKey, 10L, 30L, task);
    }

    /**
     * 执行带锁的任务（有返回值，自定义等待时间和锁定时间）
     *
     * @param lockKey   锁的键
     * @param waitTime  等待时间（秒）
     * @param leaseTime 锁定时间（秒）
     * @param task      任务
     * @param <T>       返回值类型
     * @return 执行结果
     */
    public <T> T executeWithLock(String lockKey, long waitTime, long leaseTime, java.util.concurrent.Callable<T> task) {
        boolean locked = false;
        try {
            locked = lock(lockKey, waitTime, leaseTime);
            if (!locked) {
                throw new RuntimeException("获取锁失败");
            }
            return task.call();
        } catch (Exception e) {
            throw new RuntimeException("执行任务失败: " + e.getMessage(), e);
        } finally {
            if (locked) {
                unlock(lockKey);
            }
        }
    }

    // ==================== 读写锁 ====================

    /**
     * 获取读锁
     *
     * @param lockKey 锁的键
     * @return true-获取成功，false-获取失败
     */
    public boolean readLock(String lockKey) {
        return readLock(lockKey, 10L, 30L);
    }

    /**
     * 获取读锁（自定义等待时间和锁定时间）
     *
     * @param lockKey   锁的键
     * @param waitTime  等待时间（秒）
     * @param leaseTime 锁定时间（秒）
     * @return true-获取成功，false-获取失败
     */
    public boolean readLock(String lockKey, long waitTime, long leaseTime) {
        try {
            if (StringUtils.isEmpty(lockKey)) {
                return false;
            }
            RLock lock = redissonClient.getReadWriteLock(lockKey).readLock();
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } catch (Exception e) {
            throw new RuntimeException("获取读锁失败: " + e.getMessage(), e);
        }
    }

    /**
     * 释放读锁
     *
     * @param lockKey 锁的键
     */
    public void readUnlock(String lockKey) {
        try {
            if (StringUtils.isEmpty(lockKey)) {
                return;
            }
            RLock lock = redissonClient.getReadWriteLock(lockKey).readLock();
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        } catch (Exception e) {
            throw new RuntimeException("释放读锁失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取写锁
     *
     * @param lockKey 锁的键
     * @return true-获取成功，false-获取失败
     */
    public boolean writeLock(String lockKey) {
        return writeLock(lockKey, 10L, 30L);
    }

    /**
     * 获取写锁（自定义等待时间和锁定时间）
     *
     * @param lockKey   锁的键
     * @param waitTime  等待时间（秒）
     * @param leaseTime 锁定时间（秒）
     * @return true-获取成功，false-获取失败
     */
    public boolean writeLock(String lockKey, long waitTime, long leaseTime) {
        try {
            if (StringUtils.isEmpty(lockKey)) {
                return false;
            }
            RLock lock = redissonClient.getReadWriteLock(lockKey).writeLock();
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } catch (Exception e) {
            throw new RuntimeException("获取写锁失败: " + e.getMessage(), e);
        }
    }

    /**
     * 释放写锁
     *
     * @param lockKey 锁的键
     */
    public void writeUnlock(String lockKey) {
        try {
            if (StringUtils.isEmpty(lockKey)) {
                return;
            }
            RLock lock = redissonClient.getReadWriteLock(lockKey).writeLock();
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        } catch (Exception e) {
            throw new RuntimeException("释放写锁失败: " + e.getMessage(), e);
        }
    }
}
