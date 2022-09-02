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
@SyncServerClass("pluginsData")
public class PluginsDataServer extends AbstractDefaultServer {

    @Autowired
    private ApplicationInfoService applicationInfoService;

    @Override
    Object processData(SyncData syncData) {
        String applicationId = syncData.getApplicationId();
        String data = syncData.getData();
        Boolean encryption = syncData.getEncryption();

        try{

            ApplicationInfo dbApplicationInfo = applicationInfoService.getById(applicationId);
            if (Objects.isNull(dbApplicationInfo)) {
                return DataResult.fail("应用不存在");
            }
            if(encryption){
               RSA rsa = new RSA(dbApplicationInfo.getPrivateKey(), null);
               data = StrUtil.str(rsa.decrypt(Base64.decode(data), KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
            }
            PluginsData pluginsData = JSON.parseObject(data, PluginsData.class);
            if (CharSequenceUtil.isEmpty(pluginsData.getApplicationId())) {
                return DataResult.fail("应用标识为空");
            }
            if (CharSequenceUtil.isEmpty(pluginsData.getVersion())) {
                return DataResult.fail("未知的版本信息");
            }
            if (CharSequenceUtil.isEmpty(pluginsData.getIp())) {
                return DataResult.fail("未知的服务器ip");
            }
            ApplicationInfo applicationInfo = ApplicationInfo.builder().build();
            applicationInfo.setApplicationId(pluginsData.getApplicationId());
            applicationInfo.setLastUpTime(LocalDateTime.now());
            applicationInfo.setPluginsData(JSON.toJSONString(pluginsData));
            if(applicationInfoService.updateById(applicationInfo)){
                return dbApplicationInfo.getIsBlack().equals(StatusEnum.ENABLE.getCode())?DataResult.ok():DataResult.fail("黑名单");
            }
            return DataResult.fail("同步应用信息出错。");
        }catch (Exception exception){
            logger.error("接收客户端启动数据发生异常{}", ExceptionUtil.getMessage(exception));
            return DataResult.fail("同步数据数据发生错误");
        }
    }
}
