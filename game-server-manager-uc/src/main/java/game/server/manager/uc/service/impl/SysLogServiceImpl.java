package game.server.manager.uc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.log.entity.SysLog;
import game.server.manager.log.mapper.SysLogMapper;
import game.server.manager.uc.service.SysLogService;
import org.springframework.stereotype.Service;

/**
* @author yuzhanfeng
* @description 针对表【sys_log(系统日志)】的数据库操作Service实现
* @createDate 2022-06-18 20:15:08
*/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog>
    implements SysLogService {

}




