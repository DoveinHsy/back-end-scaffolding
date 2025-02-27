package org.xiaoxingbomei.constant;

import org.springframework.beans.factory.annotation.Value;


public class OssClientConstants
{


    //阿里云API的外网域名
    @Value("${syst.aliyun.endpoint}")
    public String ENDPOINT;
    //阿里云API的密钥Access Key ID
    @Value("${syst.aliyun.accessKeyId}")
    public String ACCESS_KEY_ID;
    //阿里云API的密钥Access Key Secret
    @Value("${syst.aliyun.accessKeySecret}")
    public String ACCESS_KEY_SECRET;
    //阿里云API的bucket名称
    @Value("${syst.aliyun.backetName}")
    public String BACKET_NAME;
    @Value("${syst.aliyun.prefix}")
    public String PREFIX;



    // @PostConstruct
    // public void initParm()
    // {
    //     AliyunOssUtil.ENDPOINT = ENDPOINT;
    //     AliyunOssUtil.ACCESS_KEY_ID = ACCESS_KEY_ID;
    //     AliyunOssUtil.ACCESS_KEY_SECRET = ACCESS_KEY_SECRET;
    //     AliyunOssUtil.BACKET_NAME = BACKET_NAME;
    //     AliyunOssUtil.PREFIX = PREFIX;
    // }


}
