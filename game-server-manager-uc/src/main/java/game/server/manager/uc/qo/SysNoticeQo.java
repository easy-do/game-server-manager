package game.server.manager.uc.qo;

import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.uc.entity.SysNotice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;


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
