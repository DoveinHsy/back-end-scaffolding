package org.xiaoxingbomei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class ,
        com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure.class })
@EnableEurekaClient
@EnableFeignClients
public class Server_Sql
{
    public static void main(String[] args)
    {
        SpringApplication.run(Server_Sql.class, args);
    }
}