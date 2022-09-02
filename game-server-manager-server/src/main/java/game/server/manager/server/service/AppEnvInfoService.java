package game.server.manager.server.service;

import game.server.manager.common.vo.AppEnvInfoVo;
import game.server.manager.server.dto.AppEnvInfoDto;
import game.server.manager.server.entity.AppEnvInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import game.server.manager.common.vo.ScriptEnvListVo;

import java.util.List;

/**
 * @author yuzhanfeng
 * @description 针对表【app_env_info(应用变量)】的数据库操作Service
 * @createDate 2022-05-22 17:28:24
 */
public interface AppEnvInfoService extends IService<AppEnvInfo> {

    /**
     * 功能描述
     *
     * @param scriptId appId
     * @return java.util.List<game.server.manager.server.entity.AppEnvInfo>
     * @author laoyu
     * @date 2022/5/24
     */
    List<ScriptEnvListVo> listByScriptId(Long scriptId);

    /**
     * 功能描述
     *
     * @param scriptId appId
     * @return java.util.List<game.server.manager.server.entity.AppEnvInfo>
     * @author laoyu
     * @date 2022/5/24
     */
    List<AppEnvInfo> getListByScriptId(Long scriptId);

    /**
     * 功能描述
     *
     * @param scriptId appId
     * @return java.util.List<game.server.manager.server.entity.AppEnvInfo>
     * @author laoyu
     * @date 2022/5/24
     */
    List<AppEnvInfoVo> getVoListByScriptId(Long scriptId);


    /**
     * 功能描述
     *
     * @param id id
     * @return int
     * @author laoyu
     * @date 2022/5/24
     */
    boolean removeByAppId(Long id);

    /**
     * 保存或更新脚本变量
     *
     * @param scriptId scriptId
     * @param envList envList
     * @author laoyu
     * @date 2022/8/20
     */
    void saveOrUpdateScriptEnvList(Long scriptId, List<AppEnvInfoDto> envList);
}
