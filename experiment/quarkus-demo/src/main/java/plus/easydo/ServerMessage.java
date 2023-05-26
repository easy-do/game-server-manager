package plus.easydo;


/**
 * @author laoyu
 * @version 1.0
 * @description server消息
 * @date 2022/11/22
 */

public class ServerMessage {

    private String messageId;

    private int sync;

    private String type;

    private String data;

    public boolean isSync(){
        return sync == 1;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
