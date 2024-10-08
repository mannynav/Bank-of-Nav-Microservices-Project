package com.bankofnav.savings_accounts.controller;

import com.bankofnav.savings_accounts.constants.SavingsAccountConstants;
import com.bankofnav.savings_accounts.dto.SavingsAccountsContactInfoDto;
import com.bankofnav.savings_accounts.dto.CustomerDto;
import com.bankofnav.savings_accounts.dto.ResponseDto;
import com.bankofnav.savings_accounts.service.ISavingsAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class SavingsAccountController {

    private final ISavingsAccountsService iSavingsAccountsService;

    public SavingsAccountController(ISavingsAccountsService iSavingsAccountsService) {
        this.iSavingsAccountsService = iSavingsAccountsService;
    }

    @Autowired
    private Environment environment;

    @Autowired
    private SavingsAccountsContactInfoDto savingsAccountsContactInfoDto;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createSavingsAccount(@Valid @RequestBody CustomerDto customerDto) {
        iSavingsAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(SavingsAccountConstants.STATUS_201, SavingsAccountConstants.MESSAGE_201));
    }

    @GetMapping("/get")
    public ResponseEntity<CustomerDto> getSavingsAccount(@RequestParam
                                                               @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                               String mobileNumber) {
        CustomerDto customerDto = iSavingsAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateSavingsAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iSavingsAccountsService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(SavingsAccountConstants.STATUS_200, SavingsAccountConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(SavingsAccountConstants.STATUS_417, SavingsAccountConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteSavingsAccount(@RequestParam
                                                                @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                String mobileNumber) {
        boolean isDeleted = iSavingsAccountsService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(SavingsAccountConstants.STATUS_200, SavingsAccountConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(SavingsAccountConstants.STATUS_417, SavingsAccountConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/contact-info")
    public ResponseEntity<SavingsAccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(savingsAccountsContactInfoDto);
    }


}
