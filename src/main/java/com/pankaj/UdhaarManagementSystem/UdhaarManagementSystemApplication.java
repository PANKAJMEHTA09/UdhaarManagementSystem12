package com.pankaj.UdhaarManagementSystem;

import com.pankaj.UdhaarManagementSystem.config.RsaKeyConfigProperties;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;


@SecurityScheme(
		name = "BearerAuth",
		scheme = "Bearer",
		bearerFormat = "JWT",
		type = SecuritySchemeType.HTTP,
		in = SecuritySchemeIn.HEADER
)
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfigProperties.class)
public class UdhaarManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(UdhaarManagementSystemApplication.class, args);
	}

}
