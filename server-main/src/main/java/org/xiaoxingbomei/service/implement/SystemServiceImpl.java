package org.xiaoxingbomei.service.implement;

import cn.idev.excel.FastExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.common.entity.SystemEntity;
import org.xiaoxingbomei.service.ServerEsFeignClient;
import org.xiaoxingbomei.service.SystemService;
import org.xiaoxingbomei.utils.Exception_Utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
@Slf4j
public class SystemServiceImpl implements SystemService
{

    @Autowired
    ServerEsFeignClient esFeignClient;

    // =================================================================

    @Override
    public GlobalEntity getSystemInfo(String paramString)
    {
        GlobalEntity<List<SystemEntity>> systemInfo = esFeignClient.getSystemInfo(paramString);

        List<SystemEntity> data = systemInfo.getData();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("systemInfo", data);
        return GlobalEntity.success(resultMap,"systemInfo");
    }


    @Override
    public GlobalEntity multiHandleSystem(MultipartFile file)
    {
        // 1、接收前端参数

        List<SystemEntity> systemEntities = new ArrayList<>();
        // 2、使用multipartfile接收excel文件,并使用fastexcel读取excel
        try
        {
            systemEntities = parseExcelToSystems(file);
            log.info("读取excel成功，systemEntities:{}", systemEntities.toString());
        } catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            log.info("使用multipartfile接收excel文件失败:{}",e.getMessage());
        }

        // 3、使用minio接收excel文件,并使用fastexcel读取excel(可选)

        // 4、查找数据库中已存在的系统

        // 5、分割出需要插入和更新的系统数据

        // 6、批量插入和更新数据库

        // 7、同步数据到 Elasticsearch（批量插入或更新）

        return null;
    }

    // 使用 FastExcel 解析上传的excel文件
    private List<SystemEntity> parseExcelToSystems(MultipartFile file) throws Exception
    {
        // 创建InputStream对象读取MultipartFile内容
        try (InputStream inputStream = file.getInputStream())
        {
            // 使用fastexcel将excel转换为List<SystemEntity>
            return FastExcel.read(inputStream)
                    .head(SystemEntity.class)
                    .sheet()
                    .doReadSync();
        }
    }

    // 使用FastExcel 接收minio的excel文件


}
