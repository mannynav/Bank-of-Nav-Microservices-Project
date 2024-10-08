package com.bankofnav.savings_accounts.repository;

import com.bankofnav.savings_accounts.entity.SavingsAccounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingsAccountsRepository extends JpaRepository<SavingsAccounts, Long> {

    Optional<SavingsAccounts> findByCustomerId(Long customerId);

    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);

}
