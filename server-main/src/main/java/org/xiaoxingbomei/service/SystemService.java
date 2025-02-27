package org.xiaoxingbomei.service;

import org.apache.logging.log4j.core.impl.MutableLogEvent;
import org.springframework.web.multipart.MultipartFile;
import org.xiaoxingbomei.common.entity.GlobalEntity;

public interface SystemService
{
    GlobalEntity getSystemInfo(String paramString);


    GlobalEntity multiHandleSystem(MultipartFile file);

}
