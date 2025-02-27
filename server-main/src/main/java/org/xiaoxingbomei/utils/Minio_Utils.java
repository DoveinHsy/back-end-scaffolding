package org.xiaoxingbomei.utils;

import io.minio.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * minio的工具类
 */
@Component
@Slf4j
public class Minio_Utils
{



    /**
     * 判断指定的桶是否存在
     */
    public boolean isBucketExist(MinioClient minioClient,String bucketName)
    {
        try
        {
            Iterable<io.minio.messages.Bucket> buckets = minioClient.listBuckets();
            return StreamSupport.stream(buckets.spliterator(), false)
                    .anyMatch(bucket -> bucket.name().equals(bucketName));
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断桶是否为空
     */
    private boolean isBucketEmpty(MinioClient minioClient,String bucketName)
    {
        Iterable<Result<Item>> objects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        return streamFromIterable(objects).count() == 0;
    }

    /**
     * 判断指定桶中是否存在指定对象
     */
    public boolean doesObjectExist(MinioClient minioClient,String bucketName, String objectName)
    {
        try
        {
            Iterable<Result<Item>> objects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
            Stream<Item> objectStream = streamFromIterable(objects);
            return objectStream.anyMatch(item -> item.objectName().equals(objectName));
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将 Iterable<Result<Item>> 转换为 Stream<Item>
     */
    private Stream<Item> streamFromIterable(Iterable<Result<Item>> iterable)
    {
        Iterator<Result<Item>> iterator = iterable.iterator();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false)
                .map(result ->
                {
                    try {
                        return result.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(item -> item != null);
    }

    /**
     * 列出指定存储桶中的全部对象
     */
    public List<String> listObjectsInBucket(MinioClient minioClient,String bucketName)
    {
        List<String> objectNames = new ArrayList<String>();
        try
        {
            Iterable<Result<Item>> results = minioClient.listObjects
                    (
                            ListObjectsArgs.builder().bucket(bucketName).build()
                    );
            for (Result<Item> result : results)
            {
                Item item = result.get();
                objectNames.add(item.objectName());
            }

        }catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        }
        return objectNames;
    }

    /**
     * 删除指定桶
     * 如果桶非空，则先删除桶内的所有对象，再删除桶
     */
    public void deleteBucket(MinioClient minioClient,String bucketName)
    {
        try
        {
            // 检查桶是否为空
            if (isBucketExist(minioClient,bucketName))
            {
                if (!isBucketEmpty(minioClient,bucketName))
                {
                    // 删除桶中所有对象
                    deleteObjectsInBucket(minioClient,bucketName);
                }
                // 删除桶
                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
                log.info("Bucket " + bucketName + " deleted successfully.");
            } else {
                log.info("Bucket " + bucketName + " does not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定桶中的所有对象
     */
    private void deleteObjectsInBucket(MinioClient minioClient,String bucketName)
    {
        try
        {
            Iterable<Result<Item>> objects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
            streamFromIterable(objects).forEach(item ->
            {
                try
                {
                    minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(item.objectName()).build());
                    log.info("Deleted object: " + item.objectName());

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定桶中的某个对象
     */
    public void deleteObject(MinioClient minioClient,String bucketName, String objectName)
    {
        try {
            if (doesObjectExist(minioClient,bucketName, objectName))
            {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
                log.info("Object " + objectName + " deleted from bucket " + bucketName + " successfully.");
            } else 
            {
                log.info("Object " + objectName + " does not exist in bucket " + bucketName + ".");
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }


}
