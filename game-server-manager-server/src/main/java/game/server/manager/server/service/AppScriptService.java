package game.server.manager.server.service;

import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.AppScriptDto;
import game.server.manager.server.entity.AppScript;
import game.server.manager.common.vo.AppScriptVo;

import java.util.List;

/**
* @author yuzhanfeng
* @description 针对表【app_script】的数据库操作Service
* @createDate 2022-05-26 18:30:13
*/
public interface AppScriptService extends BaseService<AppScript, AppScriptVo, AppScriptDto> {

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
     * @param appScriptId appScriptId
     * @return boolean
     * @author laoyu
     * @date 2022/7/2
     */
    boolean exists(Long appScriptId);

    /**
     * 功能描述
     *
     * @param appId appId
     * @return java.util.List<game.server.manager.common.vo.AppScriptVo>
     * @author laoyu
     * @date 2022/8/21
     */
    List<AppScriptVo> listByAppid(Long appId);
}
