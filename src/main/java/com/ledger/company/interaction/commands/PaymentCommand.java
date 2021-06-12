package com.ledger.company.interaction.commands;

import com.ledger.company.exceptions.InvalidParameterException;
import com.ledger.company.exceptions.LedgerCoException;
import com.ledger.company.handler.CommandHandler;
import com.ledger.company.utils.MessageConstants;

import static com.ledger.company.utils.StringUtils.isFloat;
import static com.ledger.company.utils.StringUtils.isInteger;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class PaymentCommand implements Command {
    private final CommandHandler commandHandler;

    public PaymentCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public String execute(String[] params) throws LedgerCoException {
        validateParams(params);
        return commandHandler.payLumpSum(params[0], params[1], parseFloat(params[2]), parseInt(params[3]));
    }

    public void validateParams(String[] params) throws LedgerCoException {
        if (params.length != 4) {
            throw new InvalidParameterException(String.format(MessageConstants.COMMAND_REQUIRES_N_PARAMETERS_ERROR, 4));
        }
        if (!isFloat(params[2])) {
            throw new InvalidParameterException(MessageConstants.INVALID_DECIMAL_ERROR);
        }
        if(!isInteger(params[3])){
            throw new InvalidParameterException(MessageConstants.INVALID_INTEGER_ERROR);
        }
        if (parseFloat(params[2]) < 0 || parseInt(params[3]) < 0) {
            throw new InvalidParameterException(MessageConstants.NEGATIVE_VALUE_ERROR);
        }
    }

}
