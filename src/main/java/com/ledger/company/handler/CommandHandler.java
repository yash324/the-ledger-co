package com.ledger.company.handler;

import com.ledger.company.domain.Balance;
import com.ledger.company.domain.Bank;
import com.ledger.company.exceptions.BankNotFoundException;
import com.ledger.company.exceptions.LedgerCoException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class CommandHandler {
    private final List<Bank> banks;

    public CommandHandler() {
        this.banks = new ArrayList<>();
    }

    public void createLoan(String bankName, String borrowerName, float principalAmount, float numYears, float interestRate) throws LedgerCoException {
        Optional<Bank> selectedBank = banks.stream().filter(bank -> bank.getName().equalsIgnoreCase(bankName)).findFirst();
        if(!selectedBank.isPresent()) {
            Bank bank = new Bank(bankName);
            selectedBank = Optional.of(bank);
            banks.add(bank);
        }
        selectedBank.get().addLoan(borrowerName, principalAmount, numYears, interestRate);
    }

    public void payLumpSum(String bankName, String borrowerName, float amount, int emiNumber) throws LedgerCoException {
        Optional<Bank> selectedBank = banks.stream().filter(bank -> bank.getName().equalsIgnoreCase(bankName)).findFirst();
        if(!selectedBank.isPresent()) {
            throw new BankNotFoundException(bankName);
        }
        selectedBank.get().addLumpSumPayment(borrowerName, amount, emiNumber);
    }

    public Balance getBalance(String bankName, String borrowerName, int emiNumber) throws LedgerCoException {
        Optional<Bank> selectedBank = banks.stream().filter(bank -> bank.getName().equalsIgnoreCase(bankName)).findFirst();
        if(!selectedBank.isPresent()) {
            throw new BankNotFoundException(bankName);
        }
        return selectedBank.get().getLoanBalance(borrowerName, emiNumber);
    }
}
