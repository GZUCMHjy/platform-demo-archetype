package ${package}.controller;

import ${package}.dto.UserDTO;
import ${package}.result.Result;
import ${package}.entity.User;
import ${package}.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. Controller只处理请求和响应
 * 2. 使用RESTful风格
 * 3. 使用统一响应格式
 * 4. 参数校验使用@Valid注解
 *
 * @author ${author}
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {

    private final UserService userService;

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询用户")
    public Result<User> getById(
            @Parameter(description = "用户ID") @PathVariable Long id) {
        log.info("根据ID查询用户: userId={}", id);
        User user = userService.getById(id);
        return Result.success(user);
    }

    /**
     * 分页查询用户列表
     *
     * @param current 当前页
     * @param size    每页大小
     * @return 用户分页数据
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询用户列表")
    public Result<IPage<User>> pageUsers(
            @Parameter(description = "当前页") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") long size) {
        log.info("分页查询用户列表: current={}, size={}", current, size);
        IPage<User> page = userService.pageUsers(current, size);
        return Result.success(page);
    }

    /**
     * 创建用户
     *
     * @param userDTO 用户DTO
     * @return 用户ID
     */
    @PostMapping
    @Operation(summary = "创建用户")
    public Result<Long> createUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("创建用户: userDTO={}", userDTO);
        Long userId = userService.createUser(userDTO);
        return Result.success("创建用户成功", userId);
    }

    /**
     * 更新用户
     *
     * @param id      用户ID
     * @param userDTO 用户DTO
     * @return 是否成功
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新用户")
    public Result<Boolean> updateUser(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        log.info("更新用户: userId={}, userDTO={}", id, userDTO);
        userDTO.setId(id);
        boolean success = userService.updateUser(userDTO);
        return Result.success("更新用户成功", success);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 是否成功
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public Result<Boolean> deleteUser(
            @Parameter(description = "用户ID") @PathVariable Long id) {
        log.info("删除用户: userId={}", id);
        boolean success = userService.deleteUser(id);
        return Result.success("删除用户成功", success);
    }
}
