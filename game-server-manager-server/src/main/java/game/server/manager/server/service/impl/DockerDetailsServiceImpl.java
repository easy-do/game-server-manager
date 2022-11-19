package game.server.manager.server.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Version;
import game.server.manager.common.result.R;
import game.server.manager.docker.client.api.DockerClientApi;
import game.server.manager.docker.client.api.DockerClientApiEndpoint;
import game.server.manager.server.dto.DockerDetailsDto;
import game.server.manager.server.entity.DockerDetails;
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
    private DockerClientApiEndpoint dockerClientApiEndpoint;

    private DockerClientApi dockerClientApi(DockerDetailsVo dockerDetailsVo){
        return dockerClientApiEndpoint.dockerClientApi(dockerDetailsVo.getDockerHost(), dockerDetailsVo.getDockerSecret());
    }

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
        return page(mpBaseQo.getPage(), mpBaseQo.getWrapper()).convert(DockerDetailsMapstruct.INSTANCE::entityToVo);
    }

    /**
     * 查询docker配置信息
     * 
     * @param id id
     * @return docker配置信息
     */
    @Override
    public DockerDetailsVo info(Serializable id) {
        DockerDetailsVo vo = DockerDetailsMapstruct.INSTANCE.entityToVo(getById(id));
        R<Info> info = dockerClientApi(vo).info();
        if(Objects.nonNull(info)){
            vo.setDetailsJson(JSON.toJSONString(info.getData()));
        }
        R<Version> version = dockerClientApi(vo).version();
        if(Objects.nonNull(version)){
            vo.setVersionJson(JSON.toJSONString(version.getData()));
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
        return removeById(id);
    }

}
