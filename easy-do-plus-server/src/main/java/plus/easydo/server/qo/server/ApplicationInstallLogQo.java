package plus.easydo.server.qo.server;

import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.server.entity.ApplicationInstallLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;


/**
 * 应用安装日志查询对象
 * 
 * @author yuzhanfeng
 * @date 2023-04-08 18:35:18
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ApplicationInstallLogQo extends MpBaseQo<ApplicationInstallLog> {

}
