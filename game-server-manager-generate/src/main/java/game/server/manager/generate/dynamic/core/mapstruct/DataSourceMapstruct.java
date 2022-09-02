package game.server.manager.generate.dynamic.core.mapstruct;

import game.server.manager.generate.dynamic.core.DataSource;
import game.server.manager.generate.dynamic.core.dto.DataSourceDto;
import game.server.manager.generate.dynamic.core.vo.DataSourceVo;
import game.server.manager.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author yuzhanfeng
 */
@Mapper
public interface DataSourceMapstruct extends BaseMapstruct<DataSource,DataSourceVo,DataSourceDto> {
    DataSourceMapstruct INSTANCE = Mappers.getMapper(DataSourceMapstruct.class);

}
