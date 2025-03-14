package org.xiaoxingbomei.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.mysql.cj.CacheAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xiaoxingbomei.common.Enum.GlobalCodeEnum;
import org.xiaoxingbomei.common.entity.response.GlobalResponse;
import org.xiaoxingbomei.common.utils.Request_Utils;
import org.xiaoxingbomei.dao.localhost.AuthMapper;
import org.xiaoxingbomei.entity.SysRole;
import org.xiaoxingbomei.service.AuthService;

import java.util.*;
import java.util.stream.Collectors;

import static org.xiaoxingbomei.common.CacheConstant.CACHE_KEY_SEPARATOR;

/**
 *
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService
{
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthMapper authMapper;

    // ========================================================================


    @Override
    public GlobalResponse register(String paramString)
    {
        return null;
    }

    @Override
    public GlobalResponse login(String paramString)
    {
        // 1、解析前端参数
        String loginType = Request_Utils.getParam(paramString, "loginType");

        // 2、登录方式路由
        if(StringUtils.equals("1", loginType))  // 密码登录
        {
            GlobalResponse ret = loginByPassword(paramString);
            return ret;
        }

        if(StringUtils.equals("2", loginType))  // 验证码登录
        {
            loginByXxx(paramString);
        }

        if(StringUtils.equals("3",loginType))   // 单点登录
        {
            loginBySso(paramString);
        }

        return null;
    }

    // 登录-密码
    private GlobalResponse loginByPassword(String paramString)
    {
        // 1、接收前端参数
        String userId   = Request_Utils.getParam(paramString, "userId");
        String password = Request_Utils.getParam(paramString, "password");

        // 此处用代码替代真实情况下数据库比对操作
        if(StringUtils.equals("ysx",userId) & StringUtils.equals("ysx123",password))
        {
            StpUtil.login(userId);
            return GlobalResponse.success(GlobalCodeEnum.SUCCESS.getCode(), GlobalCodeEnum.SUCCESS.getMessage(),"登录成功","",null);
        }

        return GlobalResponse.error("登录失败");
    }

    // 登录-验证码
    private GlobalResponse loginByXxx(String paramString)
    {
        return null;
    }

    // 登录-单点登录
    private GlobalResponse loginBySso(String paramString)
    {
        return null;
    }


    @Override
    public GlobalResponse isLogin(String paramString)
    {
        boolean loginResult = StpUtil.isLogin();
        return GlobalResponse.success(loginResult);
    }

    @Override
    public GlobalResponse checkLogin(String paramString)
    {
        StpUtil.checkLogin();
        return GlobalResponse.success("已登录");
    }

    @Override
    public GlobalResponse logout(String paramString)
    {
        if(StpUtil.isLogin())
        {
            StpUtil.logout();
            return GlobalResponse.success("已登出");
        }
        return GlobalResponse.success("未登录，无需登出");
    }


    @Override
    public GlobalResponse getSaTokenInfo(String paramString)
    {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return GlobalResponse.success(tokenInfo,"获取sa-token信息");
    }

    @Override
    public GlobalResponse getLoginId(String paramString)
    {
        Object loginId = StpUtil.getLoginId();
        return GlobalResponse.success(loginId,"获取登录用户id");
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public GlobalResponse createRole(String paramString)
    {
        // 1、获取前端参数
        String roleKey     = Request_Utils.getParam(paramString, "roleKey");
        String roleName    = Request_Utils.getParam(paramString, "roleName");
        String description = Request_Utils.getParam(paramString, "description");
        String permissions = Request_Utils.getParam(paramString, "permissions");

        // 2、创建角色
        SysRole sysRole = new SysRole();
        sysRole.setRoleKey(roleKey);
        sysRole.setRoleName(roleName);
        sysRole.setDescription(description);

        authMapper.insertRole(sysRole);

        // 3、关联权限
        if(StringUtils.isNotEmpty(permissions))
        {
            List<String> permissionList = Arrays.asList(permissions.split(",", -1));
            // 去除多余空格(可选)
            permissionList = permissionList.stream().map(String::trim).collect(Collectors.toList());
            log.info("当前角色需要关联的权限为+{}",permissionList);
            authMapper.batchInsertRolePermissions(roleKey,permissionList);
        }

        // 4、清理缓存（可选）

        // 5、封装结果
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("roleKey", roleKey);
        resultMap.put("roleName",roleName);
        resultMap.put("permissions",permissions);

        return GlobalResponse.success(resultMap,"新增角色成功");
    }

    @Override
    public List<String> getRoleList(String loginId)
    {
        return StpUtil.getRoleList(loginId);
    }

    @Override
    public List<String> getRoleListByStore(String paramString)
    {
        // 1、获取前端参数
        String loginId = Request_Utils.getParam(paramString, "loginId");

        // 2、通过缓存获取角色列表

        // 3、通过数据库获取角色列表
        List<SysRole> sysRoles = authMapper.selectRolesByUserId(loginId);
        log.info("角色列表是啥："+sysRoles.toString());
        List<String> rolekeyList = sysRoles.stream().map(SysRole::getRoleKey).collect(Collectors.toList());

        // 4、更新缓存（可选）


        // 5、封装返回
        return rolekeyList;
    }



    @Override
    public GlobalResponse assignUserRoles(String paramString)
    {
        return null;
    }


    @Override
    public GlobalResponse getPermissionList(String loginId)
    {
        // 1、通过缓存获取权限列表

        // 2、通过数据库获取权限列表

        // 3、存入缓存

        // 4、封装返回
        List<String> permissionList = StpUtil.getPermissionList(loginId);
        return GlobalResponse.success(permissionList,loginId);
    }

}
