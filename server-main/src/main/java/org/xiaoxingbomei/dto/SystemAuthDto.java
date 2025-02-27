package org.xiaoxingbomei.dto;

import com.alibaba.fastjson.JSON;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.xiaoxingbomei.Enum.GlobalCodeEnum;
import org.xiaoxingbomei.exception.BusinessException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SystemAuthDto
{
    private String userId;      // 用户id
    private String userName;    // 用户名
    private String orgId;       // 机构id
    private String orgName;     // 机构名
    private String supOrgId;    // 上级机构id
    private String supOrgName;  // 上级机构名
    private String roleId;      // 角色id
    private String auth;        // 权限
    private String token;       // 令牌

    // 初始化
    public static SystemAuthDto initSystemAuthDto(String paramString)
    {
        SystemAuthDto dto = null;
        if(!StringUtils.isEmpty(paramString))
        {
            dto = JSON.parseObject(paramString,SystemAuthDto.class);
        }
        return dto;
    }

    // 通用入参校验
    public static void checkCommonParams(String paramString)
    {
        if(StringUtils.isEmpty(paramString))
        {
            throw new BusinessException(GlobalCodeEnum.ERROR.getCode(),"缺少必要参数");
        }
    }


}