package org.xiaoxingbomei.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String userId;

    @Schema(description = "名字")
    private String name;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "证件号码")
    private String idCard;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "电话号码")
    private String phoneNumber;

    @Schema(description = "手机号码")
    private String mobileNumber;

    @Schema(description = "地址")
//    @Desensitization(type = DesensitizationTypeEnum.CUSTOM_RULE,start = 0,end = 3)
    private String address;

    @Schema(description = "国籍")
    private String nationality;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "省份")
    private String state;

    @Schema(description = "市")
    private String city;

    @Schema(description = "银行卡")
    private String bankCard;

    @Schema(description = "职业")
    private String occupation;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "婚姻状况 1-未婚 2-已婚 3-未知")
    private String maritalStatus;


    /**
     * 扩展字段
     */
    @Schema(description = "用户类型:1-内部用户 2-对私客户 3-对公客户")
    private String userType;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;

    @Schema(description = "状态:0-失效 1-有效")
    private String status;






}
