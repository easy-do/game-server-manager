package plus.easydo.server;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import plus.easydo.docker.compose.DockerCompose;
import org.yaml.snakeyaml.Yaml;

import java.io.File;


/**
 * @author laoyu
 * @version 1.0
 * @description TODO
 * @date 2023/5/13
 */

public class Test {
    public static void main(String[] args) {
        Yaml yaml = new Yaml();
        File file = FileUtil.file("E:/docker-compose.yaml");
        JSONObject ymlJson = yaml.loadAs(FileUtil.getInputStream(file),JSONObject.class);
        DockerCompose dockerCompose = ymlJson.toBean(DockerCompose.class);
        System.err.println(JSONUtil.toJsonStr(dockerCompose, JSONConfig.create().setIgnoreNullValue(false)));

        System.err.println(yaml.dump(JSONUtil.parseObj(dockerCompose,JSONConfig.create().setIgnoreNullValue(true))));
    }
}

