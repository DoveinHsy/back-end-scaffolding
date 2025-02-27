package org.xiaoxingbomei.dao.localhost;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.xiaoxingbomei.vo.BusinessLogCommon;
import org.xiaoxingbomei.vo.SystemLogCommon;

import java.util.List;

/**
 * 日志操作 mapper
 */
@Mapper
public interface LogMapper
{
    int insertBusinessLogCommon(BusinessLogCommon businessLogCommon);

    int insertBusinessLogCommonList(@Param("logs") List<BusinessLogCommon> businessLogCommon);

    int insertSystemLogCommon(SystemLogCommon systemLogCommon);
}
