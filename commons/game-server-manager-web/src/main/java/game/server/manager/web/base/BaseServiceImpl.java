package game.server.manager.web.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.auth.AuthorizationUtil;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/6
 */

public abstract class BaseServiceImpl<Entity,QO extends MpBaseQo<Entity>, VO, DTO, MAPPER extends BaseMapper<Entity>>  extends ServiceImpl<MAPPER, Entity> implements BaseService<Entity,QO,VO,DTO> {


    @Autowired
    private AuthorizationUtil authorizationUtil;



    protected UserInfoVo getUser(){
        return authorizationUtil.getUser();
    }

    protected static boolean isAdmin(){
        return AuthorizationUtil.isAdmin();
    }

    protected static long getUserId(){
        return AuthorizationUtil.getUserId();
    }

    protected void checkAuthorization(String authType){
        authorizationUtil.checkAuthorization(authType);
    }


    protected LambdaQueryWrapper<Entity> getWrapper(){
        return Wrappers.lambdaQuery();
    }

    /**
     * 指定list查询字段
     *
     * @param wrapper wrapper
     * @author laoyu
     * @date 2022/7/6
     */
    public abstract void listSelect(LambdaQueryWrapper<Entity> wrapper);

    /**
     * 指定page查询字段
     *
     * @param wrapper wrapper
     * @author laoyu
     * @date 2022/7/6
     */
    public abstract void pageSelect(LambdaQueryWrapper<Entity> wrapper);


    /**
     * 列表
     *
     * @return java.util.List<VO>
     * @author laoyu
     * @date 2022/7/6
     */
    @Override
    public abstract List<VO> voList();

    /**
     * 分页
     *
     * @param qo qo
     * @return com.baomidou.mybatisplus.core.metadata.IPage<Entity>
     * @author laoyu
     * @date 2022/7/6
     */
    @Override
    public abstract IPage<VO> page(QO qo);

    /**
     * 详情
     *
     * @param id id
     * @return VO
     * @author laoyu
     * @date 2022/7/6
     */
    @Override
    public abstract VO info(Serializable id);

    /**
     * 添加
     *
     * @param dto dto
     * @return java.lang.Object
     * @author laoyu
     * @date 2022/7/6
     */
    @Override
    public abstract boolean add(DTO dto);

    /**
     * 编辑
     *
     * @param dto dto
     * @return java.lang.Object
     * @author laoyu
     * @date 2022/7/6
     */
    @Override
    public abstract boolean edit(DTO dto);

    /**
     * 删除
     *
     * @param id id
     * @return java.lang.Object
     * @author laoyu
     * @date 2022/7/6
     */
    @Override
    public abstract boolean delete(Serializable id);
}
