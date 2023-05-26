package plus.easydo.uc.qo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.uc.entity.SysNotice;


/**
 * 通知公告查询对象
 * 
 * @author yuzhanfeng
 * @date 2022-10-03 17:39:25
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysNoticeQo extends MpBaseQo<SysNotice> {

}
