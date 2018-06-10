package io.project.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@Configuration
@EnableMongoRepositories("io.project.mongo.repositories")
@ComponentScan(basePackages = {"io.project"})
@EntityScan("io.project.mongo.domain")
@EnableCaching
@EnableAsync
@EnableHystrix
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableEurekaClient
public class AuthserverApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
	}
}
