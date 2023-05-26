package plus.easydo.push.client.model.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import plus.easydo.push.client.model.BindDto;
import plus.easydo.push.client.model.CreateNetworkDto;
import plus.easydo.push.client.model.PortBindDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description 应用版本信息
 * @date 2023/4/1
 */

@NoArgsConstructor
@Data
public class ApplicationVersionConfig {

    @JsonProperty("createNetworks")
    private Boolean createNetworks;
    @JsonProperty("networks")
    private List<CreateNetworkDto> networks;
    @JsonProperty("subApps")
    private List<SubApps> subApps;

    @NoArgsConstructor
    @Data
    public static class SubApps{
        @JsonProperty("key")
        private Integer key;
        @JsonProperty("name")
        private String name;
        @JsonProperty("version")
        private String version;
        @JsonProperty("image")
        private String image;
        @JsonProperty("cmd")
        private String cmd;
        @JsonProperty("labels")
        private String labels;
        @JsonProperty("links")
        private String links;
        @JsonProperty("attachStdin")
        private Boolean attachStdin;
        @JsonProperty("stdinOpen")
        private Boolean stdinOpen;
        @JsonProperty("privileged")
        private Boolean privileged;
        @JsonProperty("tty")
        private Boolean tty;
        @JsonProperty("networkMode")
        private String networkMode;
        @JsonProperty("envs")
        private List<Map<String,String>> envs;
        @JsonProperty("portBinds")
        private List<PortBindDto> portBinds;
        @JsonProperty("binds")
        private List<BindDto> binds;
        @JsonProperty("publishAllPorts")
        private Boolean publishAllPorts;
        @JsonProperty("nanoCPUs")
        private Long nanoCPUs;
        @JsonProperty("memory")
        private Long memory;
        @JsonProperty("shmSize")
        private Long shmSize;
        @JsonProperty("memorySwap")
        private Long memorySwap;
        @JsonProperty("restartPolicy")
        private String restartPolicy;
    }

}
