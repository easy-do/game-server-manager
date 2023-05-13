package game.server.manager.common.mode.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 应用版本配置
 * @date 2023/4/1
 */

@NoArgsConstructor
@Data
public class ApplicationVersionConfig {


    @JsonProperty("key")
    private Integer key;
    @JsonProperty("name")
    private String name;
    @JsonProperty("version")
    private String version;
    @JsonProperty("image")
    private String image;
    @JsonProperty("attachStdin")
    private Boolean attachStdin;
    @JsonProperty("stdinOpen")
    private Boolean stdinOpen;
    @JsonProperty("tty")
    private Boolean tty;
    @JsonProperty("networkMode")
    private String networkMode;
    @JsonProperty("confData")
    private ConfData confData;

    @NoArgsConstructor
    @Data
    public static class ConfData {
        @JsonProperty("envs")
        private List<Envs> envs;
        @JsonProperty("portBinds")
        private List<PortBinds> portBinds;

        @NoArgsConstructor
        @Data
        public static class Envs {
            @JsonProperty("envName")
            private String envName;
            @JsonProperty("envKey")
            private String envKey;
            @JsonProperty("envValue")
            private String envValue;
            @JsonProperty("description")
            private String description;
        }

        @NoArgsConstructor
        @Data
        public static class PortBinds {
            @JsonProperty("containerPort")
            private String containerPort;
            @JsonProperty("localPort")
            private String localPort;
            @JsonProperty("protocol")
            private String protocol;
            @JsonProperty("description")
            private String description;
        }
    }
}
