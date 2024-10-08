package com.bankofnav.home_loans.repository;

import com.bankofnav.home_loans.entity.HomeLoans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeLoansRepository extends JpaRepository<HomeLoans, Long> {

    Optional<HomeLoans> findByPhoneNumber(String phoneNumber);

    Optional<HomeLoans> findByLoanTransactionNumber(String loanTransactionNumber);

}
