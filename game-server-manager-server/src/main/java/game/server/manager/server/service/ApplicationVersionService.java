package game.server.manager.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.web.base.BaseService;
import game.server.manager.server.dto.ApplicationVersionDto;
import game.server.manager.server.qo.ApplicationVersionQo;
import game.server.manager.server.vo.ApplicationVersionVo;
import game.server.manager.server.entity.ApplicationVersion;


/**
 * 应用版本信息Service接口
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 14:56:21
 */
public interface ApplicationVersionService extends BaseService<ApplicationVersion, ApplicationVersionQo, ApplicationVersionVo, ApplicationVersionDto>{


}
