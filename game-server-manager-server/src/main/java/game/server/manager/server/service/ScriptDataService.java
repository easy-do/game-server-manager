package game.server.manager.server.service;

import game.server.manager.common.vo.ScriptDataVo;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.server.entity.ScriptData;
import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.ScriptDataDto;

import java.util.List;

/**
* @author yuzhanfeng
* @description 针对表【script_data】的数据库操作Service
* @createDate 2022-05-26 18:30:13
*/
public interface ScriptDataService extends BaseService<ScriptData, MpBaseQo<ScriptData>, ScriptDataVo, ScriptDataDto> {

    /**
     * 统计用户创建数量
     *
     * @param userId userId
     * @return long
     * @author laoyu
     * @date 2022/5/26
     */
    long countByUserId(Long userId);

    /**
     * 是否存在
     *
     * @param appScriptId appScriptId
     * @return boolean
     * @author laoyu
     * @date 2022/5/27
     */
    boolean exist(Long appScriptId);

    /**
     * 增加热度
     *
     * @param id id
     * @author laoyu
     * @date 2022/6/17
     */
    void addHeat(Long id);

    /**
     * 是否存在
     *
     * @param scriptId scriptId
     * @return boolean
     * @author laoyu
     * @date 2022/7/2
     */
    boolean exists(Long scriptId);

}
