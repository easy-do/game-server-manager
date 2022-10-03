package game.server.manager.uc.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.io.Serial;
import java.io.Serializable;

/**
 * 通知公告数据展示对象
 * 
 * @author yuzhanfeng
 * @date 2022-10-03 17:39:25
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SysNoticeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private Long id;
    /** 公告标题 */
    private String noticeTitle;
    /** 公告类型 */
    private String noticeType;
    /** 公告内容 */
    private String noticeContent;
    /** 公告状态 */
    private String status;
    /** 创建者 */
    private Long createBy;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新者 */
    private Long updateBy;
    /** 更新时间 */
    private LocalDateTime updateTime;
    /** 备注 */
    private String remark;
    /** 删除标记 */
    private Long delFlag;

}
