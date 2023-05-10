package game.server.manager.server.qo.server;

import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.server.entity.ApplicationVersion;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

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
