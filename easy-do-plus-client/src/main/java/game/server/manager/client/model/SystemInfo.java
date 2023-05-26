package plus.easydo.push.client.model;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/8/1
 */
@Data
public class SystemInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String osName;
    private String cpuInfo;

    private String memory;

    public SystemInfo() {
        osName = OshiUtil.getOs().getFamily();
        memory = OshiUtil.getMemory().toString();
        CpuInfo cpu = OshiUtil.getCpuInfo();
        cpuInfo = "CPU核心数=" + cpu.getCpuNum() +
                ", CPU总的使用率=" + cpu.getToTal() +
                ", CPU系统使用率=" + cpu.getSys() +
                ", CPU用户使用率=" + cpu.getUser() +
                ", CPU当前等待率=" + cpu.getWait() +
                ", CPU当前空闲率=" + cpu.getFree() +
                ", CPU利用率=" + cpu.getUsed() +
                ", CPU型号信息='" + cpu.getCpuModel();
    }
}
