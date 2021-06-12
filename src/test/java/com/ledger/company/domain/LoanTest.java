package com.ledger.company.domain;

import com.ledger.company.exceptions.BackDatedPaymentException;
import com.ledger.company.exceptions.ExcessiveAmountException;
import com.ledger.company.exceptions.LedgerCoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    @Test
    void getAmountPaid_shouldReturnAmountPaidTillCurrentEmiWhenNoLumpSumPaymentDone() {
        Loan loan = new Loan("DALE", 10000, 5, 10);
        assertEquals(0, loan.getAmountPaid(0));
        assertEquals(250, loan.getAmountPaid(1));
        assertEquals(1250, loan.getAmountPaid(5));
        assertEquals(15000, loan.getAmountPaid(60));
        assertEquals(15000, loan.getAmountPaid(61));
    }

    @Test
    void getAmountPaid_shouldReturnAmountPaidTillCurrentEmiWhenLumpSumPaymentsDone() throws LedgerCoException {
        Loan loan = new Loan("DALE", 10000, 5, 10);
        assertEquals(250, loan.getAmountPaid(1));
        loan.addLumpSumPayment(1000, 1);
        assertEquals(0, loan.getAmountPaid(0));
        assertEquals(1250, loan.getAmountPaid(1));
        loan.addLumpSumPayment(5000, 3);
        assertEquals(8750, loan.getAmountPaid(11));
        assertEquals(15000, loan.getAmountPaid(36));
        assertEquals(15000, loan.getAmountPaid(70));
    }

    @Test
    void addLumpSumpPayment_shouldThrowExceptionForBackdatedPayment() throws LedgerCoException {
        Loan loan = new Loan("DALE", 10000, 5, 10);
        loan.addLumpSumPayment(5000, 5);
        assertThrows(BackDatedPaymentException.class, () -> loan.addLumpSumPayment(1000, 3));
        assertEquals(1, loan.getLumpSumPayments().size());
        assertEquals(5, loan.getLumpSumPayments().get(0).getEmiNumber());
        assertEquals(5000, loan.getLumpSumPayments().get(0).getAmount());
    }

    @Test
    void addLumpSumpPayment_shouldThrowExceptionIfAmountExceedsDueAmount() {
        Loan loan = new Loan("DALE", 10000, 5, 10);
        assertThrows(ExcessiveAmountException.class, () -> loan.addLumpSumPayment(14000, 5));
        assertTrue(loan.getLumpSumPayments().isEmpty());
    }

    @Test
    void addLumpSumpPayment_shouldAddSuccessfully() throws LedgerCoException {
        Loan loan = new Loan("DALE", 10000, 5, 10);
        float currentEmiPaid = loan.getAmountPaid(1);
        loan.addLumpSumPayment(5000, 1);
        assertEquals(currentEmiPaid + 5000, loan.getAmountPaid(1));
    }

    @Test
    void getBalance_shouldReturnSuccessfully() throws LedgerCoException {
        Loan loan = new Loan("DALE", 10000, 5, 10);
        assertEquals(0, loan.getBalance(0).getAmountPaid());
        assertEquals(60, loan.getBalance(0).getEmisRemaining());
        loan.addLumpSumPayment(5000, 1);
        assertEquals(5250, loan.getBalance(1).getAmountPaid());
        assertEquals(39, loan.getBalance(1).getEmisRemaining());
        loan.addLumpSumPayment(9499, 2);
        assertEquals(14999, loan.getBalance(2).getAmountPaid());
        assertEquals(1, loan.getBalance(2).getEmisRemaining());
        assertEquals(15000, loan.getBalance(3).getAmountPaid());
        assertEquals(0, loan.getBalance(3).getEmisRemaining());
    }

}