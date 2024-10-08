package com.bankofnav.home_loans.mapper;

import com.bankofnav.home_loans.dto.HomeLoansDto;
import com.bankofnav.home_loans.entity.HomeLoans;

public class LoansMapper {

    public static HomeLoansDto mapToLoansDto(HomeLoans homeLoans, HomeLoansDto homeLoansDto) {
        homeLoansDto.setLoanTransactionNumber(homeLoans.getLoanTransactionNumber());
        homeLoansDto.setLoanType(homeLoans.getLoanType());
        homeLoansDto.setPhoneNumber(homeLoans.getPhoneNumber());
        homeLoansDto.setTotalLoan(homeLoans.getTotalLoan());
        homeLoansDto.setAmountPaid(homeLoans.getAmountPaid());
        homeLoansDto.setOutstandingBalance(homeLoans.getOutstandingBalance());
        return homeLoansDto;
    }

    public static HomeLoans mapToLoans(HomeLoansDto homeLoansDto, HomeLoans homeLoans) {
        homeLoans.setLoanTransactionNumber(homeLoansDto.getLoanTransactionNumber());
        homeLoans.setLoanType(homeLoansDto.getLoanType());
        homeLoans.setPhoneNumber(homeLoansDto.getPhoneNumber());
        homeLoans.setTotalLoan(homeLoansDto.getTotalLoan());
        homeLoans.setAmountPaid(homeLoansDto.getAmountPaid());
        homeLoans.setOutstandingBalance(homeLoansDto.getOutstandingBalance());
        return homeLoans;
    }

}
