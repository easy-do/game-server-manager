package game.server.manager.docker.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateVolumeResponse;
import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.github.dockerjava.api.command.ListVolumesResponse;
import game.server.manager.docker.model.CreateVolumesDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author laoyu
 * @version 1.0
 * @description docker存储卷相关
 * @date 2023/4/1
 */
@Slf4j
@Component
public class DockerVolumesService {

    public ListVolumesResponse volumesList(DockerClient dockerClient){
       return dockerClient.listVolumesCmd().exec();
    }

    public InspectVolumeResponse inspectNetwork(DockerClient dockerClient, String volumesName){
        return dockerClient.inspectVolumeCmd(volumesName).exec();
    }

    public Void removeVolumes(DockerClient dockerClient, String volumesName){
        return dockerClient.removeVolumeCmd(volumesName).exec();
    }

    public CreateVolumeResponse createVolumes(DockerClient dockerClient, CreateVolumesDto createVolumesDto){
        return dockerClient.createVolumeCmd()
                .withName(createVolumesDto.getName())
                .withDriver(createVolumesDto.getDriver())
                .withLabels(createVolumesDto.getLabels())
                .withDriverOpts(createVolumesDto.getDriverOpts())
                .exec();
    }

}
