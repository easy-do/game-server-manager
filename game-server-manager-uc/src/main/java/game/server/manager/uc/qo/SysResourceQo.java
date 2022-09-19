package game.server.manager.uc.qo;

import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.uc.entity.SysResource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 系统资源查询对象
 * 
 * @author yuzhanfeng
 * @date 2022-09-19 22:43:39
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysResourceQo extends MpBaseQo<SysResource> {

}
