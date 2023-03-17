package game.server.manager.server.qo;

import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.server.entity.Application;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

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
