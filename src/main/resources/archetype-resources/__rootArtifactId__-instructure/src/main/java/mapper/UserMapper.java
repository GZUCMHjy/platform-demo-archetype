package ${package}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${package}.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 *
 * @author ${author}
 * @since 1.0.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 继承BaseMapper，拥有基础CRUD方法
}
