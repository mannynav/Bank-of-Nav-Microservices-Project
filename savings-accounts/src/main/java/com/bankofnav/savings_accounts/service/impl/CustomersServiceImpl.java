package com.bankofnav.savings_accounts.service.impl;

import com.bankofnav.savings_accounts.dto.SavingsAccountsDto;
import com.bankofnav.savings_accounts.dto.CustomerDetailsDto;
import com.bankofnav.savings_accounts.dto.HomeLoansDto;
import com.bankofnav.savings_accounts.entity.SavingsAccounts;
import com.bankofnav.savings_accounts.entity.Customer;
import com.bankofnav.savings_accounts.exception.ResourceNotFoundException;
import com.bankofnav.savings_accounts.mapper.SavingsAccountsMapper;
import com.bankofnav.savings_accounts.mapper.CustomerMapper;
import com.bankofnav.savings_accounts.repository.SavingsAccountsRepository;
import com.bankofnav.savings_accounts.repository.CustomerRepository;
import com.bankofnav.savings_accounts.service.ICustomersService;
import com.bankofnav.savings_accounts.service.client.HomeLoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private SavingsAccountsRepository savingsAccountsRepository;
    private CustomerRepository customerRepository;
    private HomeLoansFeignClient homeLoansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto getCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        SavingsAccounts savingsAccounts = savingsAccountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setSavingsAccountsDto(SavingsAccountsMapper.mapToSavingsAccountsDto(savingsAccounts, new SavingsAccountsDto()));

        ResponseEntity<HomeLoansDto> loansDtoResponseEntity = homeLoansFeignClient.getHomeLoanDetails(mobileNumber);
        customerDetailsDto.setHomeLoansDto(loansDtoResponseEntity.getBody());

        return customerDetailsDto;

    }
}
