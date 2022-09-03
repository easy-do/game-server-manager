package game.server.manager.server.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.common.utils.AppScriptUtils;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.server.dto.AppEnvInfoDto;
import game.server.manager.server.dto.AppScriptDto;
import game.server.manager.server.entity.AppScript;
import game.server.manager.common.enums.ScopeEnum;
import game.server.manager.server.mapstruct.AppEnvInfoMapstruct;
import game.server.manager.server.mapstruct.AppScriptMapstruct;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.server.service.AppEnvInfoService;
import game.server.manager.server.service.AppScriptService;
import game.server.manager.server.mapper.AppScriptMapper;
import game.server.manager.common.vo.AppEnvInfoVo;
import game.server.manager.common.vo.AppScriptVo;
import game.server.manager.common.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @description 针对表【app_script】的数据库操作Service实现
 * @createDate 2022-05-26 18:30:13
 */
@Service
public class AppScriptServiceImpl extends BaseServiceImpl<AppScript, AppScriptVo, AppScriptDto, AppScriptMapper> implements AppScriptService {


    @Autowired
    private AppEnvInfoService appEnvInfoService;

    @Override
    public long countByUserId(Long userId) {
        LambdaQueryWrapper<AppScript> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppScript::getCreateBy, userId);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public boolean exist(Long appScriptId) {
        return Objects.nonNull(baseMapper.selectById(appScriptId));
    }

    @Override
    public void addHeat(Long id) {
        baseMapper.addHeat(id);
    }

    @Override
    public boolean exists(Long appScriptId) {
        LambdaQueryWrapper<AppScript> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppScript::getId, appScriptId);
        return baseMapper.exists(wrapper);
    }

    @Override
    public List<AppScriptVo> listByAppid(Long appId) {
        LambdaQueryWrapper<AppScript> queryWrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            queryWrapper.eq(AppScript::getAdaptationAppId, appId);
            //或者是未绑定应用并且公开的
            queryWrapper.or(wrapper -> wrapper.eq(AppScript::getAdaptationAppId, "no").eq(AppScript::getScriptScope, ScopeEnum.PUBLIC));
        }
        queryWrapper.orderByDesc(AppScript::getCreateTime);
        queryWrapper.select(AppScript::getId, AppScript::getScriptName, AppScript::getAuthor, AppScript::getScriptType, AppScript::getVersion);
        return AppScriptMapstruct.INSTANCE.entityListToVoList(baseMapper.selectList(queryWrapper));
    }

    @Override
    public void listSelect(LambdaQueryWrapper<AppScript> wrapper) {
        wrapper.select(AppScript::getId, AppScript::getScriptName, AppScript::getScriptType, AppScript::getAuthor, AppScript::getVersion, AppScript::getDescription);
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<AppScript> wrapper) {
        wrapper.select(AppScript::getId, AppScript::getScriptName, AppScript::getScriptType, AppScript::getScriptScope, AppScript::getVersion, AppScript::getAuthor, AppScript::getDescription);
    }

    @Override
    public List<AppScriptVo> voList() {
        LambdaQueryWrapper<AppScript> queryWrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            //本人创建的私有脚本
            queryWrapper.and(wrapper -> wrapper.eq(AppScript::getCreateBy, getUserId()).eq(AppScript::getScriptScope, ScopeEnum.PRIVATE));
            //或者是未绑定应用并且公开的
            queryWrapper.or(wrapper -> wrapper.eq(AppScript::getAdaptationAppId, "no").eq(AppScript::getScriptScope, ScopeEnum.PUBLIC));
        }
        queryWrapper.orderByDesc(AppScript::getCreateTime);
        listSelect(queryWrapper);
        return AppScriptMapstruct.INSTANCE.entityListToVoList(baseMapper.selectList(queryWrapper));
    }

    @Override
    public IPage<AppScriptVo> page(MpBaseQo mpBaseQo) {
        LambdaQueryWrapper<AppScript> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(AppScript::getCreateBy, getUserId());
        }
        wrapper.orderByDesc(AppScript::getCreateTime);
        pageSelect(wrapper);
        return baseMapper.selectPage(mpBaseQo.startPage(), wrapper).convert(AppScriptMapstruct.INSTANCE::entityToVo);
    }

    @Override
    public AppScriptVo info(Serializable id) {
        LambdaQueryWrapper<AppScript> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(AppScript::getCreateBy, getUserId());
        }
        wrapper.eq(AppScript::getId, id);
        AppScriptVo appScriptVo = AppScriptMapstruct.INSTANCE.entityToVo(baseMapper.selectOne(wrapper));
        List<AppEnvInfoVo> envList = AppEnvInfoMapstruct.INSTANCE.entityListToVoList(appEnvInfoService.getListByScriptId(appScriptVo.getId()));
        appScriptVo.setScriptEnv(envList);
        return appScriptVo;
    }

    @Override
    public boolean add(AppScriptDto appScriptDto) {
        //校验授权信息
        checkAuthorization("appScriptAdd");
        AppScript entity = AppScriptMapstruct.INSTANCE.dtoToEntity(appScriptDto);
        entity.setCreateBy(getUserId());
        entity.setAuthor(getUser().getNickName());
        boolean result = save(entity);
        if (result) {
            List<AppEnvInfoDto> envList = appScriptDto.getScriptEnv();
            if (!envList.isEmpty()) {
                appEnvInfoService.saveOrUpdateScriptEnvList(entity.getId(),envList);
            }
        }
        //生成脚本取参内容
        String getEnvScript = AppScriptUtils.generateShellEnvScript(appEnvInfoService.getVoListByScriptId(entity.getId()));
        AppScript appScript = AppScript.builder().id(entity.getId()).scriptFile(AppScriptUtils.SCRIPT_START_LINE+getEnvScript).build();
        updateById(appScript);
        return result;
    }

    @Override
    public boolean edit(AppScriptDto appScriptDto) {
        //校验授权信息
        checkAuthorization("appScriptEdit");
        AppScript entity = AppScriptMapstruct.INSTANCE.dtoToEntity(appScriptDto);
        entity.setUpdateBy(getUserId());
        LambdaQueryWrapper<AppScript> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(AppScript::getCreateBy, getUserId());
        }
        wrapper.eq(AppScript::getId, entity.getId());
        boolean result = update(entity, wrapper);
        if (result) {
            List<AppEnvInfoDto> envList = appScriptDto.getScriptEnv();
            appEnvInfoService.saveOrUpdateScriptEnvList(appScriptDto.getId(), envList);
            if(CharSequenceUtil.isEmpty(appScriptDto.getScriptFile())){
                //更新脚本取参内容
                AppScript dbAppScript = getById(appScriptDto.getId());
                String scriptFile = dbAppScript.getScriptFile();
                int startIndex = scriptFile.indexOf(AppScriptUtils.ENV_GET_START_LINE);
                int endIndex = scriptFile.indexOf(AppScriptUtils.ENV_GET_END_LINE);
                String getEnvScript = AppScriptUtils.generateShellEnvScript(appEnvInfoService.getVoListByScriptId(entity.getId()));
                if(startIndex <=0 && endIndex <=0 ){
                    scriptFile = getEnvScript + scriptFile;
                }else {
                    getEnvScript = getEnvScript.replace(AppScriptUtils.ENV_GET_END_LINE,"");
                    String envGetScript = CharSequenceUtil.sub(scriptFile, startIndex, endIndex);
                    scriptFile = scriptFile.replace(envGetScript,getEnvScript);
                }
                AppScript appScript = AppScript.builder().id(entity.getId()).scriptFile(scriptFile).build();
                updateById(appScript);
            }
        }
        return result;
    }

    @Override
    public boolean delete(Serializable id) {
        //校验授权信息
        checkAuthorization("appScriptDel");
        LambdaQueryWrapper<AppScript> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(AppScript::getCreateBy, getUserId());
        }
        wrapper.eq(AppScript::getId, id);
        boolean result = remove(wrapper);
        if (result) {
            appEnvInfoService.removeByAppId((Long) id);
        }
        return result;
    }
}




