package plus.easydo.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import plus.easydo.server.entity.FileStore;
import plus.easydo.server.service.FileStoreService;
import plus.easydo.server.mapper.FileStoreMapper;
import org.springframework.stereotype.Service;

/**
* @author yuzhanfeng
* @description 针对表【file_store】的数据库操作Service实现
* @createDate 2022-07-14 19:36:26
*/
@Service
public class FileStoreServiceImpl extends ServiceImpl<FileStoreMapper, FileStore>
    implements FileStoreService{

    @Override
    public FileStore getByMd5(String md5) {
        LambdaQueryWrapper<FileStore> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FileStore::getMd5,md5);
        return getOne(wrapper);
    }
}




