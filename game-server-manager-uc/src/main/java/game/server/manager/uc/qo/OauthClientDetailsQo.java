package game.server.manager.uc.qo;

import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.uc.entity.OauthClientDetails;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;


/**
 * 授权客户端信息查询对象
 * 
 * @author yuzhanfeng
 * @date 2023-02-27 16:01:25
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OauthClientDetailsQo extends MpBaseQo<OauthClientDetails> {

}
