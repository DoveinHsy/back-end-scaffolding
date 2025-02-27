package org.xiaoxingbomei.service.implement;

import cn.idev.excel.FastExcel;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.common.utils.Request_Utils;
import org.xiaoxingbomei.config.minio.MinioConfig;
import org.xiaoxingbomei.entity.KnowledgeBase;
import org.xiaoxingbomei.service.WikiService;
import org.xiaoxingbomei.utils.Exception_Utils;
import org.xiaoxingbomei.vo.BusinessLogCommon;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WikiServiceImpl implements WikiService
{

    @Autowired
    private MinioConfig minioConfig;

    // ===========================================================================

    @Override
    public GlobalEntity mine_knowledgeBases_search(String paramString)
    {
        return null;
    }

    @Override
    public GlobalEntity mine_knowledgeBase_search(String paramString)
    {
        return null;
    }

    @Override
    public GlobalEntity mine_knowledgeBase_create(String paramString)
    {
        return null;
    }

    /**
     * 场景：
     * 1、创建minio链接获取excel文件
     * 2、校验excel文件
     * 3、异步落库
     * 4、同步es
     */
    @Override
    public GlobalEntity mine_knowledgeBase_multiCreate(String paramString)
    {
        // 1、接收前端参数
        String dataSource = Request_Utils.getParam(paramString, "dataSource");
        String createUser = Request_Utils.getParam(paramString, "createUser");
        String bucketName = Request_Utils.getParam(paramString, "bucketName");
        String objectName = Request_Utils.getParam(paramString, "objectName");

        List<KnowledgeBase> knowledgeFile = null;
        String errorMessage = "";

        // 2、创建minio连接获取excel文件

        // 初始化minio客户端
        MinioClient minioClient = minioConfig.createMinioClient();

        try
        {
            // 从minio获取文件流
            InputStream minioInputStream = minioClient.getObject
                    (
                            GetObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(objectName)
                                    .build()
                    );

            // 使用fastexcel解析流
            knowledgeFile = FastExcel.read(minioInputStream)
                    .head(KnowledgeBase.class)  // 映射到对应类
                    .sheet()                    // 默认读取第一个sheet
                    .doReadSync();              // 同步读取
            log.info("knowledgeFile:{}", knowledgeFile);
            // 关闭流
            minioInputStream.close();
        } catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error(":{}"+e.getMessage());
        }

        // 3、校验excel文件

        // 4、异步落库

        // 5、同步es

        // 6、创建结果体
        HashMap<String, Object> resultMap = new HashMap<>();

        return GlobalEntity.success(resultMap,"批量创建知识库成功");
    }





}
