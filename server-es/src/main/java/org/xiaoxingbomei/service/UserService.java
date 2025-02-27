package org.xiaoxingbomei.service;

import org.xiaoxingbomei.common.entity.GlobalEntity;

import java.util.List;
import java.util.Map;

public interface UserService
{
    GlobalEntity saveUserInfoToEs(List<Map<String, Object>> userListMap);
}
