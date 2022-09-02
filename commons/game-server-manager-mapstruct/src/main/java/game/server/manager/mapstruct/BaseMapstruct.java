package game.server.manager.mapstruct;


import java.util.List;

/**
 * @author yuzhanfeng
 */

public interface BaseMapstruct <ENTITY,VO,DTO>{

    /**
     * entityToDto
     *
     * @param entity entity
     * @return dto
     * @author laoyu
     * @date 2022/8/29
     */
    DTO toDto(ENTITY entity);


    /**
     * DtoToEntity
     *
     * @param dto dto
     * @return entity
     * @author laoyu
     * @date 2022/8/29
     */
    ENTITY dtoToEntity(DTO dto);

    /**
     * DtoListToEntityList
     *
     * @param dto dto
     * @return entity
     * @author laoyu
     * @date 2022/8/29
     */
    List<ENTITY> dtoListToEntityList(List<DTO> dto);


    /**
     * entityToVo
     *
     * @param entity entity
     * @return vo
     * @author laoyu
     * @date 2022/8/29
     */
    VO entityToVo(ENTITY entity);


    /**
     * voToEntity
     *
     * @param vo vo
     * @return vo
     * @author laoyu
     * @date 2022/8/29
     */
    ENTITY voToEntity(VO vo);


    /**
     * entityListToVoList
     *
     * @param list list
     * @return game.server.manager.generate.core.dto.DataSourceDto
     * @author laoyu
     * @date 2022/8/29
     */
    List<VO> entityListToVoList(List<ENTITY> list);
}
