package com.ledger.company.domain;

import com.ledger.company.exceptions.DuplicateLoanException;
import com.ledger.company.exceptions.LedgerCoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    @Test
    void addLoan_shouldAddLoanSuccessfully() throws LedgerCoException {
        Bank bank = new Bank("IDIDI");
        bank.addLoan("DALE", 10000, 5, 4);
        assertEquals(1, bank.getLoans().size());
        assertEquals("DALE", bank.getLoans().get(0).getBorrowerName());
        assertEquals(10000, bank.getLoans().get(0).getPrincipalAmount());
        assertEquals(5, bank.getLoans().get(0).getPeriodInYears());
        assertEquals(4, bank.getLoans().get(0).getInterestRate());
    }

    @Test
    void addLoan_shouldThrowExceptionWhenAlreadyExists() throws LedgerCoException {
        Bank bank = new Bank("IDIDI");
        bank.addLoan("DALE", 10000, 5, 4);
        assertThrows(DuplicateLoanException.class, () -> bank.addLoan("DALE", 3000, 2, 3));
        assertEquals(1, bank.getLoans().size());
        assertEquals("DALE", bank.getLoans().get(0).getBorrowerName());
        assertEquals(10000, bank.getLoans().get(0).getPrincipalAmount());
        assertEquals(5, bank.getLoans().get(0).getPeriodInYears());
        assertEquals(4, bank.getLoans().get(0).getInterestRate());
    }
}