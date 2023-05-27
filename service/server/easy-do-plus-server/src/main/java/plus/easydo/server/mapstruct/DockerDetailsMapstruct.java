package plus.easydo.server.mapstruct;

import plus.easydo.server.dto.DockerDetailsDto;
import plus.easydo.server.vo.server.DockerDetailsVo;
import plus.easydo.server.entity.DockerDetails;
import plus.easydo.mapstruct.BaseMapstruct;
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
