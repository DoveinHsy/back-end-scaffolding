package org.xiaoxingbomei.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.constant.EsApiConstant;
import org.xiaoxingbomei.service.SystemService;
import org.xiaoxingbomei.service.UserService;

import java.util.List;
import java.util.Map;


@RestController
@Slf4j
public class SystemController
{

    @Autowired
    private SystemService systemService;

    @Autowired
    private UserService userService;

    // ===================================================================

    @RequestMapping(value = EsApiConstant.System.es_getSystemInfo,method = RequestMethod.POST)
    public GlobalEntity getSystemInfo(@RequestBody String param)
    {
        log.info("123");
        GlobalEntity ret = null;
        ret = systemService.es_getSystemInfo(param);
        return ret;
    }

    @RequestMapping(value = EsApiConstant.User.saveUserInfoToEs,method = RequestMethod.POST)
    public GlobalEntity saveUserInfoToEs(@RequestBody List<Map<String,Object>> userListMap)
    {
        GlobalEntity ret = null;
        ret = userService.saveUserInfoToEs(userListMap);
        return ret;
    }

}
