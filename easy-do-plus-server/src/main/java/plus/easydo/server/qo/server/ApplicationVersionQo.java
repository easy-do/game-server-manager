package plus.easydo.server.qo.server;

import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.server.entity.ApplicationVersion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;


/**
 * 应用版本信息查询对象
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 14:56:21
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ApplicationVersionQo extends MpBaseQo<ApplicationVersion> {

}
