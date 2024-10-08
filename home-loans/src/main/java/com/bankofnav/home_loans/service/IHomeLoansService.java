package com.bankofnav.home_loans.service;

import com.bankofnav.home_loans.dto.HomeLoansDto;

public interface IHomeLoansService {

    void createLoan(String mobileNumber);

    HomeLoansDto getLoan(String mobileNumber);

    boolean updateLoan(HomeLoansDto homeLoansDto);

    boolean deleteLoan(String mobileNumber);

}
