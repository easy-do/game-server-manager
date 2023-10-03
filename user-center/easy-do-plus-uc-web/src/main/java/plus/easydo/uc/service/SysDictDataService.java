package plus.easydo.uc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.easydo.common.dto.ChangeStatusDto;
import plus.easydo.uc.entity.SysDictData;
import plus.easydo.common.vo.SysDictDataVo;

import java.util.List;
import java.util.Map;

/**
* @author yuzhanfeng
* @description 针对表【sys_dict_data(字典数据表)】的数据库操作Service
* @createDate 2022-07-22 10:19:20
*/
public interface SysDictDataService extends IService<SysDictData> {

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
     * @return java.util.List<plus.easydo.server.vo.SysDictDataVo>
     * @author laoyu
     * @date 2022/7/22
     */
    List<SysDictDataVo> listByCode(String dictCode);


    /**
     * 获得单个字典参数
     *
     * @param dictCode dictCode
     * @param dictDataKey dictDataKey
     * @return plus.easydo.server.vo.SysDictDataVo
     * @author laoyu
     * @date 2022/7/23
     */
    SysDictDataVo getSingleDictData(String dictCode, String dictDataKey);

    /**
     * 获取字典map结构数据
     *
     * @param
     * @return java.util.Map<java.lang.String,vo.plus.easydo.common.SysDictDataVo>
     * @author laoyu
     * @date 2022/10/23
     */
    Map<String,List<SysDictDataVo>> getDictDataMap();
}
