package com.ledger.company.handler;

import com.ledger.company.domain.Bank;
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

    public String loan(String bankName, String borrowerName, float principalAmount, float numYears, float interestRate) throws LedgerCoException {
        Optional<Bank> selectedBank = banks.stream().filter(bank -> bank.getName().equalsIgnoreCase(bankName)).findFirst();
        if(!selectedBank.isPresent()) {
            Bank bank = new Bank(bankName);
            selectedBank = Optional.of(bank);
            banks.add(bank);
        }
        selectedBank.get().addLoan(borrowerName, principalAmount, numYears, interestRate);
        return "";
    }
}
