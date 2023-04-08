package game.server.manager.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Network;
import game.server.manager.client.contants.ClientSocketTypeEnum;
import game.server.manager.client.contants.MessageTypeConstants;
import game.server.manager.client.model.BindDto;
import game.server.manager.client.model.CreateContainerDto;
import game.server.manager.client.model.CreateNetworkDto;
import game.server.manager.client.model.PortBindDto;
import game.server.manager.client.model.socket.ApplicationVersion;
import game.server.manager.client.model.socket.ApplicationVersionConfig;
import game.server.manager.client.model.socket.ServerMessage;
import game.server.manager.client.server.SyncServer;
import game.server.manager.client.service.DockerContainerService;
import game.server.manager.client.service.DockerImageService;
import game.server.manager.client.service.DockerNetworkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 安装应用处理服务
 */
@Slf4j
@Service(MessageTypeConstants.INSTALL_APPLICATION)
public class InstallApplicationHandlerService implements AbstractHandlerService {

    @Autowired
    private SyncServer syncServer;

    @Autowired
    private DockerContainerService dockerContainerService;

    @Autowired
    private DockerImageService dockerImageService;

    @Autowired
    private DockerNetworkService dockerNetworkService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("InstallApplicationHandler install application ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        try {
            String data = serverMessage.getData();
            ApplicationVersion applicationVersion = objectMapper.readValue(data, ApplicationVersion.class);
            String configData = applicationVersion.getConfData();
            ApplicationVersionConfig applicationVersionConfig = objectMapper.readValue(configData, ApplicationVersionConfig.class);
            List<ApplicationVersionConfig.SubApps> subApps = applicationVersionConfig.getSubApps();
            syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "start install application");
            if(Objects.nonNull(applicationVersionConfig.getCreateNetworks()) && applicationVersionConfig.getCreateNetworks()){
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "start create networks");
                List<CreateNetworkDto> dtoList = applicationVersionConfig.getNetworks();
                List<Network> networkList = dockerNetworkService.networkList();
                List<String> names = networkList.stream().map(Network::getName).toList();
                for (CreateNetworkDto dto : dtoList){
                    if(!names.contains(dto.getName())){
                        syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "create networks "+dto.getName());
                        dockerNetworkService.createNetwork(dto);
                    }
                }
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "create networks success");
            }
            for (ApplicationVersionConfig.SubApps subApp: subApps) {
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "install sub application" + subApp.getKey());
                String image = subApp.getImage();
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "start pull image:" + image);
                String pullLog = dockerImageService.pullImage(image);
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, pullLog);
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "create container:" + image);
                CreateContainerDto createDto = getCreateContainerDto(subApp);
                log.info("create container:{}",createDto);
                CreateContainerResponse res = dockerContainerService.createContainer(createDto);
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "create container:" + image + "success");
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "start container:" + image);
                dockerContainerService.startContainer(res.getId());
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "start container:" + image+ "success");
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(res));
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "install sub application success");
            }
            syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "install application end");
            syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "success");
        }catch (Exception e) {
            syncServer.sendFailMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "install error:"+ExceptionUtil.getMessage(e));
        }
        return null;
    }

    private static CreateContainerDto getCreateContainerDto(ApplicationVersionConfig.SubApps subApp) {
        CreateContainerDto createDto = CreateContainerDto.builder().build();
        BeanUtils.copyProperties(subApp,createDto);
        List<Map<String, String>> envs = subApp.getEnvs();
        if(Objects.nonNull(envs) && !envs.isEmpty()){
            createDto.setEnv(envs.get(0));
        }
        List<PortBindDto> portBinds = subApp.getPortBinds();
        if(Objects.nonNull(portBinds) && !portBinds.isEmpty()){
            createDto.setPortBinds(portBinds);
        }
        List<BindDto> binds = subApp.getBinds();
        if(Objects.nonNull(portBinds) && !portBinds.isEmpty()){
            createDto.setBinds(binds);
        }
        return createDto;
    }
}
