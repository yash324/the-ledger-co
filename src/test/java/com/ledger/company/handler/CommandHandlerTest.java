package com.ledger.company.handler;

import com.ledger.company.domain.Loan;
import com.ledger.company.domain.LumpSumPayment;
import com.ledger.company.exceptions.BankNotFoundException;
import com.ledger.company.exceptions.LedgerCoException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandHandlerTest {

    @Test
    void addLoan_shouldAddBankAndLoanIfNonExistent() throws LedgerCoException {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.createLoan("IDIDI", "DALE", 10000, 5,4 );
        assertEquals(1, commandHandler.getBanks().size());
        assertEquals("IDIDI", commandHandler.getBanks().get(0).getName());
        List<Loan> loans = commandHandler.getBanks().get(0).getLoans();
        assertEquals(1, loans.size());
        assertEquals("DALE", loans.get(0).getBorrowerName());
        assertEquals(10000, loans.get(0).getPrincipalAmount());
        assertEquals(5, loans.get(0).getPeriodInYears());
        assertEquals(4, loans.get(0).getInterestRate());
    }

    @Test
    void addLoan_shouldAddLoanIfBankExists() throws LedgerCoException {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.createLoan("IDIDI", "HARRY", 2000, 2,3 );

        commandHandler.createLoan("IDIDI", "DALE", 10000, 5,4 );

        assertEquals(1, commandHandler.getBanks().size());
        assertEquals("IDIDI", commandHandler.getBanks().get(0).getName());
        List<Loan> loans = commandHandler.getBanks().get(0).getLoans();
        assertEquals(2, loans.size());
        Loan loan = loans.stream().filter(l -> l.getBorrowerName().equalsIgnoreCase("DALE")).findFirst().get();
        assertEquals("DALE", loan.getBorrowerName());
        assertEquals(10000, loan.getPrincipalAmount());
        assertEquals(5, loan.getPeriodInYears());
        assertEquals(4, loan.getInterestRate());
    }

    @Test
    void payLumpSum_shouldThrowExceptionIfBankDoesntExist() throws LedgerCoException {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.createLoan("IDIDI", "HARRY", 2000, 2,3 );
        assertThrows(BankNotFoundException.class, () -> commandHandler.payLumpSum("MBI", "DALE", 10000, 5));
    }

    @Test
    void payLumpSum_shouldAddPayment() throws LedgerCoException {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.createLoan("IDIDI", "HARRY", 2000, 2,3 );
        commandHandler.payLumpSum("IDIDI", "HARRY", 1000, 5);
        List<LumpSumPayment> lumpSumPayments = commandHandler.getBanks().get(0).getLoans().get(0).getLumpSumPayments();
        assertEquals(1, lumpSumPayments.size());
        assertEquals(1000, lumpSumPayments.get(0).getAmount());
        assertEquals(5, lumpSumPayments.get(0).getEmiNumber());
    }

}