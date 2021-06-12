package com.ledger.company.interaction.commands;

import com.ledger.company.exceptions.InvalidParameterException;
import com.ledger.company.exceptions.LedgerCoException;
import com.ledger.company.handler.CommandHandler;
import com.ledger.company.utils.MessageConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyZeroInteractions;

class LoanCommandTest {
    private static CommandHandler commandHandler;
    private static LoanCommand loanCommand;

    @BeforeEach
    public void init() {
        commandHandler = Mockito.mock(CommandHandler.class);
        loanCommand = new LoanCommand(commandHandler);
    }

    @Test
    public void execute_shouldThrowExceptionIfValidationFails() {
        String[] params = {"IDIDI", "Dale", "10000a", "3", "5.1"};
        assertThrows(InvalidParameterException.class, () -> loanCommand.execute(params));
        verifyZeroInteractions(commandHandler);
    }

    @Test
    public void execute_shouldRunSuccessfully() {
        String[] params = {"IDIDI", "Dale", "10000", "3", "5"};
        assertDoesNotThrow(() -> loanCommand.execute(params));
    }

    @Test
    public void validateParams_shouldValidateNegativeParams(){
        String[] amountInvalidParams = {"IDIDI", "Dale", "-14", "5", "5"};
        try {
            loanCommand.validateParams(amountInvalidParams);
            fail("should go to exception block");
        } catch (LedgerCoException ex) {
            assertTrue(ex instanceof InvalidParameterException);
            assertEquals(MessageConstants.NEGATIVE_VALUE_ERROR, ex.getMessage());
        }
    }

    @Test
    public void validateParams_shouldValidateFloatValues() {
        String[] rateInvalidParams = {"IDIDI", "Dale", "10000", "5", "a.0"};
        try {
            loanCommand.validateParams(rateInvalidParams);
            fail("should go to exception block");
        } catch (LedgerCoException ex) {
            assertTrue(ex instanceof InvalidParameterException);
            assertEquals(MessageConstants.INVALID_DECIMAL_ERROR, ex.getMessage());
        }
    }

    @Test
    public void validateParams_shouldValidateArgumentCount() {
        CommandHandler commandHandler = Mockito.mock(CommandHandler.class);
        loanCommand = new LoanCommand(commandHandler);
        String[] params = {"1", "2"};
        try {
            loanCommand.validateParams(params);
            fail("should go to exception block");
        } catch (LedgerCoException ex) {
            assertTrue(ex instanceof InvalidParameterException);
            assertEquals(String.format(MessageConstants.COMMAND_REQUIRES_N_PARAMETERS_ERROR, 5), ex.getMessage());
        }
    }

}