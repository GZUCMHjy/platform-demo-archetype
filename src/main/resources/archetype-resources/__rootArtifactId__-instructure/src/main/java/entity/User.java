package ${package}.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体类
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. 使用Lombok简化代码
 * 2. 使用参数校验注解
 * 3. 使用Swagger注解生成接口文档
 *
 * @author ${author}
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@Schema(description = "用户信息")
public class User extends BaseEntity {

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须在2-20之间")
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20之间")
    private String password;

    @Schema(description = "昵称")
    @Size(max = 30, message = "昵称长度不能超过30")
    private String nickname;

    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "手机号")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Schema(description = "性别（0-女，1-男，2-未知）")
    private Integer gender;

    @Schema(description = "头像URL")
    private String avatar;

    @Schema(description = "状态（0-禁用，1-启用）")
    private Integer status;
}
