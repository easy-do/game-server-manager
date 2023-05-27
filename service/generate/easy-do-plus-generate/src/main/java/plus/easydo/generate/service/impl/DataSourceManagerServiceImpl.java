package plus.easydo.generate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import plus.easydo.common.exception.BizException;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
import plus.easydo.generate.dynamic.utils.JdbcDataSourceExecTool;
import plus.easydo.generate.entity.dataSourceManager;
import plus.easydo.generate.dynamic.core.HttpDataSourceExecTool;
import plus.easydo.generate.dto.DataSourceDto;
import plus.easydo.generate.dynamic.core.enums.SourceTypeEnum;
import plus.easydo.generate.mapper.DataSourceManagerMapper;
import plus.easydo.generate.service.DataSourceManagerService;
import org.springframework.stereotype.Service;



/**
 * 数据源管理Service层
 * 
 * @author gebilaoyu
 */
@Service
public class DataSourceManagerServiceImpl extends ServiceImpl<DataSourceManagerMapper, dataSourceManager> implements DataSourceManagerService {


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
