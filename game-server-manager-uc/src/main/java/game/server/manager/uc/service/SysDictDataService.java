package game.server.manager.uc.service;

import game.server.manager.common.dto.ChangeStatusDto;
import game.server.manager.uc.dto.SysDictDataDto;
import game.server.manager.uc.entity.SysDictData;
import game.server.manager.web.base.BaseService;
import game.server.manager.common.vo.SysDictDataVo;

import java.util.List;

/**
* @author yuzhanfeng
* @description 针对表【sys_dict_data(字典数据表)】的数据库操作Service
* @createDate 2022-07-22 10:19:20
*/
public interface SysDictDataService extends  BaseService<SysDictData, SysDictDataVo, SysDictDataDto> {

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
     * 根据字典编码获取所有键值对
     *
     * @param dictCode dictCode
     * @return java.util.List<game.server.manager.server.vo.SysDictDataVo>
     * @author laoyu
     * @date 2022/7/22
     */
    List<SysDictDataVo> listByCode(String dictCode);


    /**
     * 获得单个字典参数
     *
     * @param dictCode dictCode
     * @param dictDataKey dictDataKey
     * @return game.server.manager.server.vo.SysDictDataVo
     * @author laoyu
     * @date 2022/7/23
     */
    SysDictDataVo getSingleDictData(String dictCode, String dictDataKey);
}
