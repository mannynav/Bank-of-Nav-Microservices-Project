package com.bankofnav.home_loans;

import com.bankofnav.home_loans.dto.HomeLoansContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "Audit")
@EnableConfigurationProperties(value = {HomeLoansContactInfoDto.class})

public class HomeLoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeLoansApplication.class, args);
	}
}
