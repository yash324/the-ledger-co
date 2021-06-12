package com.ledger.company.domain;

import com.ledger.company.exceptions.DuplicateLoanException;
import com.ledger.company.exceptions.LedgerCoException;
import com.ledger.company.exceptions.LoanNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankTest {
    private Bank bank;

    @BeforeEach
    public void setup() throws LedgerCoException {
        this.bank = new Bank("IDIDI");
        this.bank.addLoan("DALE", 10000, 5, 10);
    }

    @Test
    void addLoan_shouldAddLoanSuccessfully() {
        assertEquals(1, bank.getLoans().size());
        assertEquals("DALE", bank.getLoans().get(0).getBorrowerName());
        assertEquals(10000, bank.getLoans().get(0).getPrincipalAmount());
        assertEquals(5, bank.getLoans().get(0).getPeriodInYears());
        assertEquals(10, bank.getLoans().get(0).getInterestRate());
    }

    @Test
    void addLoan_shouldThrowExceptionWhenAlreadyExists() {
        assertThrows(DuplicateLoanException.class, () -> bank.addLoan("DALE", 3000, 2, 3));
        assertEquals(1, bank.getLoans().size());
        assertEquals("DALE", bank.getLoans().get(0).getBorrowerName());
        assertEquals(10000, bank.getLoans().get(0).getPrincipalAmount());
        assertEquals(5, bank.getLoans().get(0).getPeriodInYears());
        assertEquals(10, bank.getLoans().get(0).getInterestRate());
    }

    @Test
    void addLumpSumpPayment_shouldAddPaymentSuccessfully() throws LedgerCoException {
        bank.addLumpSumPayment("DALE", 5000, 5);
        assertEquals(1, bank.getLoans().size());
        assertEquals(1, bank.getLoans().get(0).getLumpSumPayments().size());
        assertEquals(5, bank.getLoans().get(0).getLumpSumPayments().get(0).getEmiNumber());
        assertEquals(5000, bank.getLoans().get(0).getLumpSumPayments().get(0).getAmount());
    }

    @Test
    void addLumpSumPayment_shouldThrowExceptionWhenLoanDoesntExist() {
        assertThrows(LoanNotFoundException.class, () -> bank.addLumpSumPayment("HARRY", 5000, 5));
        assertEquals(1, bank.getLoans().size());
        assertEquals("DALE", bank.getLoans().get(0).getBorrowerName());
        assertEquals(10000, bank.getLoans().get(0).getPrincipalAmount());
        assertEquals(5, bank.getLoans().get(0).getPeriodInYears());
        assertEquals(10, bank.getLoans().get(0).getInterestRate());
    }

    @Test
    void getLoanBalance_shouldReturnSuccessfully() throws LedgerCoException {
        Balance balance = bank.getLoanBalance("DALE", 9);
        assertEquals("IDIDI", balance.getBankName());
        assertEquals("DALE", balance.getBorrowerName());
        assertEquals(2250, balance.getAmountPaid());
        assertEquals(51, balance.getEmisRemaining());
    }

    @Test
    void getLoanBalance_shouldThrowExceptionWhenLoanDoesntExist() {
        assertThrows(LoanNotFoundException.class, () -> bank.getLoanBalance("HARRY", 5));
    }
}