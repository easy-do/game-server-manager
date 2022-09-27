package game.server.manager.server.service;

import game.server.manager.server.entity.Blacklist;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yuzhanfeng
* @description 针对表【blacklist(IP黑名单)】的数据库操作Service
* @createDate 2022-08-24 17:21:37
*/
public interface BlacklistService extends IService<Blacklist> {


    /**
     * getByIp
     *
     * @param ip ip
     * @return game.server.manager.server.entity.Blacklist
     * @author laoyu
     * @date 2022/8/24
     */
    Blacklist getByIp(String ip);
}
