package ${package}.mq.consumer;

import ${package}.config.RabbitMQQueueConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 死信队列消费者
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. 死信队列消费者需要记录详细日志
 * 2. 死信消息需要告警通知
 * 3. 死信消息需要持久化存储
 *
 * @author ${author}
 * @since 1.0.0
 */
@Slf4j
@Component
public class DeadLetterConsumer {

    /**
     * 监听死信队列
     *
     * @param message 消息
     * @param channel 通道
     * @throws IOException IO异常
     */
    @RabbitListener(queues = RabbitMQQueueConfig.DEAD_LETTER_QUEUE)
    public void consume(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 获取消息内容
            String msg = new String(message.getBody());
            log.error("死信队列接收到消息: {}", msg);

            // 记录死信消息信息
            log.error("死信消息详情: message={}, properties={}", msg, message.getMessageProperties());

            // 处理死信消息（记录到数据库、发送告警等）
            processDeadLetterMessage(message, msg);

            // 手动确认消息
            channel.basicAck(deliveryTag, false);
            log.info("死信消息处理完成: {}", msg);
        } catch (Exception e) {
            log.error("死信消息处理失败: {}", message, e);
            try {
                // 拒绝消息，不重新入队
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                log.error("死信消息拒绝失败: {}", message, ex);
            }
        }
    }

    /**
     * 处理死信消息
     *
     * @param message 消息对象
     * @param msg     消息内容
     */
    private void processDeadLetterMessage(Message message, String msg) {
        // TODO: 实现死信消息处理逻辑
        // 1. 记录到数据库
        // 2. 发送告警通知
        // 3. 分析失败原因
        log.error("处理死信消息: message={}", msg);
    }
}
