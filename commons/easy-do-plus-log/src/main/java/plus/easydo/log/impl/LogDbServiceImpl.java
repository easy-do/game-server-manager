package plus.easydo.log.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import plus.easydo.log.Log;
import plus.easydo.log.LogDbService;
import plus.easydo.log.entity.SysLog;
import plus.easydo.log.mapper.SysLogMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author laoyu
 * @version 1.0
 * @description 保存日志实现类
 * @date 2022/6/18
 */
@Service
public class LogDbServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements LogDbService {

    @Override
    public void save(Log log) {
        SysLog entity  = SysLog.builder().data(JSON.toJSONString(log)).createTime(LocalDateTime.now()).build();
        baseMapper.insert(entity);
    }



}
