package com.ledger.company.interaction.commands;

import com.ledger.company.domain.Balance;
import com.ledger.company.exceptions.InvalidParameterException;
import com.ledger.company.exceptions.LedgerCoException;
import com.ledger.company.handler.CommandHandler;
import com.ledger.company.utils.MessageConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BalanceCommandTest {
    private static CommandHandler commandHandler;
    private static BalanceCommand balanceCommand;

    @BeforeEach
    public void init() {
        commandHandler = Mockito.mock(CommandHandler.class);
        balanceCommand = new BalanceCommand(commandHandler);
    }

    @Test
    public void validateParams_shouldValidateArgumentCount() {
        CommandHandler commandHandler = Mockito.mock(CommandHandler.class);
        balanceCommand = new BalanceCommand(commandHandler);
        String[] params = {"1", "2"};
        try {
            balanceCommand.validateParams(params);
            fail("should go to exception block");
        } catch (LedgerCoException ex) {
            assertTrue(ex instanceof InvalidParameterException);
            assertEquals(String.format(MessageConstants.COMMAND_REQUIRES_N_PARAMETERS_ERROR, 3), ex.getMessage());
        }
    }

    @Test
    public void validateParams_shouldValidateIntEmiCount() {
        String[] emiCountInvalidParams = {"IDIDI", "DALE", "x"};
        try {
            balanceCommand.validateParams(emiCountInvalidParams);
            fail("should go to exception block");
        } catch (LedgerCoException ex) {
            assertTrue(ex instanceof InvalidParameterException);
            assertEquals(MessageConstants.INVALID_INTEGER_ERROR, ex.getMessage());
        }
    }

    @Test
    public void validateParams_shouldValidateNegativeEmiCountParam() {
        String[] emiCountInvalidParams = {"IDIDI", "DALE", "-2"};
        try {
            balanceCommand.validateParams(emiCountInvalidParams);
            fail("should go to exception block");
        } catch (LedgerCoException ex) {
            assertTrue(ex instanceof InvalidParameterException);
            assertEquals(MessageConstants.NEGATIVE_VALUE_ERROR, ex.getMessage());
        }
    }

    @Test
    public void execute_shouldThrowExceptionWhenValidationFails() {
        String[] params = {"IDIDI", "DALE", "10000a", "3", "5.1"};
        assertThrows(InvalidParameterException.class, () -> balanceCommand.execute(params));
        verifyZeroInteractions(commandHandler);
    }

    @Test
    public void execute_shouldGetBalance() throws LedgerCoException {
        String[] params = {"IDIDI", "DALE", "3"};
        Balance mockBalance = new Balance("IDIDI", "DALE", 5000, 4);
        when(commandHandler.getBalance(params[0], params[1], 3)).thenReturn(mockBalance);
        String response = balanceCommand.execute(params);
        assertEquals("IDIDI DALE 5000 4", response);
        verify(commandHandler, times(1)).getBalance("IDIDI", "DALE", 3);
    }
}