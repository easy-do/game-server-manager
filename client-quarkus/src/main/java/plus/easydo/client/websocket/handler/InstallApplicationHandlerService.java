package plus.easydo.client.websocket.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.command.CreateContainerResponse;
import plus.easydo.client.contants.ClientSocketTypeEnum;
import plus.easydo.client.contants.MessageTypeConstants;
import plus.easydo.client.model.CreateContainerDto;
import plus.easydo.client.model.PortBindDto;
import plus.easydo.client.model.socket.ApplicationVersion;
import plus.easydo.client.model.socket.ApplicationVersionConfig;
import plus.easydo.client.model.socket.ServerMessage;
import plus.easydo.client.server.SyncServer;
import plus.easydo.client.service.DockerContainerService;
import plus.easydo.client.service.DockerImageService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author yuzhanfeng
 * @Date 2022/11/26 23:29
 * @Description 安装应用处理服务
 */
@Slf4j
@ApplicationScoped()
@Named(MessageTypeConstants.INSTALL_APPLICATION)
public class InstallApplicationHandlerService implements AbstractHandlerService {

    @Inject
    SyncServer syncServer;

    @Inject
    DockerContainerService dockerContainerService;

    @Inject
    DockerImageService dockerImageService;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public Void handler(ServerMessage serverMessage) {
        log.info("InstallApplicationHandler install application ==> {}",serverMessage);
        String messageId = serverMessage.getMessageId();
        try {
            String data = serverMessage.getData();
            ApplicationVersion applicationVersion = objectMapper.readValue(data, ApplicationVersion.class);
            String configData = applicationVersion.getConfData();
            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, ApplicationVersionConfig.class);
            List<ApplicationVersionConfig> configList = objectMapper.readValue(configData, javaType);
            syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "start install application");
            for (ApplicationVersionConfig config: configList) {
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "install sub application" + config.getKey());
                String image = config.getImage();
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "start pull image:" + image);
                String pullLog = dockerImageService.pullImage(image);
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, pullLog);
                syncServer.sendOkMessage(ClientSocketTypeEnum.INSTALL_APPLICATION_RESULT,messageId, "create container:" + image);
                CreateContainerDto createDto = CreateContainerDto.builder().build();
                BeanUtil.copyProperties(config,createDto);
                List<Map<String, String>> envs = config.getEnvs();
                if(Objects.nonNull(envs) && !envs.isEmpty()){
                    createDto.setEnv(envs.get(0));
                }
                List<PortBindDto> portBinds = config.getPortBinds();
                if(Objects.nonNull(portBinds) && !portBinds.isEmpty()){
                    createDto.setPortBinds(portBinds);
                }
                createDto.setPortBinds(config.getPortBinds());
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
}
