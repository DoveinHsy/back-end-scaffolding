package org.xiaoxingbomei.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteDebugConfig
{

    @Autowired
    private RouteLocator routeLocator;

    @Bean
    public CommandLineRunner debugRoutes()
    {
        return args ->
        {
            routeLocator.getRoutes().subscribe(route -> {
                System.out.println("Route ID: " + route.getId());
                System.out.println("URI: " + route.getUri());
                System.out.println("Predicates: " + route.getPredicate());
                System.out.println("Filters: " + route.getFilters());
                System.out.println("------------------------");
            });
        };
    }
}
