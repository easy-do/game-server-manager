package plus.easydo.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import plus.easydo.generate.entity.TemplateManagement;
import plus.easydo.generate.vo.TemplateManagementVo;

import java.util.List;


/**
 * 模板管理Service接口
 *
 * @author yuzhanfeng
 * @date 2023-05-27 17:58:09
 */
public interface TemplateManagementService extends IService<TemplateManagement> {


    List<TemplateManagementVo> selectByIds(String[] ids);
}
