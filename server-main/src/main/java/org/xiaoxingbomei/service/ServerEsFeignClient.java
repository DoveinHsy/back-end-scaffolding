package org.xiaoxingbomei.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.common.entity.SystemEntity;
import org.xiaoxingbomei.vo.User;

import java.util.List;
import java.util.Map;


/**
 * server-es 的 feign客户端
 */
@FeignClient("server-es")
public interface ServerEsFeignClient
{
    /**
     * 系统
     */
    @RequestMapping(value ="/es/system/es_getSystemInfo",method = RequestMethod.POST)
    public GlobalEntity<List<SystemEntity>> getSystemInfo(@RequestBody String paramString);


    /**
     * 用户
     */
    @RequestMapping(value ="/es/user/es_saveUserInfoToEs",method = RequestMethod.POST)
    GlobalEntity saveUserInfoToEs(List<Map<String, Object>> userListMap);


    /**
     *
     */

}
