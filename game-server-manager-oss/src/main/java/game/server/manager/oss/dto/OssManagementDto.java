package game.server.manager.oss.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
 * 存储管理数据传输对象
 * 
 * @author yuzhanfeng
 * @date 2022-09-26 16:35:09
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class OssManagementDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long id;

    /** 分组 */
    private String groupName;

    /** 路径 */
    private String filePath;

    /** 文件名 */

    private String fileName;

    /** 文件大小/kb */

    private BigDecimal fileSize;

    /** oss类型 */

    private String ossType;

    /** 创建时间 */

    private LocalDateTime createTime;

    /** 更新时间 */

    private LocalDateTime updateTime;

    /** 备注 */
    private String remark;

}
