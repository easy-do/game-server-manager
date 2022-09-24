package game.server.manager.oss.minio;

import game.server.manager.oss.exception.OssStoreException;
import game.server.manager.oss.minio.config.MinioProperties;
import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author laoyu
 * @version 1.0
 */

@Data
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(MinioProperties.class)
public class MinioTemplate {


    @Autowired
    private MinioClient client;

    @Autowired
    private MinioProperties properties;


    public String getDefaultBucket(){
        return properties.getDefaultBucket();
    }

    /**
     * 检查文件存储桶是否存在
     *
     * @param bucketName bucketName
     * @return boolean
     * @author laoyu
     */
    @SneakyThrows
    public boolean bucketExists(String bucketName){
        return client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建bucket
     *
     * @param bucketName bucketName
     * @author laoyu
     */
    @SneakyThrows
    public void createBucket(String bucketName) {
        if (!bucketExists(bucketName)) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 获取全部bucket
     *
     * @return java.util.List
     * @author laoyu
     * https://docs.minio.io/cn/java-client-api-reference.html#listBuckets
     */
    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        return client.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     * @return java.util.Optional
     * @author laoyu
     */
    @SneakyThrows
    public Optional<Bucket> getBucket(String bucketName) {
        return client.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void removeBucket(String bucketName) {
        client.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 根据文件前缀查询文件
     *
     * @param bucketName bucket名称
     * @param prefix     前缀
     * @param recursive  是否递归查询
     * @return MinioItem 列表
     */
    @SneakyThrows
    public List<MinioItem> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
        List<MinioItem> objectList = new ArrayList<>();
        ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build();
        Iterable<Result<Item>> objectsIterator = client.listObjects(listObjectsArgs);
        for (Result<Item> result : objectsIterator) {
            objectList.add(new MinioItem(result.get()));
        }
        return objectList;
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 小于等于7
     * @return url
     */
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName).object(objectName).expiry(expires).build();
        return client.getPresignedObjectUrl(getPresignedObjectUrlArgs);
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return url
     */
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName) {
        GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName).object(objectName).build();
        return client.getPresignedObjectUrl(getPresignedObjectUrlArgs);
    }

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(bucketName).object(objectName).build();
        return client.getObject(getObjectArgs);
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream) throws Exception {
        PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream,-1,-1)
                .contentType("application/octet-stream").build();
        client.putObject(putObjectArgs);
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        大小
     * @param contextType 类型
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream, long size, String contextType) throws Exception {
        PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream,size,-1)
                .contentType(contextType).build();
        client.putObject(putObjectArgs);
    }

    /**
     * 获取文件信息
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return io.minio.ObjectStat
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
     */
    public StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
        StatObjectArgs statObjectArgs = StatObjectArgs.builder().bucket(bucketName).object(objectName).build();
        return client.statObject(statObjectArgs);
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     *  https://docs.minio.io/cn/java-client-api-reference.html#removeObject
     */
    public void removeObject(String bucketName, String objectName){
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build();
            client.removeObject(removeObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OssStoreException("删除文件发生异常");
        }
    }

    /**
     * 复制文件
     *
     * @param bucketName bucketName
     * @param name name
     * @param targetBucketName targetBucketName
     * @param targetName targetName
     * @author laoyu
     */
    public void copy(String bucketName, String name, String targetBucketName, String targetName){
        try {
            CopySource copySource = CopySource.builder().bucket(bucketName).object(name).build();
            CopyObjectArgs copyObjectArgs = CopyObjectArgs.builder().source(copySource).bucket(targetBucketName).object(targetName).build();
            client.copyObject(copyObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OssStoreException("复制文件发生异常");
        }
    }


    /**
     * 移动文件
     *
     * @param bucketName bucketName
     * @param name name
     * @param targetBucketName targetBucketName
     * @param targetName targetName
     * @author laoyu
     */
    public void move(String bucketName, String name, String targetBucketName, String targetName){
        try {
            CopySource copySource = CopySource.builder().bucket(bucketName).object(name).build();
            CopyObjectArgs copyObjectArgs = CopyObjectArgs.builder().source(copySource).bucket(targetBucketName).object(targetName).build();
            client.copyObject(copyObjectArgs);
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucketName).object(name).build();
            client.removeObject(removeObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OssStoreException("移动文件发生异常");
        }
    }

}
