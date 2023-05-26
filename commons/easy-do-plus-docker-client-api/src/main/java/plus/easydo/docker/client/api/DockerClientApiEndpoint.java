package plus.easydo.docker.client.api;

/**
 * @author laoyu
 * @version 1.0
 * @description docker client api 端点
 * @date 2022/11/19
 */
public interface DockerClientApiEndpoint {

    /**
     * 构建api实例
     *
     * @param serverUrl serverUrl
     * @param secret secret
     * @return api.client.plus.easydo.docker.DockerClientApi
     * @author laoyu
     * @date 2022/11/19
     */
    DockerClientApi dockerClientApi(String serverUrl,String secret);

    /**
     * 构建镜像api实例
     *
     * @param serverUrl serverUrl
     * @param secret secret
     * @return api.client.plus.easydo.docker.DockerImageApi
     * @author laoyu
     * @date 2022/11/19
     */
    DockerImageApi dockerImageApi(String serverUrl, String secret);

    /**
     * 构建容器api实例
     *
     * @param serverUrl serverUrl
     * @param secret secret
     * @return api.client.plus.easydo.docker.DockerContainerApi
     * @author laoyu
     * @date 2022/11/19
     */
    DockerContainerApi dockerContainerApi(String serverUrl, String secret);
}
