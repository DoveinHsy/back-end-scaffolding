package org.xiaoxingbomei.config.fastexcel;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.xiaoxingbomei.entity.UploadData;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UploadDataListener extends AnalysisEventListener<UploadData>
{
    private final List<UploadData> list = new ArrayList<>();

    @Override
    public void invoke(UploadData uploadData, AnalysisContext analysisContext)
    {
        log.info("解析到一条数据:{}", JSON.toJSONString(uploadData));
        list.add(uploadData);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext)
    {
        log.info("所有数据解析完成");
        // 这里可以进行数据的存储操作，比如保存到数据库
    }

}
