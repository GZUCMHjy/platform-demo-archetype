package ${package}.mq.consumer;

import ${package}.config.RabbitMQQueueConfig;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Direct队列消费者
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. 消费者必须进行手动确认
 * 2. 消息处理异常需要有重试机制
 * 3. 消费者需要幂等性处理
 *
 * @author ${author}
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DirectConsumer {

    /**
     * 监听Direct队列
     *
     * @param message 消息
     * @param channel 通道
     * @throws IOException IO异常
     */
    @RabbitListener(queues = RabbitMQQueueConfig.DIRECT_QUEUE)
    public void consume(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 获取消息内容
            String msg = new String(message.getBody());
            log.info("Direct消费者接收到消息: {}", msg);

            // 处理业务逻辑
            processMessage(msg);

            // 手动确认消息
            channel.basicAck(deliveryTag, false);
            log.info("Direct消息处理成功: {}", msg);
        } catch (Exception e) {
            log.error("Direct消息处理失败: {}", message, e);
            try {
                // 拒绝消息，不重新入队（进入死信队列）
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                log.error("消息拒绝失败: {}", message, ex);
            }
        }
    }

    /**
     * 处理消息业务逻辑
     *
     * @param message 消息
     */
    private void processMessage(String message) {
        // TODO: 实现具体的业务逻辑
        log.info("处理Direct消息: {}", message);
    }
}
