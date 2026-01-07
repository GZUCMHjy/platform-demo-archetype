package ${package}.service.impl;

import ${package}.dto.UserDTO;
import ${package}.exception.BusinessException;
import ${package}.entity.User;
import ${package}.mapper.UserMapper;
import ${package}.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ${package}.utils.EncryptUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 用户服务实现类
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. Service层处理业务逻辑
 * 2. 使用事务保证数据一致性
 * 3. 异常必须有明确含义
 *
 * @author ${author}
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    @Override
    public User getByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            throw new BusinessException("用户名不能为空");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public IPage<User> pageUsers(long current, long size) {
        Page<User> page = new Page<>(current, size);
        return userMapper.selectPage(page, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserDTO userDTO) {
        // 检查用户名是否已存在
        User existUser = getByUsername(userDTO.getUsername());
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }

        // 创建用户
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        // 密码加密（使用BCrypt加密）
        String password = userDTO.getPassword() != null ? userDTO.getPassword() : "123456";
        user.setPassword(EncryptUtils.bcrypt(password));

        // 设置默认状态
        if (user.getStatus() == null) {
            user.setStatus(1);
        }

        userMapper.insert(user);
        log.info("创建用户成功: userId={}", user.getId());
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(UserDTO userDTO) {
        if (userDTO.getId() == null) {
            throw new BusinessException("用户ID不能为空");
        }

        // 检查用户是否存在
        User user = userMapper.selectById(userDTO.getId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 如果修改用户名，检查新用户名是否已存在
        if (!user.getUsername().equals(userDTO.getUsername())) {
            User existUser = getByUsername(userDTO.getUsername());
            if (existUser != null) {
                throw new BusinessException("用户名已存在");
            }
        }

        // 更新用户
        User updateUser = new User();
        BeanUtils.copyProperties(userDTO, updateUser);
        int rows = userMapper.updateById(updateUser);
        log.info("更新用户成功: userId={}", userDTO.getId());
        return rows > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(Long id) {
        if (id == null) {
            throw new BusinessException("用户ID不能为空");
        }

        // 检查用户是否存在
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 逻辑删除
        int rows = userMapper.deleteById(id);
        log.info("删除用户成功: userId={}", id);
        return rows > 0;
    }
}
