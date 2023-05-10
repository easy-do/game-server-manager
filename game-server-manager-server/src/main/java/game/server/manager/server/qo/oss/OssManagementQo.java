package game.server.manager.server.qo.oss;

import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.server.entity.OssManagement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * 存储管理查询对象
 * 
 * @author yuzhanfeng
 * @date 2022-09-26 16:35:09
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OssManagementQo extends MpBaseQo<OssManagement> {

}
