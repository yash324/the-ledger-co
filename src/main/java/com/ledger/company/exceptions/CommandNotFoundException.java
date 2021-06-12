package com.ledger.company.exceptions;

import com.ledger.company.utils.MessageConstants;

public class CommandNotFoundException extends Exception {
    private final String name;

    public CommandNotFoundException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return String.format(MessageConstants.COMMAND_NOT_FOUND, name);
    }
}
