package game.server.manager.uc.dto;

import java.time.LocalDateTime;

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
 * 通知公告数据传输对象
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
public class SysNoticeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private Long id;

    /** 公告标题 */

    @NotNull(message = "公告标题必填",groups = {Insert.class, Update.class})
    private String noticeTitle;

    /** 公告类型 */

    @NotNull(message = "公告类型必填",groups = {Insert.class, Update.class})
    private String noticeType;

    /** 公告内容 */

    @NotNull(message = "公告内容必填",groups = {Insert.class, Update.class})
    private String noticeContent;

    /** 公告状态 */

    @NotNull(message = "公告状态必填",groups = {Insert.class, Update.class})
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
