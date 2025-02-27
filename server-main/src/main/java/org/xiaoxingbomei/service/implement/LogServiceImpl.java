package org.xiaoxingbomei.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiaoxingbomei.Enum.GlobalCodeEnum;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.dao.localhost.LogMapper;
import org.xiaoxingbomei.service.LogService;
import org.xiaoxingbomei.vo.BusinessLogCommon;
import org.xiaoxingbomei.vo.SystemLogCommon;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService
{

    @Autowired
    private LogMapper logMapper;

    @Override
    public GlobalEntity insertBusinessLogCommon(BusinessLogCommon businessLogCommon)
    {

        int i = logMapper.insertBusinessLogCommon(businessLogCommon);
        return GlobalEntity.success(i, GlobalCodeEnum.SUCCESS.getCode(), GlobalCodeEnum.SUCCESS.getMessage(), "插入通用业务日志成功","插入通用业务日志成功");
    }

    @Override
    public GlobalEntity insertBusinessLogCommonList(List<BusinessLogCommon> businessLogCommonList)
    {
        businessLogCommonList = businessLogCommonList.stream()
                .peek(businessLogCommon -> businessLogCommon.setId(UUID.randomUUID().toString()))
                .collect(Collectors.toList());
        int i = logMapper.insertBusinessLogCommonList(businessLogCommonList);
        return GlobalEntity.success(i, GlobalCodeEnum.SUCCESS.getCode(), GlobalCodeEnum.SUCCESS.getMessage(), "批量插入通用业务日志成功","插入通用业务日志成功");
    }

    @Override
    public GlobalEntity insertSystemLogCommon(SystemLogCommon message)
    {
        return null;
    }
}
