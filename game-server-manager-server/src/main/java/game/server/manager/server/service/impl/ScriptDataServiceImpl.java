package game.server.manager.server.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import game.server.manager.common.utils.AppScriptUtils;
import game.server.manager.common.vo.ScriptDataVo;
import game.server.manager.server.dto.ScriptDataDto;
import game.server.manager.server.entity.ScriptData;
import game.server.manager.web.base.BaseServiceImpl;
import game.server.manager.server.dto.AppEnvInfoDto;
import game.server.manager.common.enums.ScopeEnum;
import game.server.manager.server.mapstruct.AppEnvInfoMapstruct;
import game.server.manager.server.mapstruct.ScriptDataMapstruct;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.server.service.AppEnvInfoService;
import game.server.manager.server.service.ScriptDataService;
import game.server.manager.server.mapper.ScriptDataMapper;
import game.server.manager.common.vo.AppEnvInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @description 针对表【script_data】的数据库操作Service实现
 * @createDate 2022-05-26 18:30:13
 */
@Service
public class ScriptDataServiceImpl extends BaseServiceImpl<ScriptData, MpBaseQo<ScriptData>, ScriptDataVo, ScriptDataDto, ScriptDataMapper> implements ScriptDataService {


    @Autowired
    private AppEnvInfoService appEnvInfoService;

    @Override
    public long countByUserId(Long userId) {
        LambdaQueryWrapper<ScriptData> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ScriptData::getCreateBy, userId);
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
    public boolean exists(Long scriptId) {
        LambdaQueryWrapper<ScriptData> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ScriptData::getId, scriptId);
        return baseMapper.exists(wrapper);
    }

    @Override
    public void listSelect(LambdaQueryWrapper<ScriptData> wrapper) {
        wrapper.select(ScriptData::getId, ScriptData::getScriptName, ScriptData::getScriptType, ScriptData::getAuthor, ScriptData::getVersion, ScriptData::getDescription);
    }

    @Override
    public void pageSelect(LambdaQueryWrapper<ScriptData> wrapper) {
        wrapper.select(ScriptData::getId, ScriptData::getScriptName, ScriptData::getScriptType, ScriptData::getScriptScope, ScriptData::getVersion, ScriptData::getAuthor, ScriptData::getDescription);
    }

    @Override
    public List<ScriptDataVo> voList() {
        LambdaQueryWrapper<ScriptData> queryWrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            //本人创建的私有脚本
            queryWrapper.and(wrapper -> wrapper.eq(ScriptData::getCreateBy, getUserId()).eq(ScriptData::getScriptScope, ScopeEnum.PRIVATE));
            //或者是公开的
            queryWrapper.or(wrapper -> wrapper.eq(ScriptData::getScriptScope, ScopeEnum.PUBLIC));
        }
        queryWrapper.orderByDesc(ScriptData::getCreateTime);
        listSelect(queryWrapper);
        return ScriptDataMapstruct.INSTANCE.entityToVo(baseMapper.selectList(queryWrapper));
    }

    @Override
    public IPage<ScriptDataVo> page(MpBaseQo<ScriptData> mpBaseQo) {
        mpBaseQo.initInstance(ScriptData.class);
        LambdaQueryWrapper<ScriptData> wrapper = mpBaseQo.getWrapper().lambda();
        if (!isAdmin()) {
            wrapper.eq(ScriptData::getCreateBy, getUserId());
        }
        return baseMapper.selectPage(mpBaseQo.startPage(), wrapper).convert(ScriptDataMapstruct.INSTANCE::entityToVo);
    }

    @Override
    public ScriptDataVo info(Serializable id) {
        LambdaQueryWrapper<ScriptData> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(ScriptData::getCreateBy, getUserId());
        }
        wrapper.eq(ScriptData::getId, id);
        ScriptDataVo scriptDataVo = ScriptDataMapstruct.INSTANCE.entityToVo(baseMapper.selectOne(wrapper));
        List<AppEnvInfoVo> envList = AppEnvInfoMapstruct.INSTANCE.entityToVo(appEnvInfoService.getListByScriptId(scriptDataVo.getId()));
        scriptDataVo.setScriptEnv(envList);
        return scriptDataVo;
    }

    @Override
    public boolean add(ScriptDataDto scriptDataDto) {
        //校验授权信息
        checkAuthorization("appScriptAdd");
        ScriptData entity = ScriptDataMapstruct.INSTANCE.dtoToEntity(scriptDataDto);
        entity.setCreateBy(getUserId());
        entity.setAuthor(getUser().getNickName());
        boolean result = save(entity);
        if (result) {
            List<AppEnvInfoDto> envList = scriptDataDto.getScriptEnv();
            if (Objects.nonNull(envList) && !envList.isEmpty()) {
                appEnvInfoService.saveOrUpdateScriptEnvList(entity.getId(),envList);
            }
        }
        //生成脚本取参内容
        String getEnvScript = AppScriptUtils.generateShellEnvScript(appEnvInfoService.getVoListByScriptId(entity.getId()));
        ScriptData scriptData = ScriptData.builder().id(entity.getId()).scriptFile(AppScriptUtils.SCRIPT_START_LINE+getEnvScript).build();
        updateById(scriptData);
        return result;
    }

    @Override
    public boolean edit(ScriptDataDto scriptDataDto) {
        //校验授权信息
        checkAuthorization("appScriptEdit");
        ScriptData entity = ScriptDataMapstruct.INSTANCE.dtoToEntity(scriptDataDto);
        entity.setUpdateBy(getUserId());
        LambdaQueryWrapper<ScriptData> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(ScriptData::getCreateBy, getUserId());
        }
        wrapper.eq(ScriptData::getId, entity.getId());
        boolean result = update(entity, wrapper);
        if (result) {
            List<AppEnvInfoDto> envList = scriptDataDto.getScriptEnv();
            appEnvInfoService.saveOrUpdateScriptEnvList(scriptDataDto.getId(), envList);
            if(CharSequenceUtil.isEmpty(scriptDataDto.getScriptFile())){
                //更新脚本取参内容
                ScriptData dbScriptData = getById(scriptDataDto.getId());
                String scriptFile = dbScriptData.getScriptFile();
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
                ScriptData scriptData = ScriptData.builder().id(entity.getId()).scriptFile(scriptFile).build();
                updateById(scriptData);
            }
        }
        return result;
    }

    @Override
    public boolean delete(Serializable id) {
        //校验授权信息
        checkAuthorization("appScriptDel");
        LambdaQueryWrapper<ScriptData> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin()) {
            wrapper.eq(ScriptData::getCreateBy, getUserId());
        }
        wrapper.eq(ScriptData::getId, id);
        boolean result = remove(wrapper);
        if (result) {
            appEnvInfoService.removeByAppId((Long) id);
        }
        return result;
    }
}




