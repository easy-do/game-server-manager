package plus.easydo.generate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import plus.easydo.common.exception.BizException;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
import plus.easydo.generate.dynamic.utils.JdbcDataSourceExecTool;
import plus.easydo.generate.entity.DataSource;
import plus.easydo.generate.dynamic.core.HttpDataSourceExecTool;
import plus.easydo.generate.dto.DataSourceDto;
import plus.easydo.generate.dynamic.core.enums.SourceTypeEnum;
import plus.easydo.generate.dynamic.core.qo.DataSourceQo;
import plus.easydo.generate.mapstruct.DataSourceMapstruct;
import plus.easydo.generate.vo.DataSourceVo;
import plus.easydo.generate.mapper.DataSourceManagerMapper;
import plus.easydo.generate.service.DataSourceManagerService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * 数据源管理Service层
 * 
 * @author gebilaoyu
 */
@Service
public class DataSourceManagerServiceImpl extends ServiceImpl<DataSourceManagerMapper, DataSource> implements DataSourceManagerService {

    private static final String HTTP = "HTTP";

    /**
     * 查询数据源管理
     *
     * @param id 数据源管理ID
     * @return 数据源管理
     */
    @Override
    public DataSourceVo selectById(String id) {
        DataSource dataSource = baseMapper.selectById(id);
        return DataSourceMapstruct.INSTANCE.entityToVo(dataSource);
    }

    /**
     * 分页条件查询数据源管理列表
     *
     * @param qo 查询条件封装
     * @return 数据源管理
     */
    @Override
    public IPage<DataSourceVo> page(DataSourceQo qo) {
        qo.initInstance(DataSource.class);
        IPage<DataSource> pageData = page(qo.getPage(), qo.getWrapper());
        return pageData.convert(DataSourceMapstruct.INSTANCE::entityToVo);
    }

    /**
     * 获取所有数据源管理列表
     *
     * @param qo 查询条件封装
     * @return 数据源管理
     */
    @Override
    public List<DataSourceVo> list(DataSourceQo qo) {
        qo.initInstance(DataSource.class);
        List<DataSource> list = list(qo.getWrapper());
        return DataSourceMapstruct.INSTANCE.entityToVo(list);
    }

    @Override
    public boolean saveOrUpdate(DataSourceDto dto) {
        DataSource entity = DataSourceMapstruct.INSTANCE.dtoToEntity(dto);
        boolean updateResult = saveOrUpdate(entity);
        if (HTTP.equals(dto.getSourceType())) {
            if(Objects.nonNull(dto.getParams())){
                entity.setParams(dto.getParams());
            }
        }
        return updateResult;
    }


    /**
     * 批量删除数据源管理
     *
     * @param ids 需要删除的数据源管理ID
     * @return 结果
     */
    @Override
    public Boolean deleteByIds(String[] ids) {
        boolean result = removeByIds(Arrays.asList(ids));
        if (result){
            for(String id: ids){
                JdbcDataSourceExecTool.removeDataSource(id);
            }
        }
        return result;
    }

    /**
     * 删除数据源管理信息
     *
     * @param id 数据源管理ID
     * @return 结果
     */
    @Override
    public Boolean deleteById(Long id) {
        boolean result = removeById(id);
        if(result){
            JdbcDataSourceExecTool.removeDataSource(id.toString());
        }
        return result;
    }

    @Override
    public R<Object> test(DataSourceDto dto) {
        if(dto.getSourceType().equals(SourceTypeEnum.MYSQL.getCode())){
            return DataResult.ok(JdbcDataSourceExecTool.testDataSource(BeanUtil.beanToMap(dto)));
        }
        if (dto.getSourceType().equals(SourceTypeEnum.HTTP.getCode())){
            return DataResult.ok(HttpDataSourceExecTool.test(dto));
        }
        throw new BizException("500","不支持的数据源");
    }

}
