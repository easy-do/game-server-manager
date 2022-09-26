package game.server.manager.oss.qo;

import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.oss.entity.OssManagement;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


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
