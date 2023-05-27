package plus.easydo.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import plus.easydo.server.entity.Blacklist;
import plus.easydo.server.service.BlacklistService;
import plus.easydo.server.mapper.BlacklistMapper;
import org.springframework.stereotype.Service;

/**
* @author yuzhanfeng
* @description 针对表【blacklist(IP黑名单)】的数据库操作Service实现
* @createDate 2022-08-24 17:21:37
*/
@Service
public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist>
    implements BlacklistService{

    @Override
    public Blacklist getByIp(String ip) {
        LambdaQueryWrapper<Blacklist> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Blacklist::getIp,ip);
        return getOne(wrapper);
    }
}




