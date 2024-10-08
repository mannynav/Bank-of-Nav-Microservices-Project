package com.bankofnav.savings_accounts.service;

import com.bankofnav.savings_accounts.dto.CustomerDetailsDto;

public interface ICustomersService {

    CustomerDetailsDto getCustomerDetails(String mobileNumber);
}
