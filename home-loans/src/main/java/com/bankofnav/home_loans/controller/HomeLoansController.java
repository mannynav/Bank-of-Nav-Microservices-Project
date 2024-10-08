package com.bankofnav.home_loans.controller;
import com.bankofnav.home_loans.constants.HomeLoansConstants;
import com.bankofnav.home_loans.dto.HomeLoansContactInfoDto;
import com.bankofnav.home_loans.dto.HomeLoansDto;
import com.bankofnav.home_loans.dto.ResponseDto;
import com.bankofnav.home_loans.service.IHomeLoansService;
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
@RequestMapping(path = "/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class HomeLoansController {

    private IHomeLoansService iHomeLoansService;

    public HomeLoansController(IHomeLoansService iHomeLoansService) {
        this.iHomeLoansService = iHomeLoansService;
    }

    @Autowired
    private Environment environment;

    @Autowired
    private HomeLoansContactInfoDto homeLoansContactInfoDto;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createHomeLoan(@RequestParam
                                                      @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                      String mobileNumber) {
        iHomeLoansService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HomeLoansConstants.STATUS_201, HomeLoansConstants.MESSAGE_201));
    }

    @GetMapping("/get")
    public ResponseEntity<HomeLoansDto> getHomeLoan(@RequestParam
                                                               @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                               String phoneNumber) {
        HomeLoansDto homeLoansDto = iHomeLoansService.getLoan(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body(homeLoansDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateHomeLoan(@Valid @RequestBody HomeLoansDto homeLoansDto) {
        boolean isUpdated = iHomeLoansService.updateLoan(homeLoansDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(HomeLoansConstants.STATUS_200, HomeLoansConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(HomeLoansConstants.STATUS_417, HomeLoansConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteHomeLoan(@RequestParam
                                                                @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                String mobileNumber) {
        boolean isDeleted = iHomeLoansService.deleteLoan(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(HomeLoansConstants.STATUS_200, HomeLoansConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(HomeLoansConstants.STATUS_417, HomeLoansConstants.MESSAGE_417_DELETE));
        }
    }


    @GetMapping("/contact-info")
    public ResponseEntity<HomeLoansContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(homeLoansContactInfoDto);
    }
}
