package game.server.manager.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/9
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 审核业务主键
     */
    private Long id;

    /**
     * 审核类型
     */
    private Integer auditType;

    /**
     * 是否通过
     */
    private Integer status;

    /**
     * 分数  正数或负数
     */
    private Long points;

    /**
     * 描述
     */
    private String description;
}
