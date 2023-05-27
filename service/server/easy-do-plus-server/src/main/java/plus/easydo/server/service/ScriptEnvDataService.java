package plus.easydo.server.service;

import plus.easydo.common.vo.ScriptEnvDataVo;
import plus.easydo.server.dto.AppEnvInfoDto;
import plus.easydo.server.entity.ScriptEnvData;
import com.baomidou.mybatisplus.extension.service.IService;
import plus.easydo.common.vo.ScriptEnvDataListVo;

import java.util.List;

/**
 * @author yuzhanfeng
 * @description 针对表【script_env_data(脚本变量)】的数据库操作Service
 * @createDate 2022-05-22 17:28:24
 */
public interface ScriptEnvDataService extends IService<ScriptEnvData> {

    /**
     * 功能描述
     *
     * @param scriptId appId
     * @return java.util.List<entity.server.plus.easydo.ScriptEnvData>
     * @author laoyu
     * @date 2022/5/24
     */
    List<ScriptEnvDataListVo> listByScriptId(Long scriptId);

    /**
     * 功能描述
     *
     * @param scriptId appId
     * @return java.util.List<entity.server.plus.easydo.ScriptEnvData>
     * @author laoyu
     * @date 2022/5/24
     */
    List<ScriptEnvData> getListByScriptId(Long scriptId);

    /**
     * 功能描述
     *
     * @param scriptId appId
     * @return java.util.List<entity.server.plus.easydo.ScriptEnvData>
     * @author laoyu
     * @date 2022/5/24
     */
    List<ScriptEnvDataVo> getVoListByScriptId(Long scriptId);


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
