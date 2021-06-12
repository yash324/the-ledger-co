package com.ledger.company.interaction.commands;

import com.ledger.company.exceptions.InvalidParameterException;
import com.ledger.company.exceptions.LedgerCoException;
import com.ledger.company.handler.CommandHandler;
import com.ledger.company.utils.MessageConstants;

import java.util.Arrays;

import static com.ledger.company.utils.StringUtils.isFloat;
import static java.lang.Float.parseFloat;

public class LoanCommand implements Command {
    private final CommandHandler commandHandler;
    public LoanCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public String execute(String[] params) throws LedgerCoException {
        validateParams(params);
        return commandHandler.createLoan(params[0], params[1], parseFloat(params[2]), parseFloat(params[3]), parseFloat(params[4]));
    }

    public void validateParams(String[] params) throws InvalidParameterException {
        if (params.length != 5) {
            throw new InvalidParameterException(String.format(MessageConstants.COMMAND_REQUIRES_N_PARAMETERS_ERROR, 5));
        }
        String[] paramsToValidate = Arrays.copyOfRange(params, 2, 5);
        for (String param : paramsToValidate) {
            if (!isFloat(param)) {
                throw new InvalidParameterException(MessageConstants.INVALID_DECIMAL_ERROR);
            }
            if (parseFloat(param) <= 0) {
                throw new InvalidParameterException(MessageConstants.NEGATIVE_VALUE_ERROR);
            }
        }
    }
}
