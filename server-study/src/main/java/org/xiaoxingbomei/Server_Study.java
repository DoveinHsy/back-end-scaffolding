package org.xiaoxingbomei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class ,
        com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure.class })
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
@Slf4j
public class Server_Study
{

    public static void main(String[] args)
    {
        SpringApplication.run(Server_Study.class, args);
    }
}