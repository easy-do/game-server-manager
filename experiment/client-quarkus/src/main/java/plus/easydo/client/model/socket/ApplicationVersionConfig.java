package plus.easydo.client.model.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import plus.easydo.client.model.PortBindDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description TODO
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
    @JsonProperty("envs")
    private List<Map<String,String>> envs;
    @JsonProperty("portBinds")
    private List<PortBindDto> portBinds;
}
