package plus.easydo.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import plus.easydo.server.entity.ExecuteLog;
import plus.easydo.server.service.ExecuteLogService;
import plus.easydo.server.mapper.ExecuteLogMapper;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
* @author yuzhanfeng
* @description 针对表【execute_log(执行日志)】的数据库操作Service实现
* @createDate 2022-07-02 02:12:05
*/
@Service
public class ExecuteLogServiceImpl extends ServiceImpl<ExecuteLogMapper, ExecuteLog>
    implements ExecuteLogService{

    @Override
    public void removeByDeviceId(Serializable id) {
        LambdaQueryWrapper<ExecuteLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ExecuteLog::getDeviceId,id);
        remove(wrapper);
    }
}




