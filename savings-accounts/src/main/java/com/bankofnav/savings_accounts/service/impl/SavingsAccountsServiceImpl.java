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

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        savingsAccountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private SavingsAccounts createNewAccount(Customer customer) {
        SavingsAccounts newAccount = new SavingsAccounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(SavingsAccountConstants.SAVINGS);
        newAccount.setBranchAddress(SavingsAccountConstants.ADDRESS);
        return newAccount;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return SavingsAccounts Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        SavingsAccounts savingsAccounts = savingsAccountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setSavingsAccountsDto(SavingsAccountsMapper.mapToSavingsAccountsDto(savingsAccounts, new SavingsAccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        SavingsAccountsDto savingsAccountsDto = customerDto.getSavingsAccountsDto();
        if(savingsAccountsDto !=null ){
            SavingsAccounts savingsAccounts = savingsAccountsRepository.findById(savingsAccountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", savingsAccountsDto.getAccountNumber().toString())
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

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        savingsAccountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
