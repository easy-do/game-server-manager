package plus.easydo.uc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import plus.easydo.uc.qo.SysNoticeQo;
import plus.easydo.web.base.BaseServiceImpl;
import plus.easydo.uc.dto.SysNoticeDto;
import plus.easydo.uc.entity.SysNotice;
import plus.easydo.uc.vo.SysNoticeVo;
import plus.easydo.uc.mapstruct.SysNoticeMapstruct;
import plus.easydo.uc.mapper.SysNoticeMapper;
import plus.easydo.uc.service.SysNoticeService;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.List;


/**
 * 通知公告Service层
 * 
 * @author yuzhanfeng
 * @date 2022-10-03 17:39:25
 */
@Service
public class SysNoticeServiceImpl extends BaseServiceImpl<SysNotice, SysNoticeQo, SysNoticeVo, SysNoticeDto, SysNoticeMapper> implements SysNoticeService {


    @Override
    public void listSelect(LambdaQueryWrapper<SysNotice> wrapper) {
        
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<SysNotice> wrapper) {

    }


    /**
     * 获取所有通知公告列表
     *
     * @return 通知公告
     */
    @Override
    public List<SysNoticeVo> voList() {
        LambdaQueryWrapper<SysNotice> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(SysNotice::getCreateTime);
        listSelect(wrapper);
        return SysNoticeMapstruct.INSTANCE.entityToVo(list(wrapper));
    }


     /**
     * 分页条件查询通知公告列表
     * 
     * @param mpBaseQo 查询条件封装
     * @return 通知公告
     */
    @Override
    public IPage<SysNoticeVo> page(SysNoticeQo mpBaseQo) {
        mpBaseQo.initInstance(SysNotice.class);
        return page(mpBaseQo.getPage(), mpBaseQo.getWrapper()).convert(SysNoticeMapstruct.INSTANCE::entityToVo);
    }


    /**
     * 查询通知公告
     * 
     * @param id id
     * @return 通知公告
     */
    @Override
    public SysNoticeVo info(Serializable id) {
        return SysNoticeMapstruct.INSTANCE.entityToVo(getById(id));
    }




    /**
     * 新增通知公告
     * 
     * @param sysNoticeDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean add(SysNoticeDto sysNoticeDto) {
        SysNotice entity = SysNoticeMapstruct.INSTANCE.dtoToEntity(sysNoticeDto);
        entity.setCreateBy(getUserId());
        return save(entity);
    }

    /**
     * 修改通知公告
     * 
     * @param sysNoticeDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean edit(SysNoticeDto sysNoticeDto) {
        SysNotice entity = SysNoticeMapstruct.INSTANCE.dtoToEntity(sysNoticeDto);
        return updateById(entity);
    }

    /**
     * 批量删除通知公告
     * 
     * @param id 需要删除的通知公告ID
     * @return 结果
     */
    @Override
    public boolean delete(Serializable id) {
        return removeById(id);
    }

}
