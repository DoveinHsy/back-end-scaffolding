package org.xiaoxingbomei.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.system.UserInfo;
import com.mysql.cj.CacheAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xiaoxingbomei.common.Enum.GlobalCodeEnum;
import org.xiaoxingbomei.common.entity.LoginInfo;
import org.xiaoxingbomei.common.entity.response.GlobalResponse;
import org.xiaoxingbomei.common.utils.Request_Utils;
import org.xiaoxingbomei.dao.localhost.AuthMapper;
import org.xiaoxingbomei.entity.SysPermission;
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

    @Autowired
    private AuthService authService;

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
        String userId   = Request_Utils.getParam(paramString, "loginId");
        String password = Request_Utils.getParam(paramString, "password");

        // 此处用代码替代真实情况下数据库比对操作
        if(StringUtils.equals("ysx",userId) & StringUtils.equals("ysx123",password))
        {
            StpUtil.login(userId);
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setLoginId(userId);
            loginInfo.setPassword(password);
//            loginInfo.setRoleList((List<String>) authService.getRole(paramString).getData());
            StpUtil.getSession().set(userId,loginInfo);
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
    public GlobalResponse getRole(String paramString)
    {
        // 1、获取前端参数
        String userId = Request_Utils.getParam(paramString, "LoginId");

        // 2、如果是获取指定用户的角色信息
        if(StringUtils.isNotEmpty(userId))
        {
            List<SysRole> roleList = authMapper.getRolesByUserId(userId);
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("userId",userId);
            resultMap.put("roleList",roleList);
            return GlobalResponse.success(resultMap,"用户的所有角色");
        }

        // 3、如果是获取全部角色信息
        List<SysRole> allRoles = authMapper.getAllRoles();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("roleList",allRoles);

        return GlobalResponse.success(resultMap,"全部的角色");
    }

    @Override
    public GlobalResponse updateRole(String paramString)
    {
        return null;
    }

    @Override
    public GlobalResponse deleteRole(String paramString)
    {
        return null;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public GlobalResponse assignUserRoles(String paramString)
    {
        // 1、获取前端参数
        String loginId = Request_Utils.getParam(paramString, "loginId");
        String roles   = Request_Utils.getParam(paramString, "roles");
        if(StringUtils.isEmpty(roles) || StringUtils.isEmpty(loginId))
        {
            return GlobalResponse.error("用户名或者角色信息不能为空");
        }

        // 2、删除用户现有的角色关联
        authMapper.deleteUserRoles(loginId);

        // 3、建立新的角色关联
        List<String> roleList = Arrays.asList(roles.split(",", -1));
        roleList = roleList.stream().map(String::trim).collect(Collectors.toList()); // 去除多余空格
        log.info("为用户{}分配角色{}",loginId,roleList);
        authMapper.insertUserRoles(loginId,roleList);

        // 4、清理缓存（如果有的话）

        // 5、如果用户已经登录，更新会话中的角色信息
        if(StpUtil.isLogin(loginId))
        {
            LoginInfo loginInfo = (LoginInfo)StpUtil.getSessionByLoginId(loginId).get(loginId);
            if(loginInfo!=null)
            {
                // 更新登录信息中的角色列表
                loginInfo.setRoleList(roleList);
                StpUtil.getSession().set(loginId,loginInfo);
                log.info("更新用户{}的会话角色信息{}",loginId,roleList);
            }
        }

        // 6、封装结果
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId",   loginId);
        resultMap.put("roleList", roleList);
        return GlobalResponse.success(resultMap,"用户角色分配成功");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public GlobalResponse createPermission(String paramString)
    {
        // 1、获取前端参数
        String permissionKey    = Request_Utils.getParam(paramString, "permissionKey");
        String permissionName   = Request_Utils.getParam(paramString, "permissionName");
        String parentId         = Request_Utils.getParam(paramString, "parentId");
        String description      = Request_Utils.getParam(paramString, "description");
        String permissionType   = Request_Utils.getParam(paramString, "permissionType");

        if(StringUtils.isEmpty(permissionKey)|| StringUtils.isEmpty(permissionName))
        {
            return GlobalResponse.error("权限名称或标识不能为空");
        }

        // 2、创建权限
        SysPermission sysPermission = new SysPermission();
        sysPermission.setPermissionKey(permissionKey);
        sysPermission.setPermissionName(permissionName);
        sysPermission.setPermissionType(permissionType);
        sysPermission.setDescription(description);
        sysPermission.setStatus("1");

        authMapper.insertPermission(sysPermission);

        // 3、封装结果
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("permissionKey", permissionKey);
        resultMap.put("permissionName", permissionName);
        resultMap.put("parentId", parentId);
        resultMap.put("description", description);

        return GlobalResponse.success(resultMap,"新增权限成功");
    }

    @Override
    public GlobalResponse getAllPermission(String paramString)
    {
        // 1、查询全部权限
        List<SysPermission> allPermission = authMapper.getAllPermission();

        // 2、封装结果
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("allPermission",allPermission);

        return GlobalResponse.success(resultMap,"获取全部权限成功");
    }

    @Override
    public GlobalResponse assignRolePermissions(String paramString)
    {
        // 1、获取前端参数
        String roleKey      = Request_Utils.getParam(paramString, "roleKey");
        String permissions  = Request_Utils.getParam(paramString, "permissions");

        List<String> permissionList = null;

        if(StringUtils.isEmpty(roleKey))
        {
            return GlobalResponse.error("角色标识不能为空");
        }

        // 2、删除角色现有权限关联
        authMapper.deleteRoleAllPermissions(roleKey);

        // 3、建立新的权限关联
        if(StringUtils.isNotEmpty(permissions))
        {
            permissionList = Arrays.asList(permissions.split(",", -1));
            permissionList = permissionList.stream().map(String::trim).collect(Collectors.toList());
            log.info("为角色【{}】分配权限【{}】",roleKey,permissionList);
            // 批量插入角色权限关联
            authMapper.batchInsertRolePermissions(roleKey,permissionList);
        }

        // 4、更新缓存或者会话存储（重要）

        // 5、封装结果
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("roleKey", roleKey);
        resultMap.put("permissions", permissionList);
        return GlobalResponse.success(resultMap);
    }

    @Override
    public GlobalResponse getRolePermissions(String paramString)
    {
        return null;
    }

    @Override
    public GlobalResponse getUserPermissions(String paramString)
    {
        return null;
    }
}
