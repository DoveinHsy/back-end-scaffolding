package org.xiaoxingbomei.config.minio;

import io.minio.MinioClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.xiaoxingbomei.utils.Exception_Utils;

import javax.annotation.PostConstruct;

/**
 * minio客户端配置
 */
@Configuration
@Slf4j
@Data
public class MinioConfig
{
    @Value("${apollo.minio.endpoint}")
    private String minioEndpoint;

    @Value("${apollo.minio.accessKey}")
    private String minioAccessKey;

    @Value("${apollo.minio.secretKey}")
    private String minioSecretKey;

    // @Autowired
    // private MinioClient minioClient;

    // 创建minio连接
    @Bean
    public MinioClient createMinioClient()
    {
        log.info("=========================Create Minio Client start===================");
        log.info("minioEndpoint: {}", minioEndpoint);
        log.info("minioAccessKey: {}", minioAccessKey);
        log.info("miniosecretKey: {}", minioSecretKey);
        return MinioClient.builder()
                .endpoint(minioEndpoint)
                .credentials(minioAccessKey,minioSecretKey)
                .build();
    }

    @PostConstruct
    public void minioInit()
    {
        try
        {
            MinioClient client = createMinioClient();
            boolean minioConnected = client.listBuckets().size() >=0;
            log.info("minioConnected: {}", minioConnected);
        }catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        }
    }

}
