package org.xiaoxingbomei.config.datasource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

/**
 * 本地数据库连接配置
 */
// 标注该类是一个配置类
@Configuration
// 指定 MyBatis 扫描的 Mapper 接口包路径，并指定使用的 SqlSessionTemplate
@MapperScan(basePackages = "org.xiaoxingbomei.dao.localhost", sqlSessionTemplateRef = "localhostSqlSessionTemplate")
public class MysqlConfigOfLocal
{

    // 定义一个 DataSource Bean，并从配置文件中读取以 `spring.datasource.localhost` 为前缀的属性
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.localhost")
    public DataSource localhostDataSource() {
        return DataSourceBuilder.create().build();
    }

    // 定义一个 SqlSessionFactory Bean，依赖于 `localhostDataSource`
    @Bean
    @DependsOn("localhostDataSource")
    public SqlSessionFactory localhostSqlSessionFactory() throws Exception
    {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // 设置数据源为 `localhostDataSource`
        factoryBean.setDataSource(localhostDataSource());
        // 设置 MyBatis 插件，例如自定义的 MybatisLogInterceptor
//        factoryBean.setPlugins(new Interceptor[]{new MybatisLogInterceptor()});

        // 配置 MyBatis 的设置，例如在字段值为 null 时调用 setter 方法
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCallSettersOnNulls(true);
        factoryBean.setConfiguration(configuration);

        // 返回配置好的 SqlSessionFactory
        return factoryBean.getObject();
    }

    // 定义一个 SqlSessionTemplate Bean，依赖于 `localhostSqlSessionFactory`
    @Bean
    @DependsOn("localhostSqlSessionFactory")
    public SqlSessionTemplate localhostSqlSessionTemplate() throws Exception
    {
        // 创建并返回 SqlSessionTemplate
        SqlSessionTemplate template = new SqlSessionTemplate(localhostSqlSessionFactory());
        return template;
    }

}
