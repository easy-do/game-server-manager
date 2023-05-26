package plus.easydo.docker.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.model.Network;
import plus.easydo.docker.model.CreateNetworkDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description docker网络相关
 * @date 2023/4/1
 */
@Slf4j
@Component
public class DockerNetworksService {

    public List<Network> networkList(DockerClient dockerClient){
        return dockerClient.listNetworksCmd().exec();
    }

    public Network inspectNetwork(DockerClient dockerClient, String networkId){
        return dockerClient.inspectNetworkCmd().withNetworkId(networkId).exec();
    }

    public Void removeNetwork(DockerClient dockerClient, String networkId){
        return dockerClient.removeNetworkCmd(networkId).exec();
    }

    public CreateNetworkResponse createNetwork(DockerClient dockerClient, CreateNetworkDto createNetworkDto){
        return dockerClient.createNetworkCmd()
                .withName(createNetworkDto.getName())
                .withDriver(createNetworkDto.getDriver())
                .withAttachable(createNetworkDto.getAttachable())
                .withInternal(createNetworkDto.getInternal())
                .withLabels(createNetworkDto.getLabels())
                .withCheckDuplicate(createNetworkDto.getCheckDuplicate())
                .withIpam(createNetworkDto.getIpam())
                .exec();
    }


}
