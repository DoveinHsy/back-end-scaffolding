package org.xiaoxingbomei.config.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.xiaoxingbomei.Enum.HttpMethodEmun;
import org.xiaoxingbomei.service.Client;
import org.xiaoxingbomei.utils.Spring_Utils;

@Slf4j
public class HttpClient implements Client
{

    private HttpMethodEmun httpMethodEmun;

    @Override
    public void setMethod(HttpMethodEmun httpMethodEmun) {
        this.httpMethodEmun = httpMethodEmun;
    }

    @Override
    public Object send(Object... arms) {
        RestTemplate restTemplate = Spring_Utils.getBean("restTemplate1");
        switch (httpMethodEmun) {
            case GET:
                break;
            case POST:

                break;
            case DELETE:
                break;
            case PUT:
                String url = arms[0].toString();
                String bizId = ((String) arms[1]);
                String replace = url.replace("{id}", bizId);
                log.info("回调接口{}", replace);
                restTemplate.put(replace, null);
                break;
            default:

        }
        return null;
    }
}