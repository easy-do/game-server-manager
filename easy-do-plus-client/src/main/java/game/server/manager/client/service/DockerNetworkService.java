package plus.easydo.push.client.service;

import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.model.Network;
import plus.easydo.push.client.model.CreateNetworkDto;
import plus.easydo.push.client.service.base.DockerNetworksBaseService;
import plus.easydo.push.client.utils.DockerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 网络相关
 * @date 2022/11/21
 */
@Slf4j
@Component
public class DockerNetworkService {

    @Autowired
    private DockerNetworksBaseService dockerNetworksBaseService;

    public List<Network> networkList(){
        return dockerNetworksBaseService.networkList(DockerUtils.creteDockerClient());
    }

    public Network inspectNetwork(String networkId){
        log.info("Docker inspectNetwork");
        return dockerNetworksBaseService.inspectNetwork(DockerUtils.creteDockerClient(),networkId);
    }

    public Void removeNetwork(String networkId){
        log.info("Docker removeNetwork");
        return dockerNetworksBaseService.removeNetwork(DockerUtils.creteDockerClient(),networkId);
    }

    public CreateNetworkResponse createNetwork(CreateNetworkDto createNetworkDto){
        log.info("Docker createNetwork");
        return dockerNetworksBaseService.createNetwork(DockerUtils.creteDockerClient(),createNetworkDto);
    }

}
