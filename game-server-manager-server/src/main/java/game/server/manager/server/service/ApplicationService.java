package game.server.manager.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.server.dto.InstallApplicationDto;
import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.ApplicationDto;
import game.server.manager.server.qo.ApplicationQo;
import game.server.manager.server.vo.ApplicationVo;
import game.server.manager.server.entity.Application;


/**
 * 应用信息Service接口
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 00:48:08
 */
public interface ApplicationService extends BaseService<Application, ApplicationQo, ApplicationVo, ApplicationDto>{

    /**
     * 应用商店分页查询
     *
     * @param applicationQo applicationQo
     * @return com.baomidou.mybatisplus.core.metadata.IPage<game.server.manager.server.vo.ApplicationVo>
     * @author laoyu
     * @date 2023/3/18
     */
    IPage<ApplicationVo> storePage(ApplicationQo applicationQo);

    /**
     * 安装应用
     *
     * @param installApplicationDto installApplicationDto
     * @return java.lang.Object
     * @author laoyu
     * @date 2023/3/19
     */
    Object install(InstallApplicationDto installApplicationDto);
}
