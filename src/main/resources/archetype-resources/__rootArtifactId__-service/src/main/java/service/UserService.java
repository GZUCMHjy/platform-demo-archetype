package ${package}.service;

import ${package}.dto.UserDTO;
import ${package}.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户服务接口
 *
 * @author ${author}
 * @since 1.0.0
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);

    /**
     * 分页查询用户列表
     *
     * @param current 当前页
     * @param size    每页大小
     * @return 用户分页数据
     */
    IPage<User> pageUsers(long current, long size);

    /**
     * 创建用户
     *
     * @param userDTO 用户DTO
     * @return 用户ID
     */
    Long createUser(UserDTO userDTO);

    /**
     * 更新用户
     *
     * @param userDTO 用户DTO
     * @return 是否成功
     */
    boolean updateUser(UserDTO userDTO);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 是否成功
     */
    boolean deleteUser(Long id);
}
