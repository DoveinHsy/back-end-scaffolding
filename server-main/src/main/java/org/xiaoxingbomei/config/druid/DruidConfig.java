package org.xiaoxingbomei.config.druid;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DruidConfig
{

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.druid.initial-size}")
    private int initialSize;

    @Value("${spring.datasource.druid.min-idle}")
    private int minIdle;

    @Value("${spring.datasource.druid.max-active}")
    private int maxActive;

    @Value("${spring.datasource.druid.max-wait}")
    private long maxWait;

    @Value("${spring.datasource.druid.test-on-borrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.validation-query}")
    private String validationQuery;

    @Value("${spring.datasource.druid.stat-view-servlet.login-username}")
    private String druidLoginUsername;

    @Value("${spring.datasource.druid.stat-view-servlet.login-password}")
    private String druidLoginPassword;

    @Value("${spring.datasource.druid.stat-view-servlet.allow}")
    private String druidAllowIp;

    @Value("${spring.datasource.druid.web-stat-filter.exclusions}")
    private String druidExclusions;

    /**
     * 配置 Druid 数据源
     */
    @Bean
    @Primary
    public DataSource dataSource() throws SQLException
    {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setValidationQuery(validationQuery);

        // 其他 Druid 连接池配置项
        dataSource.setTimeBetweenEvictionRunsMillis(60000); // 配置间隔多久检测一次空闲连接
        dataSource.setMinEvictableIdleTimeMillis(300000);   // 配置一个连接在池中最小生存的时间
        dataSource.setPoolPreparedStatements(true);         // 开启 PSCache
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20); // 设置 PSCache 大小

        return dataSource;
    }

    /**
     * 配置 Druid 监控页面 Servlet
     * 访问路径为 /druid/*，登录使用账号和密码
     */
    @Bean
    @ConditionalOnMissingBean(StatViewServlet.class) // 确保只有在没有其他 StatViewServlet 的情况下注册
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet()
    {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean =
                new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        // 配置登录账号和密码
        servletRegistrationBean.addInitParameter("loginUsername", druidLoginUsername);
        servletRegistrationBean.addInitParameter("loginPassword", druidLoginPassword);

        // 配置允许查看的 IP，防止非授权的访问
        servletRegistrationBean.addInitParameter("allow", druidAllowIp);

        return servletRegistrationBean;
    }

    /**
     * 配置 Druid Web 统计过滤器
     * 用于对请求进行统计
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean =
                new FilterRegistrationBean<>(new WebStatFilter());

        // 配置不需要监控的路径
        filterRegistrationBean.addInitParameter("exclusions", druidExclusions);

        // 配置需要监控的 URL 路径
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
