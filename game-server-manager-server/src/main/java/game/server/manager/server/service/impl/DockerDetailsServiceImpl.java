package game.server.manager.server.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.common.exception.ExceptionFactory;
import game.server.manager.common.result.R;

import game.server.manager.server.dto.DockerDetailsDto;
import game.server.manager.server.entity.ClientInfo;
import game.server.manager.server.entity.DockerDetails;
import game.server.manager.server.service.DockerBasicService;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.server.qo.DockerDetailsQo;
import game.server.manager.server.vo.DockerDetailsVo;
import game.server.manager.server.mapstruct.DockerDetailsMapstruct;
import game.server.manager.server.mapper.DockerDetailsMapper;
import game.server.manager.server.service.DockerDetailsService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * docker配置信息Service层
 * 
 * @author yuzhanfeng
 * @date 2022-11-13 12:10:30
 */
@Service
public class DockerDetailsServiceImpl extends BaseServiceImpl<DockerDetails, DockerDetailsQo, DockerDetailsVo, DockerDetailsDto, DockerDetailsMapper> implements DockerDetailsService {

    @Resource
    private DockerBasicService dockerBasicService;

    @Override
    public void listSelect(LambdaQueryWrapper<DockerDetails> wrapper) {
        
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<DockerDetails> wrapper) {

    }


    /**
     * 获取所有docker配置信息列表
     *
     * @return docker配置信息
     */
    @Override
    public List<DockerDetailsVo> voList() {
        LambdaQueryWrapper<DockerDetails> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(DockerDetails::getCreateTime);
        listSelect(wrapper);
        if (!isAdmin()) {
            wrapper.eq(DockerDetails::getCreateBy, getUserId());
        }
        wrapper.orderByDesc(DockerDetails::getCreateTime);
        return DockerDetailsMapstruct.INSTANCE.entityToVo(list(wrapper));
    }



    /**
     * 分页条件查询docker配置信息列表
     *
     * @param mpBaseQo 查询条件封装
     * @return docker配置信息
     */
    @Override
    public IPage<DockerDetailsVo> page(DockerDetailsQo mpBaseQo) {
        mpBaseQo.initInstance(DockerDetails.class);
        LambdaQueryWrapper<DockerDetails> wrapper = mpBaseQo.getWrapper().lambda();
        if(!isAdmin()){
            wrapper.eq(DockerDetails::getCreateBy,getUserId());
        }
        return page(mpBaseQo.getPage(), wrapper).convert(DockerDetailsMapstruct.INSTANCE::entityToVo);
    }

    /**
     * 查询docker配置信息
     * 
     * @param id id
     * @return docker配置信息
     */
    @Override
    public DockerDetailsVo info(Serializable id) {
        LambdaQueryWrapper<DockerDetails> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(DockerDetails::getCreateBy,getUserId());
        }
        wrapper.eq(DockerDetails::getId,id);
        DockerDetailsVo vo = DockerDetailsMapstruct.INSTANCE.entityToVo(getOne(wrapper));
        if(Objects.nonNull(vo)){
            R<String> info = dockerBasicService.info(id.toString());
            if(Objects.nonNull(info)){
                vo.setDetailsJson(info.getData());
            }
            R<String> version = dockerBasicService.version(id.toString());
            if(Objects.nonNull(version)){
                vo.setVersionJson(version.getData());
            }
        }
        return vo;
    }




    /**
     * 新增docker配置信息
     * 
     * @param dockerDetailsDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean add(DockerDetailsDto dockerDetailsDto) {
        String clientId = dockerDetailsDto.getClientId();
        if(Objects.nonNull(clientId)){
            LambdaQueryWrapper<DockerDetails> wrapper = getWrapper();
            wrapper.eq(DockerDetails::getClientId,clientId);
            if(baseMapper.exists(wrapper)){
                throw ExceptionFactory.bizException("客户端["+clientId+"]已绑定docker");
            }
        }
        DockerDetails entity = DockerDetailsMapstruct.INSTANCE.dtoToEntity(dockerDetailsDto);
        //设置密钥
        entity.setDockerSecret(UUID.fastUUID().toString(true));
        entity.setCreateBy(getUserId());
        return save(entity);
    }

    /**
     * 修改docker配置信息
     * 
     * @param dockerDetailsDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean edit(DockerDetailsDto dockerDetailsDto) {
        DockerDetails dockerDetails = getById(dockerDetailsDto);
        if(Objects.isNull(dockerDetails)){
            throw ExceptionFactory.bizException("docker不存在");
        }
        if(!CharSequenceUtil.equals(dockerDetails.getClientId(),dockerDetailsDto.getClientId())){
            throw ExceptionFactory.bizException("客户端不可更改");
        }
        LambdaQueryWrapper<DockerDetails> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(DockerDetails::getCreateBy,getUserId());
        }
        wrapper.eq(DockerDetails::getId,dockerDetailsDto.getId());
        DockerDetails entity = DockerDetailsMapstruct.INSTANCE.dtoToEntity(dockerDetailsDto);
        return updateById(entity);
    }

    /**
     * 批量删除docker配置信息
     * 
     * @param id 需要删除的docker配置信息ID
     * @return 结果
     */
    @Override
    public boolean delete(Serializable id) {
        LambdaQueryWrapper<DockerDetails> wrapper = Wrappers.lambdaQuery();
        if(!isAdmin()){
            wrapper.eq(DockerDetails::getCreateBy,getUserId());
        }
        wrapper.eq(DockerDetails::getId,id);
        return remove(wrapper);
    }

    @Override
    public DockerDetailsVo getByClientId(String clientId) {
        LambdaQueryWrapper<DockerDetails> wrapper = getWrapper();
        wrapper.eq(DockerDetails::getClientId,clientId);
        return DockerDetailsMapstruct.INSTANCE.entityToVo(getOne(wrapper));
    }
}
