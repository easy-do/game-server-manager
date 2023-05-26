package plus.easydo.docker.compose;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description  为托管服务帐户配置凭据规范。
 *          使用 Windows 容器支持服务的 Compose 实现必须支持credential_specfile:和 协议。
 *          registry:Compose 实现还可以支持自定义用例的附加协议。
 *          credential_spec必须采用以下格式file://<filename>或registry://<value-name>.
 *          credential_spec:
 *            file: my-credential-spec.json
 *          使用 时registry:，将从守护程序主机上的 Windows 注册表中读取凭据规范。具有给定名称的注册表值必须位于：
 *          credential_spec:
 *            registry: my-credential-spec
 * gMSA 配置示例
 * 在为服务配置 gMSA 凭证规范时，您只需使用 指定凭证规范config，如下例所示：
 *
 *
 * services:
 *   myservice:
 *     image: myimage:latest
 *     credential_spec:
 *       config: my_credential_spec
 *
 * configs:
 *   my_credentials_spec:
 *     file: ./my-credential-spec.json|
 * @date 2023/5/14
 */

@NoArgsConstructor
@Data
public class CredentialSpec {

    @JsonProperty("path")
    private String file;

    @JsonProperty("path")
    private String registry;
}
