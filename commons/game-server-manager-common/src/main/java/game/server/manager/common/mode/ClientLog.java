package game.server.manager.common.mode;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 执行结果
 * @author yuzhanfeng
 */
@Data
@Builder
public class ClientLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 系统id
     */
    private String systemId;

    /**
     * ip
     */
    private String ip;

    /**
     * 类型
     */
    private String type;

    /**
     * 详情
     */
    private String info;

    /**
     * 创建时间
     */
    private Date createTime;

}