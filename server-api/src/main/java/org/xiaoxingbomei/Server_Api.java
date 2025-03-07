package org.xiaoxingbomei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.xiaoxingbomei.common.config.springboot.MyBanner;
import reactivefeign.spring.config.EnableReactiveFeignClients;


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableEurekaClient
@EnableDiscoveryClient
@EnableReactiveFeignClients(basePackages = "org.xiaoxingbomei.reactiveFeign")
public class Server_Api
{
    public static void main(String[] args)
    {

        // 创建SpringApplication实例
        SpringApplication app = new SpringApplication(Server_Api.class);

        // 设置自定义的banner
        app.setBanner(new MyBanner());

        app.run(args);
    }
}