package ${package}.enums;

/**
 * 错误码枚举
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. 错误码为5位数字，前两位表示业务场景，后三位表示具体错误
 * 2. 错误码不使用连续数字，预留扩展空间
 * 3. 每个错误码必须有明确的错误描述
 *
 * @author ${author}
 * @since 1.0.0
 */
public enum ErrorCode {

    // ========== 通用错误码 10xxx ==========
    SUCCESS(10000, "操作成功"),
    ERROR(10001, "操作失败"),
    PARAM_ERROR(10002, "参数错误"),
    PARAM_MISSING(10003, "缺少必要参数"),
    PARAM_VALID_ERROR(10004, "参数校验失败"),
    OPERATION_ERROR(10005, "操作失败"),

    // ========== 用户相关错误码 11xxx ==========
    USER_NOT_EXIST(11001, "用户不存在"),
    USER_PASSWORD_ERROR(11002, "密码错误"),
    USER_ACCOUNT_DISABLED(11003, "账号已禁用"),
    USER_ACCOUNT_LOCKED(11004, "账号已锁定"),
    USER_TOKEN_EXPIRED(11005, "Token已过期"),
    USER_TOKEN_INVALID(11006, "Token无效"),
    USER_PERMISSION_DENIED(11007, "权限不足"),
    USER_ALREADY_EXIST(11008, "用户已存在"),

    // ========== 业务相关错误码 12xxx ==========
    BDATA_NOT_EXIST(12001, "数据不存在"),
    DATA_ALREADY_EXIST(12002, "数据已存在"),
    DATA_STATE_ERROR(12003, "数据状态异常"),
    BDATA_VERSION_ERROR(12004, "数据版本冲突"),

    // ========== 系统相关错误码 13xxx ==========
    SYSTEM_ERROR(13001, "系统异常"),
    SYSTEM_BUSY(13002, "系统繁忙，请稍后再试"),
    SYSTEM_MAINTENANCE(13003, "系统维护中"),

    // ========== 外部服务错误码 14xxx ==========
    RPC_ERROR(14001, "远程调用失败"),
    RPC_TIMEOUT(14002, "远程调用超时"),

    // ========== 文件相关错误码 15xxx ==========
    FILE_UPLOAD_ERROR(15001, "文件上传失败"),
    FILE_DOWNLOAD_ERROR(15002, "文件下载失败"),
    FILE_TYPE_ERROR(15003, "文件类型不支持"),
    FILE_SIZE_EXCEED(15004, "文件大小超出限制"),
    FILE_NOT_EXIST(15005, "文件不存在"),

    // ========== Redis相关错误码 16xxx ==========
    REDIS_ERROR(16001, "Redis操作失败"),
    REDIS_KEY_NOT_EXIST(16002, "Redis键不存在"),
    REDIS_LOCK_ERROR(16003, "分布式锁获取失败"),

    // ========== MQ相关错误码 17xxx ==========
    MQ_SEND_ERROR(17001, "消息发送失败"),
    MQ_CONSUME_ERROR(17002, "消息消费失败");

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误描述
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 根据错误码获取错误枚举
     *
     * @param code 错误码
     * @return 错误枚举
     */
    public static ErrorCode getErrorCode(int code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        return ERROR;
    }
}
