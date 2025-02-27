package org.xiaoxingbomei.dao.localhost;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrgMapper
{
    //
    List<Map<String,String>> getAllOrgInfo();

    //
    List<Map<String,String>> getAllOrgInfoByOrdId(@Param("orgId") String orgId);
}