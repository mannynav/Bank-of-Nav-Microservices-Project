package com.bankofnav.savings_accounts;

import com.bankofnav.savings_accounts.dto.SavingsAccountsContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "Audit")
@EnableConfigurationProperties(value = {SavingsAccountsContactInfoDto.class})
public class SavingsAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavingsAccountApplication.class, args);
	}

}
