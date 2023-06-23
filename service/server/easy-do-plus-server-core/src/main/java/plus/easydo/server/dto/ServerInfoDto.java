package plus.easydo.server.dto;

import lombok.Data;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/19
 */
@Data
public class ServerInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @NotNull(message = "id不能为空",groups = {Update.class})
    private Integer id;

    /**
     * 服务名称
     */
    @Size(min = 2,max = 12,message = "服务名称长度2-12")
    @NotNull(message = "服务名称不能为空",groups = {Insert.class, Update.class})
    private String serverName;

    /**
     * 服务地址
     */
    @Size(min = 2,max = 64,message = "服务地址2-64")
    @NotNull(message = "服务地址不能为空",groups = {Insert.class, Update.class})
    private String address;

    /**
     * 端口
     */
    @Size(min = 2,max = 6,message = "端口长度2-6")
    @NotNull(message = "端口不能为空",groups = {Insert.class, Update.class})
    private String port;

    /**
     * 用户名
     */
    @Size(min = 2,max = 12,message = "用户名长度2-12")
    @NotNull(message = "用户名不能为空",groups = {Insert.class, Update.class})
    private String userName;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空",groups = {Insert.class, Update.class})
    private String password;

}
