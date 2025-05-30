package plus.easydo.uc.service;

import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.uc.dto.SysDictTypeDto;
import plus.easydo.uc.entity.SysDictType;
import plus.easydo.web.base.BaseService;
import plus.easydo.common.vo.SysDictTypeVo;

/**
* @author yuzhanfeng
* @description 针对表【sys_dict_type(字典类型表)】的数据库操作Service
* @createDate 2022-07-22 10:19:24
*/
public interface SysDictTypeService extends BaseService<SysDictType, MpBaseQo<SysDictType>, SysDictTypeVo, SysDictTypeDto> {

    /**
     * 更改状态
     *
     * @param changeStatusDto changeStatusDto
     * @return boolean
     * @author laoyu
     * @date 2022/7/22
     */
    boolean changeStatus(ChangeStatusDto changeStatusDto);

    /**
     * 根据编号获取主键
     *
     * @param dictCode dictCode
     * @return java.lang.Long
     * @author laoyu
     * @date 2022/7/22
     */
    Long getIdByCode(String dictCode);
}
