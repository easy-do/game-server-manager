package plus.easydo.generate.mapstruct;

import org.mapstruct.Mapper;
import plus.easydo.generate.dto.DataSourceDto;
import plus.easydo.generate.entity.dataSourceManager;
import plus.easydo.generate.vo.DataSourceVo;
import plus.easydo.mapstruct.BaseMapstruct;

/**
 * @author yuzhanfeng
 */
@Mapper(componentModel = "spring")
public interface DataSourceMapstruct extends BaseMapstruct<dataSourceManager, DataSourceVo, DataSourceDto> {

}
