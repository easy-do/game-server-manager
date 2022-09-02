package game.server.manager.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.AppInfoDto;
import game.server.manager.server.entity.AppInfo;
import game.server.manager.common.vo.AppInfoVo;
import game.server.manager.mybatis.plus.qo.MpBaseQo;

/**
* @author yuzhanfeng
* @description 针对表【app_info(APP信息)】的数据库操作Service
* @createDate 2022-05-22 17:44:07
*/
public interface AppInfoService extends BaseService<AppInfo, AppInfoVo, AppInfoDto> {

    /**
     * 统计用户APP数量
     *
     * @param userId userId
     * @return long
     * @author laoyu
     * @date 2022/5/24
     */
    long countByUserId(Long userId);

    /**
     * 增加热度
     *
     * @param id id
     * @author laoyu
     * @date 2022/6/17
     */
    void addHeat(Long id);

    /**
     * 是否存在
     *
     * @param id id
     * @return boolean
     * @author laoyu
     * @date 2022/7/2
     */
    boolean exists(Long id);

    /**
     * 应用商店分页查询
     *
     * @param mpBaseQo mpBaseQo
     * @return com.baomidou.mybatisplus.core.metadata.IPage<game.server.manager.server.entity.AppInfo>
     * @author laoyu
     * @date 2022/8/7
     */
    IPage<AppInfo> storePage(MpBaseQo mpBaseQo);
}
