package org.xiaoxingbomei.config.apollo;
//
//import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
//import com.ctrip.framework.apollo.openapi.dto.OpenNamespaceDTO;
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ApolloHealthIndicator implements HealthIndicator
//{
//
//    private final ApolloOpenApiClient apolloOpenApiClient;
//
//    public ApolloHealthIndicator(ApolloOpenApiClient apolloOpenApiClient) {
//        this.apolloOpenApiClient = apolloOpenApiClient;
//    }
//
//    @Override
//    public Health health() {
//        try {
//            OpenNamespaceDTO namespace = apolloOpenApiClient.getNamespace("your-app-id", "your-cluster", "application");
//            if (namespace != null) {
//                return Health.up().build();
//            } else {
//                return Health.down().withDetail("message", "Apollo连接失败").build();
//            }
//        } catch (Exception e) {
//            return Health.down(e).withDetail("message", "Apollo连接失败").build();
//        }
//    }
//}
