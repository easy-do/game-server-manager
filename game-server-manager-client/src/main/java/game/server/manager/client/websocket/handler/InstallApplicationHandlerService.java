package game.server.manager.client.websocket.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import game.server.manager.client.model.socket.InstallLogResultData;
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
            InstallLogResultData resultData = InstallLogResultData.builder().status(true).build();
            resultData.setMessage("start install application");
            syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
            if(Objects.nonNull(applicationVersionConfig.getCreateNetworks()) && applicationVersionConfig.getCreateNetworks()){
                resultData.setMessage("start create networks");
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
                List<CreateNetworkDto> dtoList = applicationVersionConfig.getNetworks();
                List<Network> networkList = dockerNetworkService.networkList();
                List<String> names = networkList.stream().map(Network::getName).toList();
                for (CreateNetworkDto dto : dtoList){
                    if(!names.contains(dto.getName())){
                        resultData.setMessage("create networks "+dto.getName());
                        syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
                        dockerNetworkService.createNetwork(dto);
                    }
                }
                resultData.setMessage("create networks success");
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
            }
            for (ApplicationVersionConfig.SubApps subApp: subApps) {
                resultData.setMessage("install sub application" + subApp.getKey());
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
                String image = subApp.getImage();
                resultData.setMessage("start pull image:" + image);
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
                String pullLog = dockerImageService.pullImage(image);
                resultData.setMessage(pullLog);
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
                resultData.setMessage("create container:" + image);
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
                CreateContainerDto createDto = getCreateContainerDto(subApp);
                log.info("create container:{}",createDto);
                CreateContainerResponse res = dockerContainerService.createContainer(createDto);
                resultData.setMessage("create container:" + image + " success");
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
                resultData.setMessage("start container:" + image);
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
                dockerContainerService.startContainer(res.getId());
                resultData.setMessage("start container:" + image+ " success");
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
                resultData.setMessage("start container result: "+ objectMapper.writeValueAsString(res));
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
                resultData.setMessage("install sub application success");
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
            }
            resultData.setMessage("install application end");
            syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
            resultData.setStatus(false);
            resultData.setMessage("success");
            syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
        }catch (Exception e) {
            InstallLogResultData resultData = InstallLogResultData.builder().status(false).build();
            resultData.setMessage("install error:"+ExceptionUtil.getMessage(e));
            try {
                syncServer.sendFailMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, objectMapper.writeValueAsString(resultData));
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
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
