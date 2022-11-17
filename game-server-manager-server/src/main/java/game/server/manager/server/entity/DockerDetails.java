package game.server.manager.server.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;


/**
 * docker配置信息数据库映射对象
 * 
 * @author yuzhanfeng
 * @date 2022-11-13 12:10:30
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("docker_details")
public class DockerDetails implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 名称 */
    private String dockerName;

    /** ip地址 */
    private String dockerHost;

    /** 证书 */
    @TableField(exist = false,typeHandler = org.apache.ibatis.type.BlobTypeHandler.class)
    private String dockerCert;

    /** 验证连接 */
    private Long dockerIsSsl;

    /** 证书密码 */
    private String dockerCertPassword;

    /** 创建人 */
    private Long createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    /** 更新人 */
    private Long updateBy;

    /** 更新时间 */
    private LocalDateTime updateTime;

}
