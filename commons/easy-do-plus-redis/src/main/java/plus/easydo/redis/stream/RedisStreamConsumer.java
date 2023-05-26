package plus.easydo.redis.stream;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/2/20
 */
public class RedisStreamConsumer {
    private String group;
    private String topic;
    private String consumerId;

    public RedisStreamConsumer() {
    }

    public String getGroup() {
        return this.group;
    }

    public String getTopic() {
        return this.topic;
    }

    public String getConsumerId() {
        return this.consumerId;
    }

    public void setGroup(final String group) {
        this.group = group;
    }

    public void setTopic(final String topic) {
        this.topic = topic;
    }

    public void setConsumerId(final String consumerId) {
        this.consumerId = consumerId;
    }

}
