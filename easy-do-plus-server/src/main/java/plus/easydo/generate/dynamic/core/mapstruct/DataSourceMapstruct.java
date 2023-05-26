package plus.easydo.generate.dynamic.core.mapstruct;

import plus.easydo.generate.dynamic.core.DataSource;
import plus.easydo.generate.dynamic.core.dto.DataSourceDto;
import plus.easydo.generate.dynamic.core.vo.DataSourceVo;
import plus.easydo.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author yuzhanfeng
 */
@Mapper
public interface DataSourceMapstruct extends BaseMapstruct<DataSource,DataSourceVo,DataSourceDto> {
    DataSourceMapstruct INSTANCE = Mappers.getMapper(DataSourceMapstruct.class);

}
