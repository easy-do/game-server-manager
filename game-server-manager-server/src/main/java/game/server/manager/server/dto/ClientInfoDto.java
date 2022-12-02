package game.server.manager.server.dto;

import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 客户端信息
 * @author yuzhanfeng
 * @TableName client_info
 */
@Data
public class ClientInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 所属用户
     */
    @Size(min = 2,max = 12,message = "名称长度2-32")
    @NotNull(message = "名称不能为空",groups = {Insert.class, Update.class})
    private String clientName;

    /**
     * 所属服务器
     */
    private Long serverId;

}
