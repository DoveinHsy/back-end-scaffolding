package org.xiaoxingbomei.service.impl;

import org.springframework.stereotype.Service;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.common.entity.SystemEntity;
import org.xiaoxingbomei.service.SystemService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SystemServiceImpl implements SystemService
{
    @Override
    public GlobalEntity es_getSystemInfo(String paramString)
    {


        List<SystemEntity> objects = new ArrayList<>();
        SystemEntity systemEntity1 = new SystemEntity();
        SystemEntity systemEntity2 = new SystemEntity();
        systemEntity1.setSystemNumber("777");
        systemEntity1.setSystemChineseName("小型博美");
        systemEntity2.setSystemNumber("666");
        systemEntity2.setSystemChineseName("大型博美");
        objects.add(systemEntity1);
        objects.add(systemEntity2);


        return GlobalEntity.success(objects,"es-getSystemInfo");
    }
}
