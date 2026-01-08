package ${package}.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
/**
 * 定义 MQ 通用消息事件基类
 * @param <T>
 */
@Data
public abstract class BaseEvent<T> {
    /**
     * 实现子类实现方法
     * @param data
     * @return
     */
    public abstract EventMessage<T> buildEventMessage(T data);

    public abstract String topic();

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventMessage<T> {
        private T data;
        private Date timestamp;
        private String id;
    }
}