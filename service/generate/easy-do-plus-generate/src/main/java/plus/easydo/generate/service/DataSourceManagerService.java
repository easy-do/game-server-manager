package plus.easydo.generate.service;



import com.baomidou.mybatisplus.extension.service.IService;
import plus.easydo.common.result.R;
import plus.easydo.generate.dto.DataSourceDto;
import plus.easydo.generate.entity.dataSourceManager;


/**
 * 数据源管理Service接口
 * 
 * @author gebilaoyu
 */
public interface DataSourceManagerService extends IService<dataSourceManager> {


    /**
     * 测试数据源
     *
     * @param dto dto
     * @return java.lang.Boolean
     * @author laoyu
     */
    R<Object> test(DataSourceDto dto);
}
