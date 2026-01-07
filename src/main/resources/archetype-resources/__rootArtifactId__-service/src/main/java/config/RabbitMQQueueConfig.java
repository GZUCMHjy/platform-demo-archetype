package ${package}.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ队列配置类
 * <p>
 * 遵循阿里巴巴Java开发规范：
 * 1. 队列、交换机、路由键使用常量定义
 * 2. 队列配置持久化
 * 3. 配置死信队列
 *
 * @author ${author}
 * @since 1.0.0
 */
@Configuration
public class RabbitMQQueueConfig {

    // ==================== Direct Exchange ====================

    /**
     * Direct交换机
     */
    public static final String DIRECT_EXCHANGE = "direct.exchange";

    /**
     * Direct队列
     */
    public static final String DIRECT_QUEUE = "direct.queue";

    /**
     * Direct路由键
     */
    public static final String DIRECT_ROUTING_KEY = "direct.routing.key";

    // ==================== Topic Exchange ====================

    /**
     * Topic交换机
     */
    public static final String TOPIC_EXCHANGE = "topic.exchange";

    /**
     * Topic队列1
     */
    public static final String TOPIC_QUEUE_1 = "topic.queue.1";

    /**
     * Topic队列2
     */
    public static final String TOPIC_QUEUE_2 = "topic.queue.2";

    /**
     * Topic路由键1
     */
    public static final String TOPIC_ROUTING_KEY_1 = "topic.*";

    /**
     * Topic路由键2
     */
    public static final String TOPIC_ROUTING_KEY_2 = "topic.#";

    // ==================== Fanout Exchange ====================

    /**
     * Fanout交换机
     */
    public static final String FANOUT_EXCHANGE = "fanout.exchange";

    /**
     * Fanout队列1
     */
    public static final String FANOUT_QUEUE_1 = "fanout.queue.1";

    /**
     * Fanout队列2
     */
    public static final String FANOUT_QUEUE_2 = "fanout.queue.2";

    // ==================== 死信队列 ====================

    /**
     * 死信交换机
     */
    public static final String DEAD_LETTER_EXCHANGE = "dead.letter.exchange";

    /**
     * 死信队列
     */
    public static final String DEAD_LETTER_QUEUE = "dead.letter.queue";

    /**
     * 死信路由键
     */
    public static final String DEAD_LETTER_ROUTING_KEY = "dead.letter.routing.key";

    // ==================== Direct Exchange Bean ====================

    /**
     * 声明Direct交换机
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE, true, false);
    }

    /**
     * 声明Direct队列
     *
     * @return Queue
     */
    @Bean
    public Queue directQueue() {
        return QueueBuilder.durable(DIRECT_QUEUE).build();
    }

    /**
     * 绑定Direct队列到交换机
     *
     * @return Binding
     */
    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(directQueue())
                .to(directExchange())
                .with(DIRECT_ROUTING_KEY);
    }

    // ==================== Topic Exchange Bean ====================

    /**
     * 声明Topic交换机
     *
     * @return TopicExchange
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, true, false);
    }

    /**
     * 声明Topic队列1
     *
     * @return Queue
     */
    @Bean
    public Queue topicQueue1() {
        return QueueBuilder.durable(TOPIC_QUEUE_1).build();
    }

    /**
     * 声明Topic队列2
     *
     * @return Queue
     */
    @Bean
    public Queue topicQueue2() {
        return QueueBuilder.durable(TOPIC_QUEUE_2).build();
    }

    /**
     * 绑定Topic队列1到交换机
     *
     * @return Binding
     */
    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1())
                .to(topicExchange())
                .with(TOPIC_ROUTING_KEY_1);
    }

    /**
     * 绑定Topic队列2到交换机
     *
     * @return Binding
     */
    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2())
                .to(topicExchange())
                .with(TOPIC_ROUTING_KEY_2);
    }

    // ==================== Fanout Exchange Bean ====================

    /**
     * 声明Fanout交换机
     *
     * @return FanoutExchange
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE, true, false);
    }

    /**
     * 声明Fanout队列1
     *
     * @return Queue
     */
    @Bean
    public Queue fanoutQueue1() {
        return QueueBuilder.durable(FANOUT_QUEUE_1).build();
    }

    /**
     * 声明Fanout队列2
     *
     * @return Queue
     */
    @Bean
    public Queue fanoutQueue2() {
        return QueueBuilder.durable(FANOUT_QUEUE_2).build();
    }

    /**
     * 绑定Fanout队列1到交换机
     *
     * @return Binding
     */
    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(fanoutQueue1())
                .to(fanoutExchange());
    }

    /**
     * 绑定Fanout队列2到交换机
     *
     * @return Binding
     */
    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(fanoutQueue2())
                .to(fanoutExchange());
    }

    // ==================== 死信队列 Bean ====================

    /**
     * 声明死信交换机
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE, true, false);
    }

    /**
     * 声明死信队列
     *
     * @return Queue
     */
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    /**
     * 绑定死信队列到交换机
     *
     * @return Binding
     */
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DEAD_LETTER_ROUTING_KEY);
    }
}
