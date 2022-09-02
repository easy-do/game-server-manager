package game.server.manager.server.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.ssh.JschUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import game.server.manager.server.annotation.Job;
import game.server.manager.server.entity.AppScript;
import game.server.manager.server.entity.ServerInfo;
import game.server.manager.server.entity.SysJob;
import game.server.manager.server.service.AppScriptService;
import game.server.manager.server.service.ServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author laoyu
 * @version 1.0
 * @description 服务器脚本执行任务
 * @date 2022/6/8
 */
@Component
public class ScriptExecJob {

    @Autowired
    private AppScriptService appScriptService;

    @Autowired
    private ServerInfoService serverInfoService;

    @Job("execScriptJob")
    public String execScript(SysJob sysJob) throws SftpException, IOException {
        String param = sysJob.getJobParam();
        ScriptJobModel jobModel = JSON.parseObject(param, ScriptJobModel.class);
        AppScript appScript = appScriptService.getById(jobModel.getAppScriptId());
        ServerInfo serverInfo = serverInfoService.getById(jobModel.getServerId());
        String address = serverInfo.getAddress();
        String port = serverInfo.getPort();
        String userName = serverInfo.getUserName();
        String password = serverInfo.getPassword();
        Session session = JschUtil.openSession(address, Integer.parseInt(port), userName, password);
        //1.上传脚本
        String scriptStr = appScript.getScriptFile();
        scriptStr = scriptStr.replace("\r\n","\n");
        InputStream inputStream = new ByteArrayInputStream(scriptStr.getBytes());
        ChannelSftp sftp = JschUtil.openSftp(session);
        String fileName = UUID.randomUUID().toString();
        OutputStream sftpOps = sftp.put("/root/"+fileName);
        IoUtil.copy(inputStream, sftpOps);
        sftpOps.flush();
        sftpOps.close();
        inputStream.close();
        //2.执行脚本
        String execStr = "chmod u+x "+fileName+" && ./"+fileName;
        StringBuilder stringBuilder = new StringBuilder(execStr);
        JSONObject env = jobModel.getEnv();
        if(CollUtil.isNotEmpty(env)){
            stringBuilder.append(" '").append(env.toJSONString()).append("'");
        }
        String result = JschUtil.exec(session, stringBuilder.toString(), CharsetUtil.CHARSET_UTF_8);
        //3.删除脚本
        JschUtil.exec(session, "rm -r " + fileName, CharsetUtil.CHARSET_UTF_8);
        if(session.isConnected()){
            session.disconnect();
        }
        return result;
    }
}
