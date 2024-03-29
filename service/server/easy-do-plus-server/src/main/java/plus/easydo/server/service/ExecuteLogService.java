package plus.easydo.server.service;

import plus.easydo.server.entity.ExecuteLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
* @author yuzhanfeng
* @description 针对表【execute_log(执行日志)】的数据库操作Service
* @createDate 2022-07-02 02:12:05
*/
public interface ExecuteLogService extends IService<ExecuteLog> {

    /**
     * 根据设备id删除日志
     *
     * @param id id
     * @author laoyu
     * @date 2022/8/11
     */
    void removeByDeviceId(Serializable id);
}
