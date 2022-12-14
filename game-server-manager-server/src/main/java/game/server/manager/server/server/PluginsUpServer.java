package game.server.manager.server.server;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson2.JSON;
import game.server.manager.common.mode.PluginsData;
import game.server.manager.common.mode.SyncData;
import game.server.manager.common.result.DataResult;
import game.server.manager.server.annotation.SyncServerClass;
import game.server.manager.server.entity.ApplicationInfo;
import game.server.manager.common.enums.StatusEnum;
import game.server.manager.server.service.ApplicationInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * @author laoyu
 * @version 1.0
 */
@SyncServerClass("up")
public class PluginsUpServer extends AbstractDefaultServer {

    @Autowired
    private ApplicationInfoService applicationInfoService;


    @Override
    Object processData(SyncData syncData) {
        String applicationId = syncData.getApplicationId();
        String data = syncData.getData();
        Boolean encryption = syncData.getEncryption();
        try {
            if(CharSequenceUtil.isEmpty(applicationId)){
                return DataResult.fail("客户端标识为空。");
            }
            ApplicationInfo application = applicationInfoService.getById(applicationId);
            if(Objects.isNull(application)){
                return DataResult.fail("应用不存在。");
            }
            if(application.getIsBlack().equals(StatusEnum.DISABLE.getCode())){
                return DataResult.fail("应用已处于黑名单");
            }
            if(encryption){
                RSA rsa = new RSA(application.getPrivateKey(), null);
                data = StrUtil.str(rsa.decrypt(Base64.decode(data), KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
            }
            PluginsData pluginsData = JSON.parseObject(data, PluginsData.class);
            if (CharSequenceUtil.isEmpty(pluginsData.getIp())) {
                return DataResult.fail("未知的服务器ip");
            }
            if (CharSequenceUtil.isEmpty(pluginsData.getVersion())) {
                return DataResult.fail("未知的版本信息");
            }
            ApplicationInfo applicationInfo = ApplicationInfo.builder()
                    .applicationId(applicationId)
                    .pluginsData(JSON.toJSONString(pluginsData))
                    .lastUpTime(LocalDateTime.now()).build();
            return applicationInfoService.updateById(applicationInfo)?DataResult.okMsg("确认存活。"):DataResult.fail("更新存活状态失败。");
        } catch (Exception exception) {
            logger.error("接收客户端心跳数据发生异常{}", ExceptionUtil.getMessage(exception));
            return DataResult.fail(ExceptionUtil.getMessage(exception));
        }
    }


}
