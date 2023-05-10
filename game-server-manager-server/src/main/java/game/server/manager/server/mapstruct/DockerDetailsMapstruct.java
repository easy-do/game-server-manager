package game.server.manager.server.mapstruct;

import game.server.manager.server.dto.DockerDetailsDto;
import game.server.manager.server.entity.DockerDetails;
import game.server.manager.server.vo.server.DockerDetailsVo;
import game.server.manager.mapstruct.BaseMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * docker配置信息转换类
 * @author yuzhanfeng
 * @date 2022-11-13 12:10:30
 */
@Mapper
public interface DockerDetailsMapstruct extends BaseMapstruct<DockerDetails, DockerDetailsVo, DockerDetailsDto> {

    DockerDetailsMapstruct INSTANCE = Mappers.getMapper(DockerDetailsMapstruct.class);

}
