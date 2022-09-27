package game.server.manager.oss.minio;

import game.server.manager.common.exception.OssException;
import game.server.manager.oss.minio.config.MinioProperties;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    public boolean bucketExists(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建bucket
     *
     * @param bucketName bucketName
     * @author laoyu
     */
    public void createBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
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
    public List<Bucket> getAllBuckets() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return client.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     * @return java.util.Optional
     * @author laoyu
     */
    public Optional<Bucket> getBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return client.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     * @param bucketName bucket名称
     */
    public void removeBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
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
    public List<MinioItem> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
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
    public String getObjectUrl(String bucketName, String objectName, Integer expires) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
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
    public String getObjectUrl(String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
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
    public InputStream getObject(String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
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
            throw new OssException("删除文件发生异常");
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
            throw new OssException("复制文件发生异常");
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
            throw new OssException("移动文件发生异常");
        }
    }

}
