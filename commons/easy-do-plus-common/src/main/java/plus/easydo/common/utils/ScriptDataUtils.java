package plus.easydo.common.utils;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSONObject;
import plus.easydo.common.exception.BizException;
import plus.easydo.common.vo.ScriptEnvDataVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 脚本工具类
 * @date 2022/8/20
 */

public class ScriptDataUtils {


    public static final List<ScriptEnvDataVo> GLOBAL_ENV_LIST = ListUtil.toList(
            ScriptEnvDataVo.builder().envName("脚本版本").envKey("SCRIPT_VERSION").envValue("no").shellKey("c").description("全局变量(勿删)-应用版本").build()
    );

    public static final List<String> GLOBAL_SHELL_KEY_LIST = Arrays.asList("a");

    public static final String SCRIPT_START_LINE = "#!/bin/bash\n";

    public static final String ENV_GET_START_LINE = "#-----BEGIN 脚本取参-----\n";

    public static final String ENV_GET_END_LINE = "#-----END 脚本取参-----\n";

    public static final String EXPORT_REMARK = "##envName #description\n";
    public static final String EXPORT = "export #envKey=\"#envValue\"\n";

    public static final String WHILE_LINE = "while getopts {#shellKeys:} opt\n";

    public static final String DO_LINE = "do\n";

    public static final String CASE_LINE = "    case $opt in\n";

    public static final String SHELL_KEY_LINE = "        #shellKey)\n";

    public static final String SET_KEY_LINE = "            #envKey=$OPTARG;;\n";

    public static final String OTHER_KEY_LINE = "        ?)\n";

    public static final String ECHO_NO_LINE = "        echo \"向脚本传递了意外的参数, 第$OPTIND个 值为:$OPTARG\"\n";

    public static final String EXIT_LINE = "        exit 1;;\n";

    public static final String ESAC_LINE = "    esac\n";

    public static final String DONE_LINE = "done\n";

    public static final String GET_ENV_VALUE_START_LINE = "#-----BEGIN 取参示例-----\n";

    public static final String GET_ENV_VALUE_END_LINE = "#-----END 取参示例-----\n";

    public static final String ECHO_ENV_VALUE_LINE = "echo #envKey=$#envKey\n";

    public static final String EXEC_LINE = "#脚本执行示例: sh xxx.sh #shellEnvs \n";

    public static final String SCRIPT_CONTEXT = "#-----scriptContent-----\n";

    private ScriptDataUtils() {
    }

    /**
     * 生成变量key
     *
     * @param shellKeyList shellKeyList
     * @return java.lang.String
     * @author laoyu
     * @date 2022/8/21
     */
    public static String generateShellKey(List<String> shellKeyList){
        List<String> allShellKey = allShellKey();
        if(!shellKeyList.isEmpty()){
            shellKeyList.addAll(GLOBAL_SHELL_KEY_LIST);
            allShellKey = allShellKey.stream().filter(shellKey-> !shellKeyList.contains(shellKey)).toList();
        }
        if(allShellKey.isEmpty()){
            throw new BizException("参数可用key为空。");
        }
        return allShellKey.get(0);
    }

    /**
     * 获得所有可用变量key
     *
     * @return java.util.List<java.lang.String>
     * @author laoyu
     * @date 2022/8/21
     */
    public static List<String> allShellKey(){
        int size = 26;
        List<String> allShellKey = new ArrayList<>();
        for(int i = 0; i < size; i++ ){
            allShellKey.add((char)('a' + i)+"");
        }
        return allShellKey;
    }

    /**
     * 生成变量取参脚本
     *
     * @param scriptEnvDataVoList scriptEnvDataVoList
     * @return java.lang.String
     * @author laoyu
     * @date 2022/8/21
     */
    public static String generateShellEnvScript(List<ScriptEnvDataVo> scriptEnvDataVoList){
        //添加固定的全局变量
        List<ScriptEnvDataVo> allEnvList = new ArrayList<>();
        allEnvList.addAll(GLOBAL_ENV_LIST);
        allEnvList.addAll(scriptEnvDataVoList);
        //开始生成脚本
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ENV_GET_START_LINE);
        allEnvList.forEach(appEnvInfoVo -> {
            stringBuilder.append(EXPORT_REMARK.replace("#envName",appEnvInfoVo.getEnvName()).replace("#description",appEnvInfoVo.getDescription()));
            stringBuilder.append(EXPORT.replace("#envKey",appEnvInfoVo.getEnvKey()).replace("#envValue",appEnvInfoVo.getEnvValue()));
        });
        StringBuilder shellKeysBuilder = new StringBuilder();
        for (ScriptEnvDataVo scriptEnvDataVo :allEnvList) {
            shellKeysBuilder.append(":").append(scriptEnvDataVo.getShellKey());
        }
        stringBuilder.append(WHILE_LINE.replace("#shellKeys",shellKeysBuilder.toString()));
        stringBuilder.append(DO_LINE);
        stringBuilder.append(CASE_LINE);
        for (ScriptEnvDataVo scriptEnvDataVo :allEnvList) {
            stringBuilder.append(SHELL_KEY_LINE.replace("#shellKey", scriptEnvDataVo.getShellKey()));
            stringBuilder.append(SET_KEY_LINE.replace("#envKey", scriptEnvDataVo.getEnvKey()));
        }
        stringBuilder.append(OTHER_KEY_LINE);
        stringBuilder.append(ECHO_NO_LINE);
        stringBuilder.append(EXIT_LINE);
        stringBuilder.append(ESAC_LINE);
        stringBuilder.append(DONE_LINE);
        stringBuilder.append(GET_ENV_VALUE_START_LINE);
        allEnvList.forEach(appEnvInfoVo -> stringBuilder.append(ECHO_ENV_VALUE_LINE.replace("#envKey",appEnvInfoVo.getEnvKey())));
        stringBuilder.append(GET_ENV_VALUE_END_LINE);
        //添加执行示例
        StringBuilder execShellEnvs = new StringBuilder();
        allEnvList.forEach(appEnvInfoVo -> execShellEnvs.append("-").append(appEnvInfoVo.getShellKey()).append(" '").append(appEnvInfoVo.getEnvValue()).append("' "));
        stringBuilder.append(EXEC_LINE.replace("#shellEnvs",execShellEnvs.toString()));
        stringBuilder.append(ENV_GET_END_LINE);
        stringBuilder.append(SCRIPT_CONTEXT);
        return stringBuilder.toString();
    }

    public static String generateExecShellEnvs(JSONObject env, List<ScriptEnvDataVo> scriptEnvDataVoList) {
        //添加固定的全局变量
        List<ScriptEnvDataVo> allEnvList = new ArrayList<>();
        allEnvList.addAll(GLOBAL_ENV_LIST);
        allEnvList.addAll(scriptEnvDataVoList);
        //开始生成传参指令
        StringBuilder stringBuilder = new StringBuilder();
        allEnvList.forEach(appEnvInfoVo -> {
            String evnKey = appEnvInfoVo.getEnvKey();
            String shellKey = appEnvInfoVo.getShellKey();
            String envValue = env.getString(evnKey);
            if(CharSequenceUtil.isNotEmpty(envValue)){
                stringBuilder.append("-").append(shellKey).append(" '").append(envValue).append("' ");
            }else {
                stringBuilder.append("-").append(shellKey).append(" '").append(appEnvInfoVo.getEnvValue()).append("' ");
            }
        });
        return stringBuilder.toString();
    }
}
