package ${package}.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户数据传输对象
 *
 * @author ${author}
 * @since 1.0.0
 */
@Data
@Schema(description = "用户信息")
public class UserDTO {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须在2-20之间")
    private String username;

    @Schema(description = "密码（创建用户时必填，更新用户时可选）")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20之间")
    private String password;

    @Schema(description = "昵称")
    @Size(max = 30, message = "昵称长度不能超过30")
    private String nickname;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别（0-女，1-男，2-未知）")
    private Integer gender;

    @Schema(description = "头像URL")
    private String avatar;

    @Schema(description = "状态（0-禁用，1-启用）")
    private Integer status;
}