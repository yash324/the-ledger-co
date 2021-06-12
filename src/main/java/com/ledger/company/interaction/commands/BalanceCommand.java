package com.ledger.company.interaction.commands;

import com.ledger.company.exceptions.InvalidParameterException;
import com.ledger.company.exceptions.LedgerCoException;
import com.ledger.company.handler.CommandHandler;
import com.ledger.company.utils.MessageConstants;

import static com.ledger.company.utils.StringUtils.isInteger;
import static java.lang.Integer.parseInt;

public class BalanceCommand implements Command {
    private final CommandHandler commandHandler;

    public BalanceCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public String execute(String[] params) throws LedgerCoException {
        validateParams(params);
        return commandHandler.getBalance(params[0], params[1], parseInt(params[2])).toString();
    }
    public void validateParams(String[] params) throws LedgerCoException {
        if (params.length != 3) {
            throw new InvalidParameterException(String.format(MessageConstants.COMMAND_REQUIRES_N_PARAMETERS_ERROR, 3));
        }
        if(!isInteger(params[2])){
            throw new InvalidParameterException(MessageConstants.INVALID_INTEGER_ERROR);
        }
        if (parseInt(params[2]) < 0) {
            throw new InvalidParameterException(MessageConstants.NEGATIVE_VALUE_ERROR);
        }
    }
}
