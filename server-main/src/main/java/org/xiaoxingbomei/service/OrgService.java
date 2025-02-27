package org.xiaoxingbomei.service;

import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.controller.request.GetOrgInfoDto;

public interface OrgService
{

    GlobalEntity getAllOrgInfo();

    GlobalEntity getAllOrgInfoByOrgId(GetOrgInfoDto dto);

}
