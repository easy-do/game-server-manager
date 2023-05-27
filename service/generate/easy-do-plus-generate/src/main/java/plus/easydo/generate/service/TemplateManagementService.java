package plus.easydo.generate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import plus.easydo.generate.qo.TemplateManagementQo;
import plus.easydo.generate.vo.TemplateManagementVo;
import plus.easydo.generate.dto.TemplateManagementDto;

import java.util.List;

/**
 * 模板管理Service接口
 *
 * @author gebilaoyu
 */
public interface TemplateManagementService {

    /**
     * 查询模板管理
     *
     * @param id 模板管理ID
     * @return 模板管理
     */
    TemplateManagementVo selectById(Long id);

    /**
     * 分页条件查询模板管理列表
     *
     * @param qo 条件封装
     * @return 模板管理集合
     */
    IPage<TemplateManagementVo> page(TemplateManagementQo qo);

    /**
     * 获取所有模板管理列表
     *
     * @param qo 条件封装
     * @return 模板管理集合
     */
    List<TemplateManagementVo> list(TemplateManagementQo qo);

    /**
     * 新增模板管理
     *
     * @param dto 数据传输对象
     * @return 结果
     */
    int insert(TemplateManagementDto dto);

    /**
     * 修改模板管理
     *
     * @param dto 数据传输对象
     * @return 结果
     */
    boolean update(TemplateManagementDto dto);

    /**
     * 批量删除模板管理
     *
     * @param ids 需要删除的模板管理ID
     * @return 结果
     */
    Boolean deleteByIds(Long[] ids);

    /**
     * 删除模板管理信息
     *
     * @param id 模板管理ID
     * @return 结果
     */
    Boolean deleteById(Long id);

    /**
     * 根据id集合查询列表
     *
     * @param ids ids
     * @return java.util.List
     * @author laoyu
     */
    List<TemplateManagementVo> selectByIds(String[] ids);
}
