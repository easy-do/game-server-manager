package plus.easydo.server.qo.server;

import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.server.entity.Application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;


/**
 * 应用信息查询对象
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 00:48:08
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ApplicationQo extends MpBaseQo<Application> {

}
