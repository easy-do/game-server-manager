package game.server.manager.generate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.generate.dto.TemplateManagementDto;
import game.server.manager.generate.entity.TemplateManagement;
import game.server.manager.generate.mapper.TemplateManagementMapper;
import game.server.manager.generate.qo.TemplateManagementQo;
import game.server.manager.generate.service.TemplateManagementService;
import game.server.manager.generate.vo.TemplateManagementVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


/**
 * 模板管理Service层
 *
 * @author gebilaoyu
 */
@Service
public class TemplateManagementServiceImpl extends ServiceImpl<TemplateManagementMapper, TemplateManagement> implements TemplateManagementService {


    /**
     * 查询模板管理
     *
     * @param id 模板管理ID
     * @return 模板管理
     */
    @Override
    public TemplateManagementVo selectById(Long id) {
        TemplateManagement templateManagement = baseMapper.selectById(id);
        return BeanUtil.copyProperties(templateManagement, TemplateManagementVo.class);
    }

    /**
     * 分页条件查询模板管理列表
     *
     * @param qo 查询条件封装
     * @return 模板管理
     */
    @Override
    public IPage<TemplateManagementVo> page(TemplateManagementQo qo) {
        IPage<TemplateManagement> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        LambdaQueryWrapper<TemplateManagement> wrapper = Wrappers.lambdaQuery();
        if (CharSequenceUtil.isNotBlank(qo.getTemplateName())) {
            wrapper.like(TemplateManagement::getTemplateName, qo.getTemplateName());
        }
        if (CharSequenceUtil.isNotBlank(qo.getTemplateCode())) {
            wrapper.like(TemplateManagement::getTemplateCode, qo.getTemplateCode());
        }
        if (CharSequenceUtil.isNotBlank(qo.getPackagePath())) {
            wrapper.like(TemplateManagement::getPackagePath, qo.getPackagePath());
        }
        IPage<TemplateManagement> iPage = baseMapper.selectPage(page, wrapper);
        return iPage.convert(entity->BeanUtil.copyProperties(entity,TemplateManagementVo.class));
    }

    /**
     * 获取所有模板管理列表
     *
     * @param qo 查询条件封装
     * @return 模板管理
     */
    @Override
    public List<TemplateManagementVo> list(TemplateManagementQo qo) {
        List<TemplateManagement> list = baseMapper.selectList(Wrappers.query());
        return BeanUtil.copyToList(list, TemplateManagementVo.class);

    }

    /**
     * 新增模板管理
     *
     * @param dto 数据传输对象
     * @return 结果
     */
    @Override
    public int insert(TemplateManagementDto dto) {
        TemplateManagement templateManagement = TemplateManagement.builder().build();
        BeanUtils.copyProperties(dto, templateManagement);
        return baseMapper.insert(templateManagement);
    }

    /**
     * 修改模板管理
     *
     * @param dto 数据传输对象
     * @return 结果
     */
    @Override
    public int update(TemplateManagementDto dto) {
        TemplateManagement templateManagement = TemplateManagement.builder().build();
        BeanUtils.copyProperties(dto, templateManagement);
        return baseMapper.updateById(templateManagement);
    }

    /**
     * 批量删除模板管理
     *
     * @param ids 需要删除的模板管理ID
     * @return 结果
     */
    @Override
    public Boolean deleteByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids));
    }

    /**
     * 删除模板管理信息
     *
     * @param id 模板管理ID
     * @return 结果
     */
    @Override
    public Boolean deleteById(Long id) {
        return removeById(id);
    }

    @Override
    public List<TemplateManagementVo> selectByIds(String[] ids) {
        List<TemplateManagement> list = baseMapper.selectBatchIds(Arrays.asList(ids));
        return BeanUtil.copyToList(list, TemplateManagementVo.class);
    }

}
