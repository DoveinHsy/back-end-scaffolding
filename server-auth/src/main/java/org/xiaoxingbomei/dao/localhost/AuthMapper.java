package org.xiaoxingbomei.dao.localhost;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.xiaoxingbomei.entity.SysPermission;
import org.xiaoxingbomei.entity.SysRole;

import java.util.List;

@Mapper
public interface AuthMapper
{


    /**
     * 用户
     */

    /**
     * 角色
     */
    void insertRole(@Param("role") SysRole role);

    /**
     * 权限
     */
    int  insertPermission(@Param("sysPermission") SysPermission sysPermission);
    List<SysPermission>  getAllPermission();

    /**
     * 用户 & 角色
     */
    List<SysRole> getRolesByUserId(@Param("userId") String userId);
    List<SysRole> getAllRoles();
    int insertUserRoles(@Param("loginId") String loginId, @Param("roleIds") List<String> roleIds);
    int deleteUserRoles(@Param("loginId") String loginId);

    /**
     * 角色 & 权限
     */
    List<String> selectPermIdsByRoleId(String roleId);
    int batchInsertRolePermissions(@Param("roleKey") String roleKey, @Param("permissionKeys") List<String> permissionKeys);
    int deleteRolePermissions(@Param("roleKey")String roleId, @Param("permissionKeys") List<String> permissionKeys);
    int deleteRoleAllPermissions(@Param("roleKey")String roleKey);

}
