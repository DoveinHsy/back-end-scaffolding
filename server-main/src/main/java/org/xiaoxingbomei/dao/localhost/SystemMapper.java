package org.xiaoxingbomei.dao.localhost;

import org.apache.ibatis.annotations.Mapper;
import org.xiaoxingbomei.common.entity.SystemEntity;

import java.util.List;

@Mapper
public interface SystemMapper
{
    List<String> getSystemIdsByMembers(List<SystemEntity> members); //
}
