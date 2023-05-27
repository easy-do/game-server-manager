package plus.easydo.generate.service.impl;


import lombok.RequiredArgsConstructor;
import plus.easydo.generate.entity.TemplateManagement;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import plus.easydo.generate.mapper.TemplateManagementMapper;
import plus.easydo.generate.mapstruct.TemplateMapstruct;
import plus.easydo.generate.service.TemplateManagementService;
import org.springframework.stereotype.Service;
import plus.easydo.generate.vo.TemplateManagementVo;

import java.util.Arrays;
import java.util.List;


/**
 * 模板管理Service接口
 *
 * @author yuzhanfeng
 * @date 2023-05-27 18:00:34
 */
@Service
@RequiredArgsConstructor
public class TemplateManagementServiceImpl extends ServiceImpl<TemplateManagementMapper, TemplateManagement> implements TemplateManagementService {

    private final TemplateMapstruct convert;

    @Override
    public List<TemplateManagementVo> selectByIds(String[] ids) {
        return convert.entityToVo(baseMapper.selectBatchIds(Arrays.asList(ids)));
    }
}
