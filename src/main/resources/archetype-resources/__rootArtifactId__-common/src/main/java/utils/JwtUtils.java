package ${package}.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * <p>
 * 基于java-jwt实现，提供JWT生成、解析、验证功能
 * 遵循阿里巴巴Java开发规范：
 * 1. 工具类必须是静态方法
 * 2. 密钥应该配置在配置文件中
 * 3. Token过期时间应该根据业务场景设置
 *
 * @author ${author}
 * @since 1.0.0
 */
public class JwtUtils {

    /**
     * 默认密钥（生产环境应该从配置文件读取）
     */
    private static final String DEFAULT_SECRET = "your-secret-key-please-change-it-in-production";

    /**
     * 默认过期时间（7天）
     */
    private static final long DEFAULT_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

    /**
     * Token前缀
     */
    private static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 用户ID声明
     */
    private static final String CLAIM_USER_ID = "userId";

    /**
     * 用户名声明
     */
    private static final String CLAIM_USERNAME = "username";

    private JwtUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // ==================== Token生成 ====================

    /**
     * 生成Token（使用默认密钥和过期时间）
     *
     * @param userId   用户ID
     * @param username 用户名
     * @return Token
     */
    public static String generateToken(Long userId, String username) {
        return generateToken(userId, username, DEFAULT_SECRET, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 生成Token（自定义过期时间）
     *
     * @param userId      用户ID
     * @param username    用户名
     * @param expireHours 过期时间（小时）
     * @return Token
     */
    public static String generateToken(Long userId, String username, long expireHours) {
        long expireTime = expireHours * 60 * 60 * 1000;
        return generateToken(userId, username, DEFAULT_SECRET, expireTime);
    }

    /**
     * 生成Token（自定义密钥和过期时间）
     *
     * @param userId      用户ID
     * @param username    用户名
     * @param secret      密钥
     * @param expireTime  过期时间（毫秒）
     * @return Token
     */
    public static String generateToken(Long userId, String username, String secret, long expireTime) {
        try {
            Date now = new Date();
            Date expireDate = new Date(now.getTime() + expireTime);

            Algorithm algorithm = Algorithm.HMAC256(secret);

            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");

            return JWT.create()
                    .withHeader(header)
                    .withClaim(CLAIM_USER_ID, userId)
                    .withClaim(CLAIM_USERNAME, username)
                    .withIssuedAt(now)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("生成Token失败: " + e.getMessage(), e);
        }
    }

    // ==================== Token解析 ====================

    /**
     * 解析Token
     *
     * @param token Token
     * @return DecodedJWT
     */
    public static DecodedJWT parseToken(String token) {
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException e) {
            throw new RuntimeException("Token解析失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从Token中获取用户ID
     *
     * @param token Token
     * @return 用户ID
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = parseToken(token);
            return jwt.getClaim(CLAIM_USER_ID).asLong();
        } catch (Exception e) {
            throw new RuntimeException("获取用户ID失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从Token中获取用户名
     *
     * @param token Token
     * @return 用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = parseToken(token);
            return jwt.getClaim(CLAIM_USERNAME).asString();
        } catch (Exception e) {
            throw new RuntimeException("获取用户名失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从Token中获取过期时间
     *
     * @param token Token
     * @return 过期时间
     */
    public static Date getExpiresAt(String token) {
        try {
            DecodedJWT jwt = parseToken(token);
            return jwt.getExpiresAt();
        } catch (Exception e) {
            throw new RuntimeException("获取过期时间失败: " + e.getMessage(), e);
        }
    }

    /**
     * 从Token中获取签发时间
     *
     * @param token Token
     * @return 签发时间
     */
    public static Date getIssuedAt(String token) {
        try {
            DecodedJWT jwt = parseToken(token);
            return jwt.getIssuedAt();
        } catch (Exception e) {
            throw new RuntimeException("获取签发时间失败: " + e.getMessage(), e);
        }
    }

    // ==================== Token验证 ====================

    /**
     * 验证Token（使用默认密钥）
     *
     * @param token Token
     * @return true-有效，false-无效
     */
    public static boolean verifyToken(String token) {
        return verifyToken(token, DEFAULT_SECRET);
    }

    /**
     * 验证Token（自定义密钥）
     *
     * @param token  Token
     * @param secret 密钥
     * @return true-有效，false-无效
     */
    public static boolean verifyToken(String token, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证Token是否过期
     *
     * @param token Token
     * @return true-已过期，false-未过期
     */
    public static boolean isExpired(String token) {
        try {
            Date expiresAt = getExpiresAt(token);
            return expiresAt.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 验证Token是否有效（未过期且格式正确）
     *
     * @param token Token
     * @return true-有效，false-无效
     */
    public static boolean isValid(String token) {
        try {
            return verifyToken(token) && !isExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== Token处理 ====================

    /**
     * 去除Token前缀（Bearer前缀）
     *
     * @param token Token
     * @return 去除前缀后的Token
     */
    public static String removeTokenPrefix(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        if (token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }
        return token;
    }

    /**
     * 添加Token前缀
     *
     * @param token Token
     * @return 添加前缀后的Token
     */
    public static String addTokenPrefix(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        if (token.startsWith(TOKEN_PREFIX)) {
            return token;
        }
        return TOKEN_PREFIX + token;
    }

    // ==================== 刷新Token ====================

    /**
     * 刷新Token
     *
     * @param token 旧Token
     * @return 新Token
     */
    public static String refreshToken(String token) {
        try {
            DecodedJWT jwt = parseToken(token);
            Long userId = jwt.getClaim(CLAIM_USER_ID).asLong();
            String username = jwt.getClaim(CLAIM_USERNAME).asString();
            return generateToken(userId, username);
        } catch (Exception e) {
            throw new RuntimeException("刷新Token失败: " + e.getMessage(), e);
        }
    }

    /**
     * 刷新Token（自定义过期时间）
     *
     * @param token       旧Token
     * @param expireHours 过期时间（小时）
     * @return 新Token
     */
    public static String refreshToken(String token, long expireHours) {
        try {
            DecodedJWT jwt = parseToken(token);
            Long userId = jwt.getClaim(CLAIM_USER_ID).asLong();
            String username = jwt.getClaim(CLAIM_USERNAME).asString();
            return generateToken(userId, username, expireHours);
        } catch (Exception e) {
            throw new RuntimeException("刷新Token失败: " + e.getMessage(), e);
        }
    }
}
