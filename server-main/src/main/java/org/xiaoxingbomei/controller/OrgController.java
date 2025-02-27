package org.xiaoxingbomei.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.constant.ApiConstant;
import org.xiaoxingbomei.controller.request.GetOrgInfoDto;
import org.xiaoxingbomei.service.OrgService;

import javax.validation.Valid;


@RestController
@Tag(name="机构controller",description = "机构controller")
public class OrgController
{
    @Autowired
    public OrgService orgService;

    @Operation(summary = "获取全部机构信息",description = "无条件，获取全部机构信息")
    @RequestMapping(value = ApiConstant.Org.getOrgInfo, method = RequestMethod.POST)
    public GlobalEntity getAllOrgInfo(@RequestBody String paramString)
    {
        GlobalEntity ret = null;

        ret = orgService.getAllOrgInfo();

        return ret;
    }

    @Operation(summary = "获取全部机构信息",description = "通过orgId，获取全部机构信息")
    @RequestMapping(value = ApiConstant.Org.getOrgInfoByOrgId,method = RequestMethod.POST)
    public GlobalEntity getAllOrgInfoByOrgId(@RequestBody @Valid GetOrgInfoDto dto)
    {
        GlobalEntity ret = null;

        ret = orgService.getAllOrgInfoByOrgId(dto);

        return ret;
    }

}
