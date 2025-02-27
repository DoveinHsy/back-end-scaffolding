package org.xiaoxingbomei.dao.localhost;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.xiaoxingbomei.vo.User;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper
{
    List<User> getAllUserInfo();

    List<User> findByIdCards(@Param("idCards") List<String> importIdCardSet);

    // 批量插入用户信息
    void createUserInfo(@Param("list") List<User> users);

    // 批量更新
    void updateUserInfo(@Param("list") List<User> users);




}
