package org.xiaoxingbomei.dao.localhost;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 用户 & 角色
     */
    List<SysRole> selectRolesByUserId(@Param("userId") String userId);
    int insertUserRoles(@Param("loginId") String loginId, @Param("roleIds") List<String> roleIds);
    int deleteUserRoles(@Param("loginId") String loginId, @Param("roleIds") List<String> roleIds);

    /**
     * 角色 & 权限
     */
    List<String> selectPermIdsByRoleId(String roleId);
    int batchInsertRolePermissions(@Param("roleKey") String roleKey, @Param("permissionIds") List<String> permissionIds);
    int deleteRolePermissions(@Param("roleId")String roleId, @Param("permissionIds") List<String> permIds);

}
