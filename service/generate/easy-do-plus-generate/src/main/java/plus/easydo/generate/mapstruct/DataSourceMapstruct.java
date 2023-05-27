package plus.easydo.generate.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import plus.easydo.generate.dto.DataSourceDto;
import plus.easydo.generate.entity.DataSource;
import plus.easydo.generate.vo.DataSourceVo;
import plus.easydo.mapstruct.BaseMapstruct;

/**
 * @author yuzhanfeng
 */
@Mapper
public interface DataSourceMapstruct extends BaseMapstruct<DataSource, DataSourceVo, DataSourceDto> {
    DataSourceMapstruct INSTANCE = Mappers.getMapper(DataSourceMapstruct.class);

}
