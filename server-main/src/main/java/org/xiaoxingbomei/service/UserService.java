package org.xiaoxingbomei.service;


import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.vo.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService
{
    GlobalEntity getAllUserInfo();

    void createUserIndex();

    void createUserInfo(@Param("list") List<User> users);

    void exportUserInfoTemplate(HttpServletResponse response) throws Exception;

    GlobalEntity updateUserInfoByTemplate(MultipartFile file) throws Exception;

    List<String> discoverNewWords(List<String> texts);

    void updateLocalDictionary(List<String> newWords);
}
