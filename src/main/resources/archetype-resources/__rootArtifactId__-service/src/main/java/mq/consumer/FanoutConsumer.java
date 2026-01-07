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
 * Fanout队列消费者
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
public class FanoutConsumer {

    /**
     * 监听Fanout队列1
     *
     * @param message 消息
     * @param channel 通道
     * @throws IOException IO异常
     */
    @RabbitListener(queues = RabbitMQQueueConfig.FANOUT_QUEUE_1)
    public void consumeQueue1(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 获取消息内容
            String msg = new String(message.getBody());
            log.info("Fanout消费者1接收到消息: {}", msg);

            // 处理业务逻辑
            processMessageQueue1(msg);

            // 手动确认消息
            channel.basicAck(deliveryTag, false);
            log.info("Fanout消息1处理成功: {}", msg);
        } catch (Exception e) {
            log.error("Fanout消息1处理失败: {}", message, e);
            try {
                // 拒绝消息，不重新入队（进入死信队列）
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                log.error("消息拒绝失败: {}", message, ex);
            }
        }
    }

    /**
     * 监听Fanout队列2
     *
     * @param message 消息
     * @param channel 通道
     * @throws IOException IO异常
     */
    @RabbitListener(queues = RabbitMQQueueConfig.FANOUT_QUEUE_2)
    public void consumeQueue2(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 获取消息内容
            String msg = new String(message.getBody());
            log.info("Fanout消费者2接收到消息: {}", msg);

            // 处理业务逻辑
            processMessageQueue2(msg);

            // 手动确认消息
            channel.basicAck(deliveryTag, false);
            log.info("Fanout消息2处理成功: {}", msg);
        } catch (Exception e) {
            log.error("Fanout消息2处理失败: {}", message, e);
            try {
                // 拒绝消息，不重新入队（进入死信队列）
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                log.error("消息拒绝失败: {}", message, ex);
            }
        }
    }

    /**
     * 处理消息业务逻辑（队列1）
     *
     * @param message 消息
     */
    private void processMessageQueue1(String message) {
        // TODO: 实现具体的业务逻辑
        log.info("处理Fanout消息1: {}", message);
    }

    /**
     * 处理消息业务逻辑（队列2）
     *
     * @param message 消息
     */
    private void processMessageQueue2(String message) {
        // TODO: 实现具体的业务逻辑
        log.info("处理Fanout消息2: {}", message);
    }
}
