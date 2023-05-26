package plus.easydo.server.service.oss.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import plus.easydo.common.exception.BizException;
import plus.easydo.server.config.oss.minio.MinioOssStoreServer;
import plus.easydo.server.dto.OssManagementDto;
import plus.easydo.server.entity.OssManagement;
import plus.easydo.server.mapper.OssManagementMapper;
import plus.easydo.server.mapstruct.OssManagementMapstruct;
import plus.easydo.server.qo.oss.OssManagementQo;
import plus.easydo.server.service.oss.OssManagementService;
import plus.easydo.server.util.oss.OssUtil;
import plus.easydo.server.vo.oss.OssManagementVo;
import plus.easydo.web.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * 存储管理Service层
 * 
 * @author yuzhanfeng
 * @date 2022-09-26 16:35:09
 */
@Service
public class OssManagementServiceImpl extends BaseServiceImpl<OssManagement, OssManagementQo, OssManagementVo, OssManagementDto, OssManagementMapper> implements OssManagementService {

   @Autowired
   private MinioOssStoreServer minioOssStoreServer;

    @Override
    public void listSelect(LambdaQueryWrapper<OssManagement> wrapper) {
        
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<OssManagement> wrapper) {

    }


    /**
     * 获取所有存储管理列表
     *
     * @return 存储管理
     */
    @Override
    public List<OssManagementVo> voList() {
        LambdaQueryWrapper<OssManagement> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(OssManagement::getCreateTime);
        listSelect(wrapper);
        return OssManagementMapstruct.INSTANCE.entityToVo(list(wrapper));
    }


     /**
     * 分页条件查询存储管理列表
     * 
     * @param mpBaseQo 查询条件封装
     * @return 存储管理
     */
    @Override
    public IPage<OssManagementVo> page(OssManagementQo mpBaseQo) {
        mpBaseQo.initInstance(OssManagement.class);
        return page(mpBaseQo.getPage(), mpBaseQo.getWrapper()).convert(OssManagementMapstruct.INSTANCE::entityToVo);
    }


    /**
     * 查询存储管理
     * 
     * @param id id
     * @return 存储管理
     */
    @Override
    public OssManagementVo info(Serializable id) {
        return OssManagementMapstruct.INSTANCE.entityToVo(getById(id));
    }




    /**
     * 新增存储管理
     * 
     * @param ossManagementDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean add(OssManagementDto ossManagementDto) {
        OssManagement entity = OssManagementMapstruct.INSTANCE.dtoToEntity(ossManagementDto);
        entity.setCreateBy(getUserId());
        return save(entity);
    }

    /**
     * 修改存储管理
     * 
     * @param ossManagementDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean edit(OssManagementDto ossManagementDto) {
        OssManagement entity = OssManagementMapstruct.INSTANCE.dtoToEntity(ossManagementDto);
        return updateById(entity);
    }

    /**
     * 批量删除存储管理
     * 
     * @param id 需要删除的存储管理ID
     * @return 结果
     */
    @Override
    public boolean delete(Serializable id) {
        OssManagement oss = getById(id);
        if(Objects.isNull(oss)){
            throw new BizException("文件不存在");
        }
        minioOssStoreServer.remove(oss.getGroupName(), OssUtil.endWithSlash(oss.getFilePath()) + oss.getFileName());
        return removeById(id);
    }

    @Override
    public void removeByWrapper(String groupName, String filePath, String fileName) {
        LambdaQueryWrapper<OssManagement> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OssManagement::getGroupName,groupName);
        wrapper.eq(OssManagement::getFilePath,filePath);
        wrapper.eq(OssManagement::getFileName,fileName);
        remove(wrapper);
    }
}
