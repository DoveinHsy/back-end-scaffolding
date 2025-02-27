package org.xiaoxingbomei.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(name = "GetOrgInfoDto",description = "查询机构信息的dto")
@Data
public class GetOrgInfoDto
{

    @Schema(description = "主键 机构id")
    @NotNull
    private String orgId;

    @Schema(description = "机构名称")
    private String orgName;

    @Schema(description = "父机构id")
    private String parentId;

    @Schema(description = "机构层级")
    private String orgLevel;

    @Schema(description = "机构层级路径")
    private String orgPath;

    @Schema(description = "是否为数据机构")
    private String isDOrg;

    @Schema(description = "是否为xx机构")
    private String isVOrg;

    @Schema(description = "数据机构id")
    private String dataOrgId;

    @Schema(description = "rl父亲机构id")
    private String rlParentID;

    @Schema(description = "备注1")
    private String remark1;

    @Schema(description = "备注2")
    private String remark2;
}
