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

class PaymentCommandTest {
    private static CommandHandler commandHandler;
    private static PaymentCommand paymentCommand;

    @BeforeEach
    public void init() {
        commandHandler = Mockito.mock(CommandHandler.class);
        paymentCommand = new PaymentCommand(commandHandler);
    }

    @Test
    public void execute_shouldThrowExceptionIfValidationFails() {
        String[] params = {"IDIDI", "Dale", "10000a", "3", "5.1"};
        assertThrows(InvalidParameterException.class, () -> paymentCommand.execute(params));
        verifyZeroInteractions(commandHandler);
    }

    @Test
    public void execute_shouldRunSuccessfully() {
        String[] params = {"IDIDI", "Dale", "10000", "3"};
        assertDoesNotThrow(() -> paymentCommand.execute(params));
    }

    @Test
    public void validateParams_shouldValidateArgumentCount() {
        CommandHandler commandHandler = Mockito.mock(CommandHandler.class);
        paymentCommand = new PaymentCommand(commandHandler);
        String[] params = {"1", "2"};
        try {
            paymentCommand.validateParams(params);
            fail("should go to exception block");
        } catch (LedgerCoException ex) {
            assertTrue(ex instanceof InvalidParameterException);
            assertEquals(String.format(MessageConstants.COMMAND_REQUIRES_N_PARAMETERS_ERROR, 4), ex.getMessage());
        }
    }

    @Test
    public void validateParams_shouldValidateIntEmiCount() {
        String[] amountInvalidParams = {"IDIDI", "Dale", "300", "x"};
        try {
            paymentCommand.validateParams(amountInvalidParams);
            fail("should go to exception block");
        } catch (LedgerCoException ex) {
            assertTrue(ex instanceof InvalidParameterException);
            assertEquals(MessageConstants.INVALID_INTEGER_ERROR, ex.getMessage());
        }
    }

    @Test
    public void validateParams_shouldValidateFloatAmount() {
        String[] amountInvalidParams = {"IDIDI", "Dale", "a.0", "3"};
        try {
            paymentCommand.validateParams(amountInvalidParams);
            fail("should go to exception block");
        } catch (LedgerCoException ex) {
            assertTrue(ex instanceof InvalidParameterException);
            assertEquals(MessageConstants.INVALID_DECIMAL_ERROR, ex.getMessage());
        }
    }

    @Test
    public void validateParams_shouldValidateNegativeAmountParam(){
        String[] amountInvalidParams = {"IDIDI", "Dale", "-14", "5"};
        try {
            paymentCommand.validateParams(amountInvalidParams);
            fail("should go to exception block");
        } catch (LedgerCoException ex) {
            assertTrue(ex instanceof InvalidParameterException);
            assertEquals(MessageConstants.NEGATIVE_VALUE_ERROR, ex.getMessage());
        }
    }

    @Test
    public void validateParams_shouldValidateNegativeEmiCountParam(){
        String[] emiCountInvalidParams = {"IDIDI", "Dale", "14000", "-2"};
        try {
            paymentCommand.validateParams(emiCountInvalidParams);
            fail("should go to exception block");
        } catch (LedgerCoException ex) {
            assertTrue(ex instanceof InvalidParameterException);
            assertEquals(MessageConstants.NEGATIVE_VALUE_ERROR, ex.getMessage());
        }
    }
}