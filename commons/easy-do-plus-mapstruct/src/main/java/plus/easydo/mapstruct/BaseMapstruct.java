package plus.easydo.mapstruct;


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
    List<ENTITY> dtoToEntity(List<DTO> dto);


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
     * entityToVo
     *
     * @param list list
     * @return plus.easydo.generate.core.dto.DataSourceDto
     * @author laoyu
     * @date 2022/8/29
     */
    List<VO> entityToVo(List<ENTITY> list);
}
