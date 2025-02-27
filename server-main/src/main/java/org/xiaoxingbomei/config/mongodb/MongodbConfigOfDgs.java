package org.xiaoxingbomei.config.mongodb;



import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * mongodb多数据源配置-dgs
 */
//@Configuration
//@EnableMongoRepositories
//        (
////        basePackages = "org.xiaoxingbomei.dao.mongodb.dgs",
//        mongoTemplateRef = "mongoTemplateOfDgs"
//        )
//public class MongodbConfigOfDgs extends AbstractMongoClientConfiguration
//{
//        @Value("${apollo.mongodb.dgs.url}")
//        private String localMongodbUrl;
//
//        @Value("${apollo.mongodb.dgs.database}")
//        private String localMongodbDatabase;
//
//        @Override
//        protected String getDatabaseName()
//        {
//                return localMongodbDatabase;
//        }
//
//        @Override
//        public MongoClient mongoClient()
//        {
//                return MongoClients.create(localMongodbUrl);
//        }
//
//        @Bean
//        @Primary  // 如果定义另外的数据源就不用标注这个注解了，目前只有一个
//        public MongoTemplate mongoTemplateOfDgs()
//        {
//                return new MongoTemplate(mongoClient(), getDatabaseName());
//        }
//
//
//}
