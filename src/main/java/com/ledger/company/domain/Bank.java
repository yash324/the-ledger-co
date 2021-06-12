package com.ledger.company.domain;

import com.ledger.company.exceptions.DuplicateLoanException;
import com.ledger.company.exceptions.LedgerCoException;
import com.ledger.company.exceptions.LoanNotFoundException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            throw new DuplicateLoanException(name, borrowerName);
        }
        Loan loan = new Loan(borrowerName, principalAmount, numYears, interestRate);
        loans.add(loan);
    }

    public void addLumpSumPayment(String borrowerName, float amount, int emiNumber) throws LedgerCoException {
        Optional<Loan> foundLoan = loans.stream().filter(loan -> loan.getBorrowerName().equalsIgnoreCase(borrowerName)).findFirst();
        if (!foundLoan.isPresent()) {
            throw new LoanNotFoundException(name, borrowerName);
        }
        foundLoan.get().addLumpSumPayment(amount, emiNumber);
    }

    public Balance getLoanBalance(String borrowerName, int emiNumber) throws LedgerCoException {
        Optional<Loan> foundLoan = loans.stream().filter(loan -> loan.getBorrowerName().equalsIgnoreCase(borrowerName)).findFirst();
        if (!foundLoan.isPresent()) {
            throw new LoanNotFoundException(name, borrowerName);
        }
        Balance balance = foundLoan.get().getBalance(emiNumber);
        balance.setBankName(this.name);
        return balance;
    }
}
