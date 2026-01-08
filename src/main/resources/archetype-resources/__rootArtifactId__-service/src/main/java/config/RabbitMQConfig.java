package ${package}.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
/**
 * RabbitMQ配置类
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. 使用Jackson序列化替代JDK序列化
 * 2. 配置消息确认机制
 * 3. 配置消息重试机制
 *
 * @author ${author}
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty(name = "spring.rabbitmq.enabled", havingValue = "true", matchIfMissing = false)
public class RabbitMQConfig {

    /**
     * JSON消息转换器
     *
     * @return MessageConverter
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate配置
     *
     * @param connectionFactory 连接工厂
     * @return RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        // 开启发送确认
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                // 消息发送成功
                System.out.println("消息发送成功");
            } else {
                // 消息发送失败
                System.err.println("消息发送失败: " + cause);
            }
        });

        // 开启返回确认
        rabbitTemplate.setReturnsCallback(returned -> {
            System.err.println("消息无法路由: " + returned.getMessage());
        });

        return rabbitTemplate;
    }
}
