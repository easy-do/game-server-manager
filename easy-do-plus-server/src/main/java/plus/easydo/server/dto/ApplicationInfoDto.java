package plus.easydo.server.dto;

import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * 应用信息
 * @author yuzhanfeng
 */
@Data
public class ApplicationInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     */
    @NotNull(message = "id不能为空",groups = {Update.class})
    private String applicationId;


    /**
     * 应用名称
     */
    @Size(min = 2,max = 12,message = "应用名称长度2-12")
    @NotNull(message = "应用名称不能为空",groups = {Insert.class,Update.class})
    private String applicationName;

    /**
     * 设备类型
     */
    @NotNull(message = "设备类型不能为空",groups = {Insert.class,Update.class})
    private Integer deviceType;

    /**
     * 设备id
     */
    @NotNull(message = "所属设备不能为空",groups = {Insert.class,Update.class})
    private String deviceId;

    /**
     * 应用类型
     */
    @NotNull(message = "应用类型不能为空",groups = {Insert.class,Update.class})
    private Long appId;


}
