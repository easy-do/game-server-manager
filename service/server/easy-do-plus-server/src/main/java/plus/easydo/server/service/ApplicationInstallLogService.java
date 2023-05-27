package plus.easydo.server.service;

import plus.easydo.common.vo.LogResultVo;
import plus.easydo.server.dto.ApplicationInstallLogDto;
import plus.easydo.server.vo.server.ApplicationInstallLogVo;
import plus.easydo.web.base.BaseService;
import plus.easydo.server.qo.server.ApplicationInstallLogQo;
import plus.easydo.server.entity.ApplicationInstallLog;

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
