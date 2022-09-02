package game.server.manager.web.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/6
 */

public interface BaseService<Entity,VO,DTO> extends IService<Entity> {


    /**
     * 获取列表集合
     *
     * @return plus.easydo.core.result.R<java.util.List<VO>>
     * @author laoyu
     * @date 2022/7/6
     */
    public List<VO> voList();

    /**
     * 分页查询
     *
     * @param mpBaseQo mpBaseQo
     * @return com.baomidou.mybatisplus.core.metadata.IPage<Entity>
     * @author laoyu
     * @date 2022/7/6
     */
    public IPage<VO> page(@RequestBody MpBaseQo mpBaseQo);

    /**
     * 获取详情
     *
     * @param id id
     * @return VO
     * @author laoyu
     * @date 2022/7/6
     */
    public VO info(Serializable id);

    /**
     * 添加
     *
     * @param dto dto
     * @return java.lang.Object
     * @author laoyu
     * @date 2022/7/6
     */
    public boolean add(DTO dto);

    /**
     * 修改
     *
     * @param dto dto
     * @return java.lang.Object
     * @author laoyu
     * @date 2022/7/6
     */
    public boolean edit(DTO dto);

    /**
     * 删除
     *
     * @param id id
     * @return java.lang.Object
     * @author laoyu
     * @date 2022/7/6
     */
    public boolean delete( Serializable id);

}
