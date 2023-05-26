package plus.easydo.server.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import plus.easydo.common.exception.ExceptionFactory;
import plus.easydo.common.utils.ScriptDataUtils;
import plus.easydo.common.vo.ScriptDataVo;
import plus.easydo.common.vo.ScriptEnvDataVo;
import plus.easydo.server.dto.ScriptDataDto;
import plus.easydo.server.entity.ScriptData;
import plus.easydo.server.dto.AppEnvInfoDto;
import plus.easydo.server.mapstruct.AppEnvInfoMapstruct;
import plus.easydo.server.mapstruct.ScriptDataMapstruct;
import plus.easydo.web.base.BaseServiceImpl;
import plus.easydo.common.enums.ScopeEnum;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.server.service.ScriptEnvDataService;
import plus.easydo.server.service.ScriptDataService;
import plus.easydo.server.mapper.ScriptDataMapper;
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
    private ScriptEnvDataService scriptEnvDataService;

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
        List<ScriptEnvDataVo> envList = AppEnvInfoMapstruct.INSTANCE.entityToVo(scriptEnvDataService.getListByScriptId(scriptDataVo.getId()));
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
                scriptEnvDataService.saveOrUpdateScriptEnvList(entity.getId(),envList);
            }
        }
        //生成脚本取参内容
        String getEnvScript = ScriptDataUtils.generateShellEnvScript(scriptEnvDataService.getVoListByScriptId(entity.getId()));
        ScriptData scriptData = ScriptData.builder().id(entity.getId()).scriptFile(ScriptDataUtils.SCRIPT_START_LINE+getEnvScript).build();
        updateById(scriptData);
        return result;
    }

    @Override
    public boolean edit(ScriptDataDto scriptDataDto) {
        ScriptData dbScriptData = getById(scriptDataDto.getId());
        if(Objects.isNull(dbScriptData)){
            throw ExceptionFactory.bizException("脚本不存在");
        }
        ScriptData entity = ScriptDataMapstruct.INSTANCE.dtoToEntity(scriptDataDto);
        entity.setUpdateBy(getUserId());
        LambdaQueryWrapper<ScriptData> wrapper = Wrappers.lambdaQuery();
        if (!isAdmin() && dbScriptData.getCreateBy() != getUserId()) {
            throw ExceptionFactory.bizException("只能编辑自己的脚本");
        }
        wrapper.eq(ScriptData::getId, entity.getId());
        boolean updateScriptFile = CharSequenceUtil.isNotEmpty(scriptDataDto.getScriptFile()) && !CharSequenceUtil.equals(scriptDataDto.getScriptFile(),dbScriptData.getScriptFile());
        //如果不是更新脚本内容、只是编辑脚本基本信息则自动生成一次新的脚本上半部分
        if (!updateScriptFile) {
            List<AppEnvInfoDto> envList = scriptDataDto.getScriptEnv();
            if (Objects.nonNull(envList) && !envList.isEmpty()) {
                scriptEnvDataService.saveOrUpdateScriptEnvList(scriptDataDto.getId(), envList);
            }
            if(CharSequenceUtil.isEmpty(scriptDataDto.getScriptFile())){
                //旧的脚本内容
                String scriptFile = dbScriptData.getScriptFile();
                String scriptContext = "";
                //脚本正文开始位置
                int scriptIndex = scriptFile.indexOf(ScriptDataUtils.SCRIPT_CONTEXT);
                //兼容老的脚本生成
                if(scriptIndex <=0){
                    scriptIndex = scriptFile.indexOf(ScriptDataUtils.ENV_GET_START_LINE);
                    //截取脚本正文内容
                    scriptContext = CharSequenceUtil.subAfter(scriptFile, ScriptDataUtils.ENV_GET_START_LINE, true);
                }else {
                    //截取脚本正文内容
                    scriptContext = CharSequenceUtil.subAfter(scriptFile, ScriptDataUtils.SCRIPT_CONTEXT, true);
                }
                //生成上半部分内容
                String getEnvScript = ScriptDataUtils.generateShellEnvScript(scriptEnvDataService.getVoListByScriptId(entity.getId()));
                if(scriptIndex <=0){
                    //不存在上半部分内容则直接拼接
                    scriptFile = getEnvScript + scriptFile;
                }else {
                    //拼接新的上半部分内容拼接到正文前
                    scriptFile = StrBuilder.create(getEnvScript).append(scriptContext).toString();
                }
                entity.setScriptFile(scriptFile);
            }
        }
        return update(entity, wrapper);
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
            scriptEnvDataService.removeByAppId((Long) id);
        }
        return result;
    }
}




