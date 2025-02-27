package org.xiaoxingbomei.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiaoxingbomei.Enum.GlobalCodeEnum;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.controller.request.GetOrgInfoDto;
import org.xiaoxingbomei.dao.localhost.OrgMapper;
import org.xiaoxingbomei.service.OrgService;

import java.util.List;
import java.util.Map;


@Service
public class OrgServiceImpl implements OrgService
{

    @Autowired
    private OrgMapper orgMapper;


    @Override
    public GlobalEntity getAllOrgInfo()
    {

        List<Map<String, String>> allOrgInfo = orgMapper.getAllOrgInfo();

        return GlobalEntity.success
                (
                        allOrgInfo,
                        GlobalCodeEnum.SUCCESS.getCode(),
                        GlobalCodeEnum.SUCCESS.getMessage(),
                        "获取全部机构信息",
                        "获取全部机构信息"
                );
    }

    @Override
    public GlobalEntity getAllOrgInfoByOrgId(GetOrgInfoDto dto)
    {
        String orgId = dto.getOrgId();

        List<Map<String, String>> allOrgInfoByOrdId = orgMapper.getAllOrgInfoByOrdId(orgId);

        return GlobalEntity.success
                (
                        allOrgInfoByOrdId,
                        GlobalCodeEnum.SUCCESS.getCode(),
                        GlobalCodeEnum.SUCCESS.getMessage(),
                        "获取指定机构信息",
                        "获取指定机构信息"
                );

    }
}
