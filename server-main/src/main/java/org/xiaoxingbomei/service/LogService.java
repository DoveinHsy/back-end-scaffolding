package org.xiaoxingbomei.service;

import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.vo.BusinessLogCommon;
import org.xiaoxingbomei.vo.SystemLogCommon;

import java.util.List;

public interface LogService
{
    public GlobalEntity insertBusinessLogCommon(BusinessLogCommon message);

    public GlobalEntity insertBusinessLogCommonList(List<BusinessLogCommon> message);

    public GlobalEntity insertSystemLogCommon(SystemLogCommon message);
}
