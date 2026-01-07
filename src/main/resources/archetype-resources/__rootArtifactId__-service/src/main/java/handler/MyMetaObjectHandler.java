package ${package}.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis Plus字段自动填充处理器
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. 自动填充创建时间和更新时间
 * 2. 自动填充创建人和更新人
 * 3. 从上下文中获取当前用户信息
 *
 * @author ${author}
 * @since 1.0.0
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时自动填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("开始插入填充...");

        // 自动填充创建时间
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());

        // 自动填充更新时间
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        // TODO: 从上下文中获取当前用户ID
        Long currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            // 自动填充创建人
            this.strictInsertFill(metaObject, "createBy", Long.class, currentUserId);
            // 自动填充更新人
            this.strictInsertFill(metaObject, "updateBy", Long.class, currentUserId);
        }
    }

    /**
     * 更新时自动填充
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("开始更新填充...");

        // 自动填充更新时间
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        // TODO: 从上下文中获取当前用户ID
        Long currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            // 自动填充更新人
            this.strictUpdateFill(metaObject, "updateBy", Long.class, currentUserId);
        }
    }

    /**
     * 获取当前用户ID
     * <p>
     * TODO: 实际项目中需要从Spring Security上下文或JWT中获取
     *
     * @return 用户ID
     */
    private Long getCurrentUserId() {
        // 从上下文中获取当前用户ID
        // UserContext user = SecurityContextHolder.getUser();
        // return user != null ? user.getId() : null;

        // 暂时返回null，实际项目中需要实现
        return null;
    }
}
