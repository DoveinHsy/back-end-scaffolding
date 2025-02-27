package org.xiaoxingbomei.service;

/**
 * 短连接服务
 */
public interface ShortLinkService
{
    /**
     * 生成短连接
     * @param url
     * @return
     */
    String generateShortLink(String url);

    /**
     * 获取原始链接
     * @param shortLink
     * @return
     */
    String getOriginalLink(String shortLink);
}
