package game.server.manager.server.service.impl;

import cn.hutool.core.io.IoUtil;
import com.github.dockerjava.api.model.Image;
import feign.Response;
import game.server.manager.docker.client.api.DockerClientApiEndpoint;
import game.server.manager.docker.client.api.DockerImageApi;
import game.server.manager.common.result.R;
import game.server.manager.server.websocket.model.SocketPullImageData;
import game.server.manager.server.service.DockerDetailsService;
import game.server.manager.server.service.DockerImageService;
import game.server.manager.server.vo.DockerDetailsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description dicker 镜像相关
 * @date 2022/11/19
 */
@Service
public class DockerImageServiceImpl implements DockerImageService {

    @Resource
    private DockerDetailsService dockerDetailsService;

    @Resource
    private DockerClientApiEndpoint dockerClientApiEndpoint;

    private DockerImageApi dockerImageApi(String dockerId){
        DockerDetailsVo dockerDetailsVo = dockerDetailsService.info(dockerId);
        return dockerClientApiEndpoint.dockerImageApi(dockerDetailsVo.getDockerHost(), dockerDetailsVo.getDockerSecret());
    }

    @Override
    public R<List<Image>> listImages(String dockerId) {
        return dockerImageApi(dockerId).listImages();
    }

    @Override
    public R<Void> removeImage(String dockerId, String imageId) {
        return dockerImageApi(dockerId).removeImage(imageId);
    }

    @Override
    public void pullImage(String dockerId, String repository, ServletOutputStream outputStream) throws IOException {
        Response response = dockerImageApi(dockerId).pullImage(repository);
        Response.Body body = response.body();
        IoUtil.copy(body.asInputStream(),outputStream);
    }

    @Override
    public void socketPullImage(SocketPullImageData socketPullImageData) {
        Response response = dockerImageApi(socketPullImageData.getDockerId()).pullImage(socketPullImageData.getRepository());
        Response.Body body = response.body();
        Session session = socketPullImageData.getSession();
        InputStream in = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            in = body.asInputStream();
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);
            String str;
            // 通过readLine()方法按行读取字符串
            while ((str = br.readLine()) != null) {
                if(Objects.nonNull(session) && session.isOpen()){
                    session.getBasicRemote().sendText(str);
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            // 统一在finally中关闭流，防止发生异常的情况下，文件流未能正常关闭
            try {
                if(Objects.nonNull(session) && session.isOpen()){
                    session.close();
                }
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
