package plus.easydo.server.service;

import plus.easydo.server.entity.FileStore;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yuzhanfeng
* @description 针对表【file_store】的数据库操作Service
* @createDate 2022-07-14 19:36:26
*/
public interface FileStoreService extends IService<FileStore> {

    /**
     * 根据md5查找文件
     *
     * @param md5 md5
     * @return entity.server.plus.easydo.FileStore
     * @author laoyu
     * @date 2022/7/16
     */
    FileStore getByMd5(String md5);
}
