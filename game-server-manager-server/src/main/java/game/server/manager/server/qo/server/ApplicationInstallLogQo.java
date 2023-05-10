package game.server.manager.server.qo.server;

import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.server.entity.ApplicationInstallLog;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

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
