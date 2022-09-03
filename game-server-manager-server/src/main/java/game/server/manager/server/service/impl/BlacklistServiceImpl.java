package game.server.manager.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.server.entity.Blacklist;
import game.server.manager.server.service.BlacklistService;
import game.server.manager.server.mapper.BlacklistMapper;
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
    public Blacklist getByIP(String ip) {
        LambdaQueryWrapper<Blacklist> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Blacklist::getIp,ip);
        return getOne(wrapper);
    }
}




