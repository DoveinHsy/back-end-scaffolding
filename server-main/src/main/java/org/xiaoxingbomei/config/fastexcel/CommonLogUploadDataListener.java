package org.xiaoxingbomei.config.fastexcel;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.xiaoxingbomei.entity.UploadData;
import org.xiaoxingbomei.service.LogService;
import org.xiaoxingbomei.vo.BusinessLogCommon;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CommonLogUploadDataListener extends AnalysisEventListener<BusinessLogCommon>
{
    private final List<BusinessLogCommon> list = new ArrayList<>();

    private static final int BATCH_COUNT = 100;

    @Autowired
    private LogService logService;


    @Override
    public void invoke(BusinessLogCommon uploadData, AnalysisContext analysisContext)
    {
        log.info("解析到一条数据:{}", JSON.toJSONString(uploadData));
        list.add(uploadData);
        if(list.size() >= BATCH_COUNT)
        {
            saveData();
            list.clear(); // 清空缓存，继续收集下一批数据
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext)
    {
        log.info("所有数据解析完成");
        // 这里可以进行数据的存储操作，比如保存到数据库

        // 所有数据读取完成后，再存储剩余数据
        saveData();
    }

    private void saveData()
    {
        log.info("【{}】条通用日志数据落库开始",list.size());
        logService.insertBusinessLogCommonList(list);
        log.info("【{}】条通用日志数据落库结束",list.size());

    }

}
