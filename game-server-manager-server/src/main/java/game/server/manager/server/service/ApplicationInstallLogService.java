package game.server.manager.server.service;

import game.server.manager.common.vo.LogResultVo;
import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.ApplicationInstallLogDto;
import game.server.manager.server.qo.server.ApplicationInstallLogQo;
import game.server.manager.server.vo.server.ApplicationInstallLogVo;
import game.server.manager.server.entity.ApplicationInstallLog;

import java.util.List;


/**
 * 应用安装日志Service接口
 * 
 * @author yuzhanfeng
 * @date 2023-04-08 18:35:18
 */
public interface ApplicationInstallLogService extends BaseService<ApplicationInstallLog, ApplicationInstallLogQo, ApplicationInstallLogVo, ApplicationInstallLogDto>{


    LogResultVo getLog(String logId);

    List<ApplicationInstallLog> getNoFinishLog();
}
