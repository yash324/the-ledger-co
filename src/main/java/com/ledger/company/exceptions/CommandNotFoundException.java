package com.ledger.company.exceptions;

import com.ledger.company.utils.MessageConstants;

public class CommandNotFoundException extends LedgerCoException {

    public CommandNotFoundException(String commandName) {
        super(String.format(MessageConstants.COMMAND_NOT_FOUND, commandName));
    }

}
