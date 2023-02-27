package game.server.manager.uc.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.common.exception.BizException;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.uc.dto.OauthClientDetailsDto;
import game.server.manager.uc.entity.OauthClientDetails;
import game.server.manager.uc.qo.OauthClientDetailsQo;
import game.server.manager.uc.vo.OauthClientDetailsVo;
import game.server.manager.uc.mapstruct.OauthClientDetailsMapstruct;
import game.server.manager.uc.mapper.OauthClientDetailsMapper;
import game.server.manager.uc.service.OauthClientDetailsService;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.List;


/**
 * 授权客户端信息Service层
 * 
 * @author yuzhanfeng
 * @date 2023-02-27 16:01:25
 */
@Service
public class OauthClientDetailsServiceImpl extends BaseServiceImpl<OauthClientDetails,OauthClientDetailsQo, OauthClientDetailsVo, OauthClientDetailsDto, OauthClientDetailsMapper> implements OauthClientDetailsService {


    @Override
    public void listSelect(LambdaQueryWrapper<OauthClientDetails> wrapper) {
        
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<OauthClientDetails> wrapper) {

    }


    /**
     * 获取所有授权客户端信息列表
     *
     * @return 授权客户端信息
     */
    @Override
    public List<OauthClientDetailsVo> voList() {
        LambdaQueryWrapper<OauthClientDetails> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(OauthClientDetails::getCreateTime);
        listSelect(wrapper);
        return OauthClientDetailsMapstruct.INSTANCE.entityToVo(list(wrapper));
    }


     /**
     * 分页条件查询授权客户端信息列表
     * 
     * @param mpBaseQo 查询条件封装
     * @return 授权客户端信息
     */
    @Override
    public IPage<OauthClientDetailsVo> page(OauthClientDetailsQo mpBaseQo) {
        mpBaseQo.initInstance(OauthClientDetails.class);
        return page(mpBaseQo.getPage(), mpBaseQo.getWrapper()).convert(OauthClientDetailsMapstruct.INSTANCE::entityToVo);
    }


    /**
     * 查询授权客户端信息
     * 
     * @param id id
     * @return 授权客户端信息
     */
    @Override
    public OauthClientDetailsVo info(Serializable id) {
        return OauthClientDetailsMapstruct.INSTANCE.entityToVo(getById(id));
    }




    /**
     * 新增授权客户端信息
     * 
     * @param oauthClientDetailsDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean add(OauthClientDetailsDto oauthClientDetailsDto) {
        OauthClientDetails entity = OauthClientDetailsMapstruct.INSTANCE.dtoToEntity(oauthClientDetailsDto);
        entity.setClientSecret(UUID.fastUUID().toString(true));
        entity.setCreateBy(getUserId());
        return save(entity);
    }

    /**
     * 修改授权客户端信息
     * 
     * @param oauthClientDetailsDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean edit(OauthClientDetailsDto oauthClientDetailsDto) {
        if(CharSequenceUtil.isEmpty(oauthClientDetailsDto.getClientSecret())){
            throw new BizException("500","授权码不能为空");
        }
        OauthClientDetails entity = OauthClientDetailsMapstruct.INSTANCE.dtoToEntity(oauthClientDetailsDto);
        return updateById(entity);
    }

    /**
     * 批量删除授权客户端信息
     * 
     * @param id 需要删除的授权客户端信息ID
     * @return 结果
     */
    @Override
    public boolean delete(Serializable id) {
        return removeById(id);
    }

}
