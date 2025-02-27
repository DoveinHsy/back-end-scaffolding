package org.xiaoxingbomei.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Schema(name = "BusinessLogCommon",description = "对系统操作日志进行落库用的实体")
@Getter
@Setter
public class BusinessLogCommon implements Serializable
{
    private static final long serialVersionUID = 1L;

    @ExcelProperty("id")
    @Schema(description = "主键")
    private String id;

    @ExcelProperty("userId")
    @Schema(description = "用户id")
    private String userId;

    @ExcelProperty("userName")
    @Schema(description = "用户名称")
    private String userName;

    @ExcelProperty("roleId")
    @Schema(description = "角色id")
    private String roleId;

    @ExcelProperty("roleName")
    @Schema(description = "角色名")
    private String roleName;

    @ExcelProperty("orgId")
    @Schema(description = "机构id")
    private String orgId;

    @ExcelProperty("orgName")
    @Schema(description = "机构名")
    private String orgName;

    @ExcelProperty("channelId")
    @Schema(description = "渠道id")
    private String channelId;

    @ExcelProperty("channelName")
    @Schema(description = "渠道名")
    private String channelName;

    @ExcelProperty("deviceId")
    @Schema(description = "设备id")
    private String deviceId;

    @ExcelProperty("deviceName")
    @Schema(description = "设备名")
    private String deviceName;

    @ExcelProperty("pageId")
    @Schema(description = "页面id")
    private String pageId;

    @ExcelProperty("pageName")
    @Schema(description = "页面名")
    private String pageName;

    @ExcelProperty("moduleId")
    @Schema(description = "模块id")
    private String moduleId;

    @ExcelProperty("moduleName")
    @Schema(description = "模块名")
    private String moduleName;

    @ExcelProperty("operateTypeId")
    @Schema(description = "操作类型id")
    private String operateTypeId;

    @ExcelProperty("operateTypeName")
    @Schema(description = "操作类型名")
    private String operateTypeName;

    @ExcelProperty("operateTime")
    @Schema(description = "操作时间")
    private String operateTime;

    @ExcelProperty("content")
    @Schema(description = "备注")
    private String content;


    // 初始化
    public static BusinessLogCommon initBusinessLogCommon(BusinessLogCommon businessLogCommon)
    {
        businessLogCommon.setId(UUID.randomUUID().toString());
        return businessLogCommon;
    }


}
