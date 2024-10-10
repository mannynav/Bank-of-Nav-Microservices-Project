package com.bankofnav.savings_accounts.service.client;

import com.bankofnav.savings_accounts.dto.HomeLoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("home-loans")
public interface HomeLoansFeignClient {

    @GetMapping(value = "/v1/get",consumes = "application/json")
    public ResponseEntity<HomeLoansDto> getHomeLoanDetails(@RequestParam String phoneNumber);

}
