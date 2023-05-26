package plus.easydo.uc.qo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.uc.entity.SysResource;


/**
 * 系统资源查询对象
 * 
 * @author yuzhanfeng
 * @date 2022-09-19 22:43:39
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysResourceQo extends MpBaseQo<SysResource> {

}
