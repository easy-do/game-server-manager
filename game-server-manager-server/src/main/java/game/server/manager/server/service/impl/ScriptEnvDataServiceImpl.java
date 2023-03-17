package game.server.manager.server.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.common.utils.ScriptDataUtils;
import game.server.manager.common.vo.ScriptEnvDataVo;
import game.server.manager.server.dto.AppEnvInfoDto;
import game.server.manager.server.entity.ScriptEnvData;
import game.server.manager.server.entity.ScriptData;
import game.server.manager.server.mapstruct.AppEnvInfoMapstruct;
import game.server.manager.server.service.ScriptEnvDataService;
import game.server.manager.server.mapper.ScriptEnvDataMapper;
import game.server.manager.server.service.ScriptDataService;
import game.server.manager.common.vo.ScriptEnvDataListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuzhanfeng
 * @description 针对表【script_env_data(脚本变量)】的数据库操作Service实现
 * @createDate 2022-05-24 19:51:01
 */
@Service
public class ScriptEnvDataServiceImpl extends ServiceImpl<ScriptEnvDataMapper, ScriptEnvData>
        implements ScriptEnvDataService {

    @Autowired
    private ScriptDataService scriptDataService;

    @Override
    public List<ScriptEnvDataListVo> listByScriptId(Long scriptId) {
        List<ScriptEnvDataListVo> allEvenList = new ArrayList<>();
        //检查脚本是否存在依赖脚本，如果存在则查询出依赖脚本的变量
        ScriptData scriptData = scriptDataService.getById(scriptId);
        String basicScript = scriptData.getBasicScript();
        if (CharSequenceUtil.isNotBlank(basicScript)) {
            String[] scriptIds = basicScript.split(",");
            if (scriptIds.length > 0) {
                for (int i = 0; i < scriptIds.length; i++) {
                    ScriptData script = scriptDataService.getById(scriptIds[i]);
                    List<ScriptEnvData> list = getListByScriptId(script.getId());
                    ScriptEnvDataListVo scriptEnvDataListVo = ScriptEnvDataListVo.builder()
                            .scriptName(script.getScriptName())
                            .env(AppEnvInfoMapstruct.INSTANCE.entityToVo(list))
                            .build();
                    allEvenList.add(scriptEnvDataListVo);
                }
            }
        }
        //查询主脚本的变量
        List<ScriptEnvData> list = getListByScriptId(scriptId);
        ScriptEnvDataListVo scriptEnvDataListVo = ScriptEnvDataListVo.builder()
                .scriptName(scriptData.getScriptName())
                .env(AppEnvInfoMapstruct.INSTANCE.entityToVo(list))
                .build();
        allEvenList.add(scriptEnvDataListVo);
        return allEvenList;
    }

    @Override
    public List<ScriptEnvData> getListByScriptId(Long scriptId) {
        LambdaQueryWrapper<ScriptEnvData> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ScriptEnvData::getScriptId, scriptId);
        wrapper.orderByDesc(ScriptEnvData::getCreateTime);
        wrapper.select(ScriptEnvData::getId, ScriptEnvData::getEnvName, ScriptEnvData::getEnvKey, ScriptEnvData::getShellKey, ScriptEnvData::getEnvValue, ScriptEnvData::getEnvType, ScriptEnvData::getDescription);
        return list(wrapper);
    }

    @Override
    public List<ScriptEnvDataVo> getVoListByScriptId(Long scriptId) {
        List<ScriptEnvData> list = getListByScriptId(scriptId);
        return AppEnvInfoMapstruct.INSTANCE.entityToVo(list);
    }

    @Override
    public boolean removeByAppId(Long id) {
        LambdaQueryWrapper<ScriptEnvData> envWrapper = Wrappers.lambdaQuery();
        envWrapper.eq(ScriptEnvData::getScriptId, id);
        return remove(envWrapper);
    }

    @Override
    public void saveOrUpdateScriptEnvList(Long scriptId, List<AppEnvInfoDto> envList) {
        List<ScriptEnvData> existEnvList = getListByScriptId(scriptId);
        if (!existEnvList.isEmpty()) {
            //如果存在历史变量则比较结果,旧的变量直接update，新的直接新增
            envList = envList.stream().peek(appEnvInfoDto -> {
                String key = appEnvInfoDto.getEnvKey();
                for (ScriptEnvData scriptEnvData : existEnvList) {
                    if (scriptEnvData.getEnvKey().equals(key)) {
                        appEnvInfoDto.setId(scriptEnvData.getId());
                        appEnvInfoDto.setShellKey(scriptEnvData.getShellKey());
                    }
                }
            }).toList();
            //如果只是修改了变量的key,但无法对比，则删掉多出来的已存在的变量
            List<String> deleteKeys = new ArrayList<>();
            List<String> newEnvKeys = envList.stream().map(AppEnvInfoDto::getEnvKey).toList();
            existEnvList.forEach(scriptEnvData -> {
                if (!newEnvKeys.contains(scriptEnvData.getEnvKey())) {
                    deleteKeys.add(scriptEnvData.getEnvKey());
                }
            });
            if (!deleteKeys.isEmpty()) {
                removeByAppIdAndEnvKeys(scriptId, deleteKeys);
            }
        }
        List<String> existShellKey = shellKeySet(envList);
        List<AppEnvInfoDto> appEnvInfoVoList = envList.stream().peek(appEnvInfoDto -> {
            if(CharSequenceUtil.isEmpty(appEnvInfoDto.getShellKey()) || ScriptDataUtils.GLOBAL_SHELL_KEY_LIST.contains(appEnvInfoDto.getShellKey())){
                String shellKey = ScriptDataUtils.generateShellKey(existShellKey);
                existShellKey.add(shellKey);
                appEnvInfoDto.setShellKey(shellKey);
                appEnvInfoDto.setAppId(scriptId);
            }
        }).toList();
        saveOrUpdateBatch(AppEnvInfoMapstruct.INSTANCE.dtoToEntity(appEnvInfoVoList));
    }

    private void removeByAppIdAndEnvKeys(Long scriptId, List<String> deleteKeys) {
        LambdaQueryWrapper<ScriptEnvData> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ScriptEnvData::getScriptId, scriptId);
        wrapper.in(ScriptEnvData::getEnvKey, deleteKeys);
        remove(wrapper);
    }

    private List<String> shellKeySet(List<AppEnvInfoDto> envList) {
        List<String> shellKeyList = new ArrayList<>();
        envList.forEach(appEnvInfoDto -> {
            String shellKey = appEnvInfoDto.getShellKey();
            if (CharSequenceUtil.isNotBlank(shellKey)) {
                shellKeyList.add(shellKey);
            }
        });
        return shellKeyList;
    }
}




