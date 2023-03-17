package game.server.manager.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.common.vo.UserInfoVo;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.server.dto.ApplicationDto;
import game.server.manager.server.entity.Application;
import game.server.manager.server.qo.ApplicationQo;
import game.server.manager.server.vo.ApplicationVo;
import game.server.manager.server.mapstruct.ApplicationMapstruct;
import game.server.manager.server.mapper.ApplicationMapper;
import game.server.manager.server.service.ApplicationService;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.List;


/**
 * 应用信息Service层
 * 
 * @author yuzhanfeng
 * @date 2023-03-18 00:48:08
 */
@Service
public class ApplicationServiceImpl extends BaseServiceImpl<Application,ApplicationQo, ApplicationVo, ApplicationDto, ApplicationMapper> implements ApplicationService {


    @Override
    public void listSelect(LambdaQueryWrapper<Application> wrapper) {
        
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<Application> wrapper) {

    }


    /**
     * 获取所有应用信息列表
     *
     * @return 应用信息
     */
    @Override
    public List<ApplicationVo> voList() {
        LambdaQueryWrapper<Application> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(Application::getCreateTime);
        listSelect(wrapper);
        return ApplicationMapstruct.INSTANCE.entityToVo(list(wrapper));
    }


     /**
     * 分页条件查询应用信息列表
     * 
     * @param mpBaseQo 查询条件封装
     * @return 应用信息
     */
    @Override
    public IPage<ApplicationVo> page(ApplicationQo mpBaseQo) {
        mpBaseQo.initInstance(Application.class);
        return page(mpBaseQo.getPage(), mpBaseQo.getWrapper()).convert(ApplicationMapstruct.INSTANCE::entityToVo);
    }


    /**
     * 查询应用信息
     * 
     * @param id id
     * @return 应用信息
     */
    @Override
    public ApplicationVo info(Serializable id) {
        return ApplicationMapstruct.INSTANCE.entityToVo(getById(id));
    }




    /**
     * 新增应用信息
     * 
     * @param applicationDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean add(ApplicationDto applicationDto) {
        Application entity = ApplicationMapstruct.INSTANCE.dtoToEntity(applicationDto);
        UserInfoVo user = getUser();
        entity.setCreateBy(user.getId());
        entity.setAuthor(user.getNickName());
        return save(entity);
    }

    /**
     * 修改应用信息
     * 
     * @param applicationDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean edit(ApplicationDto applicationDto) {
        Application entity = ApplicationMapstruct.INSTANCE.dtoToEntity(applicationDto);
        entity.setUpdateBy(getUserId());
        return updateById(entity);
    }

    /**
     * 批量删除应用信息
     * 
     * @param id 需要删除的应用信息ID
     * @return 结果
     */
    @Override
    public boolean delete(Serializable id) {
        return removeById(id);
    }

}
