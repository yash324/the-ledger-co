package com.ledger.company.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Loan {
    private final String borrowerName;
    private final float principalAmount;
    private final float periodInYears;
    private final float interestRate;
    private final List<LumpSumPayment> lumpSumPayments;

    public Loan(String borrowerName, float principalAmount, float periodInYears, float interestRate) {
        this.borrowerName = borrowerName;
        this.principalAmount = principalAmount;
        this.periodInYears = periodInYears;
        this.interestRate = interestRate;
        this.lumpSumPayments = new ArrayList<>();
    }

}
