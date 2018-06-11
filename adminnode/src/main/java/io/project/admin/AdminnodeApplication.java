package io.project.admin;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableAdminServer
public class AdminnodeApplication {

    private final String adminContextPath;

    public AdminnodeApplication(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    public static void main(String[] args) {
        SpringApplication.run(AdminnodeApplication.class, args);
    }

    @Bean
    //@Profile("secure")
    public SecurityWebFilterChain securityWebFilterChainSecure(ServerHttpSecurity http) {
        // @formatter:off
        return http.authorizeExchange()
                .pathMatchers(adminContextPath + "/assets/**").permitAll()
                .pathMatchers(adminContextPath + "/login").permitAll()
                .anyExchange().authenticated()
                .and()
                .formLogin().loginPage(adminContextPath + "/login").and()
                .logout().logoutUrl(adminContextPath + "/logout").and()
                .httpBasic().and()
                .csrf().disable()
                .build();
        // @formatter:on
    }

}
