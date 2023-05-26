package plus.easydo.log;



/**
 * @author laoyu
 * @version 1.0
 * @description 日志数据库存储服务
 * @date 2022/6/18
 */

public interface LogDbService {

    /**
     * 存储操作日志
     *
     * @param log log
     * @author laoyu
     * @date 2022/6/18
     */
    void save(Log log);

}
