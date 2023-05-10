package game.server.manager.server.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.common.enums.ApplicationInstallLogStatusenum;
import game.server.manager.common.vo.LogResultVo;
import game.server.manager.redis.config.RedisUtils;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.server.dto.ApplicationInstallLogDto;
import game.server.manager.server.entity.ApplicationInstallLog;
import game.server.manager.server.qo.server.ApplicationInstallLogQo;
import game.server.manager.server.vo.server.ApplicationInstallLogVo;
import game.server.manager.server.mapstruct.ApplicationInstallLogMapstruct;
import game.server.manager.server.mapper.ApplicationInstallLogMapper;
import game.server.manager.server.service.ApplicationInstallLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static game.server.manager.common.constant.SystemConstant.APPLICATION_INSTALL_LOG;


/**
 * 应用安装日志Service层
 * 
 * @author yuzhanfeng
 * @date 2023-04-08 18:35:18
 */
@Service
public class ApplicationInstallLogServiceImpl extends BaseServiceImpl<ApplicationInstallLog,ApplicationInstallLogQo, ApplicationInstallLogVo, ApplicationInstallLogDto, ApplicationInstallLogMapper> implements ApplicationInstallLogService {


    @Autowired
    private RedisUtils<String> redisUtils;

    @Override
    public void listSelect(LambdaQueryWrapper<ApplicationInstallLog> wrapper) {
        
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<ApplicationInstallLog> wrapper) {

    }


    /**
     * 获取所有应用安装日志列表
     *
     * @return 应用安装日志
     */
    @Override
    public List<ApplicationInstallLogVo> voList() {
        LambdaQueryWrapper<ApplicationInstallLog> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(ApplicationInstallLog::getCreateTime);
        listSelect(wrapper);
        return ApplicationInstallLogMapstruct.INSTANCE.entityToVo(list(wrapper));
    }


     /**
     * 分页条件查询应用安装日志列表
     * 
     * @param mpBaseQo 查询条件封装
     * @return 应用安装日志
     */
    @Override
    public IPage<ApplicationInstallLogVo> page(ApplicationInstallLogQo mpBaseQo) {
        mpBaseQo.initInstance(ApplicationInstallLog.class);
        LambdaQueryWrapper<ApplicationInstallLog> wrapper = mpBaseQo.getWrapper().lambda();
        if (!isAdmin()) {
            wrapper.eq(ApplicationInstallLog::getCreateBy, getUserId());
        }
        return page(mpBaseQo.getPage(), wrapper).convert(ApplicationInstallLogMapstruct.INSTANCE::entityToVo);
    }


    /**
     * 查询应用安装日志
     * 
     * @param id id
     * @return 应用安装日志
     */
    @Override
    public ApplicationInstallLogVo info(Serializable id) {
        return ApplicationInstallLogMapstruct.INSTANCE.entityToVo(getById(id));
    }




    /**
     * 新增应用安装日志
     * 
     * @param applicationInstallLogDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean add(ApplicationInstallLogDto applicationInstallLogDto) {
        ApplicationInstallLog entity = ApplicationInstallLogMapstruct.INSTANCE.dtoToEntity(applicationInstallLogDto);
        entity.setCreateBy(getUserId());
        return save(entity);
    }

    /**
     * 修改应用安装日志
     * 
     * @param applicationInstallLogDto 数据传输对象
     * @return 结果
     */
    @Override
    public boolean edit(ApplicationInstallLogDto applicationInstallLogDto) {
        ApplicationInstallLog entity = ApplicationInstallLogMapstruct.INSTANCE.dtoToEntity(applicationInstallLogDto);
        return updateById(entity);
    }

    /**
     * 批量删除应用安装日志
     * 
     * @param id 需要删除的应用安装日志ID
     * @return 结果
     */
    @Override
    public boolean delete(Serializable id) {
        return removeById(id);
    }

    @Override
    public LogResultVo getLog(String logId) {
        ApplicationInstallLog applicationInstallLog = getById(logId);
        if(Objects.isNull(applicationInstallLog)){
            return LogResultVo.builder().isFinish(true).logs(List.of("日志不存在")).build();
        }
        Integer status = applicationInstallLog.getStatus();
        //如果进行中则从缓存读取
        Collection<String> logs;
        if (!ApplicationInstallLogStatusenum.isFinish(status)) {
            logs = redisUtils.zRange(APPLICATION_INSTALL_LOG + logId, 0, -1);
            return LogResultVo.builder().isFinish(false).logs(logs).build();
        } else {
            //如果已经执行完成直接返回数据库的日志
            String log = applicationInstallLog.getLogData();
            if(CharSequenceUtil.isNotEmpty(log)){
                logs = List.of(log);
            }else {
                logs = Collections.emptyList();
            }
            return LogResultVo.builder().isFinish(true).logs(logs).build();
        }
    }

    @Override
    public List<ApplicationInstallLog> getNoFinishLog() {
        LambdaQueryWrapper<ApplicationInstallLog> wrapper = Wrappers.lambdaQuery();
        wrapper.notIn(ApplicationInstallLog::getStatus,ApplicationInstallLogStatusenum.SUCCESS.getStatus(),ApplicationInstallLogStatusenum.FAILED.getStatus());
        return list(wrapper);
    }
}
