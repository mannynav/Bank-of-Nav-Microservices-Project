package com.bankofnav.savings_accounts.service;

import com.bankofnav.savings_accounts.dto.CustomerDto;

public interface ISavingsAccountsService {

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);

}
