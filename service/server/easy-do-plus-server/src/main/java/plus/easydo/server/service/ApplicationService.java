package plus.easydo.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import plus.easydo.server.dto.ApplicationDto;
import plus.easydo.server.dto.InstallApplicationDto;
import plus.easydo.server.vo.server.ApplicationVo;
import plus.easydo.web.base.BaseService;
import plus.easydo.server.qo.server.ApplicationQo;
import plus.easydo.server.entity.Application;


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
     * @return com.baomidou.mybatisplus.core.metadata.IPage<server.vo.server.plus.easydo.ApplicationVo>
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
    boolean install(InstallApplicationDto installApplicationDto);
}
