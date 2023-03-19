package game.server.manager.server.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * docker配置信息数据传输对象
 * 
 * @author yuzhanfeng
 * @date 2022-11-13 12:10:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class DockerDetailsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 客户端 */
    private String clientId;

    /** 名称 */
    @NotNull(message = "名称必填",groups = {Insert.class, Update.class})
    private String dockerName;

    /** ip地址 */
    private String dockerHost;

    /** 证书 */
    private String dockerCert;

    /** 模式 api/client */
    private String dockerModel;

    /** 验证连接 */
    private Boolean sslFlag;

    /** 证书密码 */
    private String dockerCertPassword;

    /** 创建人 */
    private Long createBy;

    /** 创建时间 */

    private LocalDateTime createTime;

    /** 更新人 */
    private Long updateBy;

    /** 更新时间 */
    private LocalDateTime updateTime;

}
