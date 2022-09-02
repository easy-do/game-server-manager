package game.server.manager.server.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import game.server.manager.common.utils.AppScriptUtils;
import game.server.manager.server.dto.AppEnvInfoDto;
import game.server.manager.server.entity.AppEnvInfo;
import game.server.manager.server.entity.AppScript;
import game.server.manager.server.mapstruct.AppEnvInfoMapstruct;
import game.server.manager.server.service.AppEnvInfoService;
import game.server.manager.server.mapper.AppEnvInfoMapper;
import game.server.manager.server.service.AppScriptService;
import game.server.manager.common.vo.AppEnvInfoVo;
import game.server.manager.common.vo.ScriptEnvListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuzhanfeng
 * @description 针对表【app_env_info(应用变量)】的数据库操作Service实现
 * @createDate 2022-05-24 19:51:01
 */
@Service
public class AppEnvInfoServiceImpl extends ServiceImpl<AppEnvInfoMapper, AppEnvInfo>
        implements AppEnvInfoService {

    @Autowired
    private AppScriptService appScriptService;

    @Override
    public List<ScriptEnvListVo> listByScriptId(Long scriptId) {
        List<ScriptEnvListVo> allEvenList = new ArrayList<>();
        //检查脚本是否存在依赖脚本，如果存在则查询出依赖脚本的变量
        AppScript appScript = appScriptService.getById(scriptId);
        String basicScript = appScript.getBasicScript();
        if (CharSequenceUtil.isNotBlank(basicScript)) {
            String[] scriptIds = basicScript.split(",");
            if (scriptIds.length > 0) {
                for (int i = 0; i < scriptIds.length; i++) {
                    AppScript script = appScriptService.getById(scriptIds[i]);
                    List<AppEnvInfo> list = getListByScriptId(script.getId());
                    ScriptEnvListVo scriptEnvListVo = ScriptEnvListVo.builder()
                            .scriptName(script.getScriptName())
                            .env(AppEnvInfoMapstruct.INSTANCE.entityListToVoList(list))
                            .build();
                    allEvenList.add(scriptEnvListVo);
                }
            }
        }
        //查询主脚本的变量
        List<AppEnvInfo> list = getListByScriptId(scriptId);
        ScriptEnvListVo scriptEnvListVo = ScriptEnvListVo.builder()
                .scriptName(appScript.getScriptName())
                .env(AppEnvInfoMapstruct.INSTANCE.entityListToVoList(list))
                .build();
        allEvenList.add(scriptEnvListVo);
        return allEvenList;
    }

    @Override
    public List<AppEnvInfo> getListByScriptId(Long scriptId) {
        LambdaQueryWrapper<AppEnvInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppEnvInfo::getAppId, scriptId);
        wrapper.orderByDesc(AppEnvInfo::getCreateTime);
        wrapper.select(AppEnvInfo::getId, AppEnvInfo::getEnvName, AppEnvInfo::getEnvKey, AppEnvInfo::getShellKey, AppEnvInfo::getEnvValue, AppEnvInfo::getEnvType, AppEnvInfo::getDescription);
        return list(wrapper);
    }

    @Override
    public List<AppEnvInfoVo> getVoListByScriptId(Long scriptId) {
        List<AppEnvInfo> list = getListByScriptId(scriptId);
        return AppEnvInfoMapstruct.INSTANCE.entityListToVoList(list);
    }

    @Override
    public boolean removeByAppId(Long id) {
        LambdaQueryWrapper<AppEnvInfo> envWrapper = Wrappers.lambdaQuery();
        envWrapper.eq(AppEnvInfo::getAppId, id);
        return remove(envWrapper);
    }

    @Override
    public void saveOrUpdateScriptEnvList(Long scriptId, List<AppEnvInfoDto> envList) {
        List<AppEnvInfo> existEnvList = getListByScriptId(scriptId);
        if (!existEnvList.isEmpty()) {
            //如果存在历史变量则比较结果,旧的变量直接update，新的直接新增
            envList = envList.stream().peek(appEnvInfoDto -> {
                String key = appEnvInfoDto.getEnvKey();
                for (AppEnvInfo appEnvInfo : existEnvList) {
                    if (appEnvInfo.getEnvKey().equals(key)) {
                        appEnvInfoDto.setId(appEnvInfo.getId());
                        appEnvInfoDto.setShellKey(appEnvInfo.getShellKey());
                    }
                }
            }).toList();
            //如果只是修改了变量的key,但无法对比，则删掉多出来的已存在的变量
            List<String> deleteKeys = new ArrayList<>();
            List<String> newEnvKeys = envList.stream().map(AppEnvInfoDto::getEnvKey).toList();
            existEnvList.forEach(appEnvInfo -> {
                if (!newEnvKeys.contains(appEnvInfo.getEnvKey())) {
                    deleteKeys.add(appEnvInfo.getEnvKey());
                }
            });
            if (!deleteKeys.isEmpty()) {
                removeByAppIdAndEnvKeys(scriptId, deleteKeys);
            }
        }
        List<String> existShellKey = shellKeySet(envList);
        List<AppEnvInfoDto> appEnvInfoVoList = envList.stream().peek(appEnvInfoDto -> {
            if(CharSequenceUtil.isEmpty(appEnvInfoDto.getShellKey()) || AppScriptUtils.GLOBAL_SHELL_KEY_LIST.contains(appEnvInfoDto.getShellKey())){
                String shellKey = AppScriptUtils.generateShellKey(existShellKey);
                existShellKey.add(shellKey);
                appEnvInfoDto.setShellKey(shellKey);
                appEnvInfoDto.setAppId(scriptId);
            }
        }).toList();
        saveOrUpdateBatch(AppEnvInfoMapstruct.INSTANCE.dtoListToEntityList(appEnvInfoVoList));
    }

    private void removeByAppIdAndEnvKeys(Long scriptId, List<String> deleteKeys) {
        LambdaQueryWrapper<AppEnvInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppEnvInfo::getAppId, scriptId);
        wrapper.in(AppEnvInfo::getEnvKey, deleteKeys);
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




