package ${package}.mq.producer;

import ${package}.config.RabbitMQQueueConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ消息生产者
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. 发送消息时必须指定交换机和路由键
 * 2. 消息发送失败需要有重试机制
 * 3. 重要消息需要持久化
 *
 * @author ${author}
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MQProducer {

    private final RabbitTemplate rabbitTemplate;

    // ==================== Direct Exchange ====================

    /**
     * 发送消息到Direct交换机
     *
     * @param message 消息
     */
    public void sendToDirect(Object message) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQQueueConfig.DIRECT_EXCHANGE,
                    RabbitMQQueueConfig.DIRECT_ROUTING_KEY,
                    message
            );
            log.info("发送Direct消息成功: {}", message);
        } catch (Exception e) {
            log.error("发送Direct消息失败: {}", message, e);
            throw new RuntimeException("发送Direct消息失败: " + e.getMessage(), e);
        }
    }

    // ==================== Topic Exchange ====================

    /**
     * 发送消息到Topic交换机
     *
     * @param routingKey 路由键
     * @param message    消息
     */
    public void sendToTopic(String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQQueueConfig.TOPIC_EXCHANGE,
                    routingKey,
                    message
            );
            log.info("发送Topic消息成功: routingKey={}, message={}", routingKey, message);
        } catch (Exception e) {
            log.error("发送Topic消息失败: routingKey={}, message={}", routingKey, message, e);
            throw new RuntimeException("发送Topic消息失败: " + e.getMessage(), e);
        }
    }

    // ==================== Fanout Exchange ====================

    /**
     * 发送消息到Fanout交换机
     *
     * @param message 消息
     */
    public void sendToFanout(Object message) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQQueueConfig.FANOUT_EXCHANGE,
                    "",
                    message
            );
            log.info("发送Fanout消息成功: {}", message);
        } catch (Exception e) {
            log.error("发送Fanout消息失败: {}", message, e);
            throw new RuntimeException("发送Fanout消息失败: " + e.getMessage(), e);
        }
    }

    // ==================== 延迟消息 ====================

    /**
     * 发送延迟消息（需要延迟插件支持）
     *
     * @param exchange   交换机
     * @param routingKey 路由键
     * @param message    消息
     * @param delay      延迟时间（毫秒）
     */
    public void sendDelay(String exchange, String routingKey, Object message, long delay) {
        try {
            rabbitTemplate.convertAndSend(
                    exchange,
                    routingKey,
                    message,
                    msg -> {
                        msg.getMessageProperties().setDelay((int) delay);
                        return msg;
                    }
            );
            log.info("发送延迟消息成功: exchange={}, routingKey={}, message={}, delay={}", exchange, routingKey, message, delay);
        } catch (Exception e) {
            log.error("发送延迟消息失败: exchange={}, routingKey={}, message={}, delay={}", exchange, routingKey, message, delay, e);
            throw new RuntimeException("发送延迟消息失败: " + e.getMessage(), e);
        }
    }

    // ==================== 通用发送方法 ====================

    /**
     * 发送消息（通用方法）
     *
     * @param exchange   交换机
     * @param routingKey 路由键
     * @param message    消息
     */
    public void send(String exchange, String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            log.info("发送消息成功: exchange={}, routingKey={}, message={}", exchange, routingKey, message);
        } catch (Exception e) {
            log.error("发送消息失败: exchange={}, routingKey={}, message={}", exchange, routingKey, message, e);
            throw new RuntimeException("发送消息失败: " + e.getMessage(), e);
        }
    }

    /**
     * 发送消息（带回调）
     *
     * @param exchange   交换机
     * @param routingKey 路由键
     * @param message    消息
     * @param callback   回调函数
     */
    public void sendWithCallback(String exchange, String routingKey, Object message, Runnable callback) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            log.info("发送消息成功: exchange={}, routingKey={}, message={}", exchange, routingKey, message);
            if (callback != null) {
                callback.run();
            }
        } catch (Exception e) {
            log.error("发送消息失败: exchange={}, routingKey={}, message={}", exchange, routingKey, message, e);
            throw new RuntimeException("发送消息失败: " + e.getMessage(), e);
        }
    }
}
