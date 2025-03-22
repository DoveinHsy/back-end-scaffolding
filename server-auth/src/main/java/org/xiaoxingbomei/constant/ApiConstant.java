package org.xiaoxingbomei.constant;

public class ApiConstant
{

    /**
     * satoken
     */
    public class Auth
    {
        public static final String login             = "/login";             // 登录入口（密码登录、验证码、单点登录）
        public static final String isLogin           = "/isLogin";           // 是否登录 boolean
        public static final String checkLogin        = "/checkLogin";        // 检测登录，未登录抛出异常
        public static final String logout            = "/logout";            // 登出
        public static final String getSaTokenInfo    = "/getSaTokenInfo";    // 获取sa-token的信息
        public static final String getLoginId        = "/getLoginId";        // 获取当前登录用户id

        public static final String createRole         = "/createRole";        // 创建角色（单个）
        public static final String getRole            = "/getRole";           // 查询角色(指定或全部)
        public static final String updateRole         = "/updateRole";        // 修改角色（单个）
        public static final String deleteRole         = "/deleteRole";        // 删除角色（单个）
        public static final String assignUserRoles    = "/assignUserRoles";   // 授予用户角色

        public static final String createPermission       = "/createPermission";      // 创建权限
        public static final String getAllPermission       = "/getAllPermission";      // 获取全部的权限
        public static final String assignRolePermissions  = "/assignRolePermissions"; // 给角色授予权限
        public static final String getRolePermissions     = "/getRolePermissions";    // 获取角色所拥有的权限
        public static final String getUserPermissions     = "/getUserPermissions";    // 获取用户所拥有的权限

    }

    /**
     * 权限-令牌
     */
    public class Token
    {
        public static final String getCacheToken = "/getCacheToken"; // 获取缓存中的令牌
    }

    /**
     * 用户
     */
    public class User
    {
        public static final String createUser   = "/createUser";  // 创建用户
        public static final String updateUser   = "/updateUser";  // 更新用户
        public static final String deleteUser   = "/deleteUser";  // 删除用户
        public static final String frozeUser    = "/frozeUser";   // 冻结用户
        public static final String getUserById  = "/getUserById"; // 根据id获取
        public static final String getAllUsers  = "/getAllUsers"; // 获取全部用户

        public static final String assignRoles  = "/assignRoles"; // 授予角色
    }

    /**
     * 角色
     */
    public class Role
    {
        public static final String getRoleById  = "/getRoleById";  // 根据主键获取角色信息
        public static final String getAllRoles  = "/getAllRoles";  // 获取全部角色信息
        public static final String createRole   = "/createRole";   // 创建角色
    }

    /**
     * 权限
     */
    public class Permission
    {
        public static final String getPermissionsByUser = "/getPermissionsByUser"; // 获取指定用户权限
        public static final String getPermissionsTree   = "/getPermissionsTree";   // 获取权限树
        public static final String createPermission     = "/createPermission";     // 新增权限
    }

}
