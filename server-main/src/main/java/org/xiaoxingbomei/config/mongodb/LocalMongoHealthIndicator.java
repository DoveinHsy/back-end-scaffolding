package org.xiaoxingbomei.config.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;



/**
 * actuator健康监测
 */
@Component("localMongoHealthIndicator")
public class LocalMongoHealthIndicator implements HealthIndicator
{

    @Autowired
    @Qualifier("mongoTemplateOfLocal")
    private MongoTemplate primaryMongoTemplate;

    @Override
    public Health health()
    {
        try
        {
            primaryMongoTemplate.executeCommand("{ ping: 1 }");
            return Health.up().withDetail("database", "local").build();
        } catch (Exception e)
        {
            return Health.down(e).withDetail("database", "local").build();
        }
    }

}