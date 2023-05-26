package plus.easydo.common.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色信息表
 * @author yuzhanfeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleVo implements Serializable {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 角色状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 默认角色（0否 1默认）
     */
    private Integer isDefault;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    @Serial
    private static final long serialVersionUID = 1L;
}
