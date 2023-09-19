package com.mice.backoffice.c2v_mice_backoffice_api;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.persistence.EntityManager;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

@OpenAPIDefinition(servers = {@Server(url="/", description = "Mice B/O API URL")})
@EnableFeignClients
//@EnableJpaRepositories(repositoryImplementationPostfix = "")
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}, scanBasePackages = "com")
public class C2vMiceBackofficeApiApplication {
	@Bean
	ApplicationRunner onStart() {
		return args -> TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(C2vMiceBackofficeApiApplication.class, args);
	}

	@Bean
	JPAQueryFactory jpaQueryFactory(EntityManager em) {
		return new JPAQueryFactory(em);
	}
}
