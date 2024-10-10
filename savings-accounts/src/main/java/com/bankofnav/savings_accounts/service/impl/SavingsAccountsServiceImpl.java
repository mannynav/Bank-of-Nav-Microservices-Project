package com.bankofnav.savings_accounts.service.impl;

import com.bankofnav.savings_accounts.constants.SavingsAccountConstants;
import com.bankofnav.savings_accounts.dto.SavingsAccountsDto;
import com.bankofnav.savings_accounts.dto.CustomerDto;
import com.bankofnav.savings_accounts.entity.SavingsAccounts;
import com.bankofnav.savings_accounts.entity.Customer;
import com.bankofnav.savings_accounts.exception.CustomerAlreadyExistsException;
import com.bankofnav.savings_accounts.exception.ResourceNotFoundException;
import com.bankofnav.savings_accounts.mapper.SavingsAccountsMapper;
import com.bankofnav.savings_accounts.mapper.CustomerMapper;
import com.bankofnav.savings_accounts.repository.SavingsAccountsRepository;
import com.bankofnav.savings_accounts.repository.CustomerRepository;
import com.bankofnav.savings_accounts.service.ISavingsAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class SavingsAccountsServiceImpl implements ISavingsAccountsService {

    private SavingsAccountsRepository savingsAccountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByPhoneNumber(customerDto.getPhoneNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getPhoneNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        savingsAccountsRepository.save(createNewAccount(savedCustomer));
    }

    private SavingsAccounts createNewAccount(Customer customer) {
        SavingsAccounts newAccount = new SavingsAccounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(SavingsAccountConstants.SAVINGS);
        newAccount.setBranchAddress(SavingsAccountConstants.ADDRESS);
        return newAccount;
    }


    @Override
    public CustomerDto fetchAccount(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "phone Number", phoneNumber)
        );
        SavingsAccounts savingsAccounts = savingsAccountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Savings Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setSavingsAccountsDto(SavingsAccountsMapper.mapToSavingsAccountsDto(savingsAccounts, new SavingsAccountsDto()));
        return customerDto;
    }


    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        SavingsAccountsDto savingsAccountsDto = customerDto.getSavingsAccountsDto();
        if(savingsAccountsDto !=null ){
            SavingsAccounts savingsAccounts = savingsAccountsRepository.findById(savingsAccountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Savings Account", "Savings account number", savingsAccountsDto.getAccountNumber().toString())
            );
            SavingsAccountsMapper.mapToSavingsAccounts(savingsAccountsDto, savingsAccounts);
            savingsAccounts = savingsAccountsRepository.save(savingsAccounts);

            Long customerId = savingsAccounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "phone Number", phoneNumber)
        );
        savingsAccountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
