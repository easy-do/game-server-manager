package plus.easydo.uc.qo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.uc.entity.OauthClientDetails;


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
