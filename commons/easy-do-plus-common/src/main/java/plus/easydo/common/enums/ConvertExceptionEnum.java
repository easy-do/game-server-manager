package plus.easydo.common.enums;

/**
 * @author laoyu
 * @version 1.0
 * @description 异常转换枚举
 * @date 2022/9/27
 */

public enum ConvertExceptionEnum {

    /**/
    AUTH_FAIL("Auth fail","服务器账号密码验证失败。"),
    CONNECTION_TIMED_OUT("Connection timed out", "连接超时,请确保服务器地址可访问。"),
    TIMEOUT ("timeout","连接超时。"),
    CONNECTION_REFUSED   ("Connection refused","尝试与服务器连接被拒绝,请确保服务器地址可访问。"),
    CONNECTION_RESET   ("Connection reset","服务器连接被重置,请确保服务器地址可访问。"),
    CHANNEL_IS_NOT_OPENED    ("channel is not opened","脚本执行中断,与服务器的通信异常关闭,无法跟踪脚本后续执行情况。"),
    PERMISSION_DENIED  ("SftpException: Permission denied","上传脚本失败，没有目录访问权限。"),
    FAILURE  ("SftpException: Failure","上传脚本失败，磁盘空间不足。"),
    UNKNOWN_HOST("UnknownHostException","未知的主机地址");



    private final String en;
    private final String cn;

    ConvertExceptionEnum(String en, String cn) {
        this.en = en;
        this.cn = cn;
    }

    public String getEn() {
        return en;
    }

    public String getCn() {
        return cn;
    }
}
