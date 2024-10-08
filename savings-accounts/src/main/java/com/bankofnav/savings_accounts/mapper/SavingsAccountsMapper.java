package com.bankofnav.savings_accounts.mapper;

import com.bankofnav.savings_accounts.dto.SavingsAccountsDto;
import com.bankofnav.savings_accounts.entity.SavingsAccounts;

public class SavingsAccountsMapper {

    public static SavingsAccountsDto mapToSavingsAccountsDto(SavingsAccounts savingsAccounts, SavingsAccountsDto savingsAccountsDto) {
        savingsAccountsDto.setAccountNumber(savingsAccounts.getAccountNumber());
        savingsAccountsDto.setAccountType(savingsAccounts.getAccountType());
        savingsAccountsDto.setBranchAddress(savingsAccounts.getBranchAddress());
        return savingsAccountsDto;
    }

    public static SavingsAccounts mapToSavingsAccounts(SavingsAccountsDto savingsAccountsDto, SavingsAccounts savingsAccounts) {
        savingsAccounts.setAccountNumber(savingsAccountsDto.getAccountNumber());
        savingsAccounts.setAccountType(savingsAccountsDto.getAccountType());
        savingsAccounts.setBranchAddress(savingsAccountsDto.getBranchAddress());
        return savingsAccounts;
    }

}
