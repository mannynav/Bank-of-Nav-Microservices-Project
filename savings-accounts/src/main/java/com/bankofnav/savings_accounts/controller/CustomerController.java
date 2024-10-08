package com.bankofnav.savings_accounts.controller;

import com.bankofnav.savings_accounts.dto.CustomerDetailsDto;
import com.bankofnav.savings_accounts.service.ICustomersService;
import jakarta.validation.constraints.Pattern;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private final ICustomersService iCustomersService;

    public CustomerController(ICustomersService iCustomersService){
        this.iCustomersService = iCustomersService;
    }

    @GetMapping("/getCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> getCustomer(@RequestParam
                                                                   @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                   String phoneNumber){
        CustomerDetailsDto customerDetailsDto = iCustomersService.getCustomerDetails(phoneNumber);
        return ResponseEntity.status(HttpStatus.SC_OK).body(customerDetailsDto);

    }


}
