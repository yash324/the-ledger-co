package com.ledger.company.domain;

import com.ledger.company.exceptions.DuplicateLoanException;
import com.ledger.company.exceptions.LedgerCoException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Bank {
    private final String name;
    private final List<Loan> loans;

    public Bank(String name) {
        this.name = name;
        this.loans = new ArrayList<>();
    }

    public void addLoan(String borrowerName, float principalAmount, float numYears, float interestRate) throws LedgerCoException {
        if (loans.stream().anyMatch(loan -> loan.getBorrowerName().equalsIgnoreCase(borrowerName))) {
            throw new DuplicateLoanException();
        }
        Loan loan = new Loan(borrowerName, principalAmount, numYears, interestRate);
        loans.add(loan);
    }
}
