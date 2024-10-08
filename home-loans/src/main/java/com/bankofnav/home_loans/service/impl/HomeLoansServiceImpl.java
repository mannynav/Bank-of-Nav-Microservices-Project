package com.bankofnav.home_loans.service.impl;

import com.bankofnav.home_loans.constants.HomeLoansConstants;
import com.bankofnav.home_loans.dto.HomeLoansDto;
import com.bankofnav.home_loans.entity.HomeLoans;
import com.bankofnav.home_loans.exception.HomeLoanAlreadyExistsException;
import com.bankofnav.home_loans.exception.ResourceNotFoundException;
import com.bankofnav.home_loans.mapper.LoansMapper;
import com.bankofnav.home_loans.repository.HomeLoansRepository;
import com.bankofnav.home_loans.service.IHomeLoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class HomeLoansServiceImpl implements IHomeLoansService {

    private HomeLoansRepository homeLoansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<HomeLoans> optionalLoans= homeLoansRepository.findByPhoneNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new HomeLoanAlreadyExistsException("Home Loan already exists with phone nummber "+mobileNumber);
        }
        homeLoansRepository.save(createNewLoan(mobileNumber));
    }

    private HomeLoans createNewLoan(String mobileNumber) {
        HomeLoans newLoan = new HomeLoans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanTransactionNumber(Long.toString(randomLoanNumber));
        newLoan.setPhoneNumber(mobileNumber);
        newLoan.setLoanType(HomeLoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(HomeLoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingBalance(HomeLoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public HomeLoansDto getLoan(String mobileNumber) {
        HomeLoans homeLoans = homeLoansRepository.findByPhoneNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Home Loan", "Phone Number", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(homeLoans, new HomeLoansDto());
    }

    @Override
    public boolean updateLoan(HomeLoansDto homeLoansDto) {
        HomeLoans homeLoans = homeLoansRepository.findByLoanTransactionNumber(homeLoansDto.getLoanTransactionNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Home Loan", "Loan Transaction Number", homeLoansDto.getLoanTransactionNumber()));
        LoansMapper.mapToLoans(homeLoansDto, homeLoans);
        homeLoansRepository.save(homeLoans);
        return  true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        HomeLoans homeLoans = homeLoansRepository.findByPhoneNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Home Loan", "Phone Number", mobileNumber)
        );
        homeLoansRepository.deleteById(homeLoans.getLoanId());
        return true;
    }

}
