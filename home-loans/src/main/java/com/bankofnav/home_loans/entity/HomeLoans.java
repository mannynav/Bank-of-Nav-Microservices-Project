package com.bankofnav.home_loans.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HomeLoans extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    private String phoneNumber;

    private String loanTransactionNumber;

    private String loanType;

    private int totalLoan;

    private int amountPaid;

    private int outstandingBalance;

}
