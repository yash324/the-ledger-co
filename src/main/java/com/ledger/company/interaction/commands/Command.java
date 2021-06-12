package com.ledger.company.interaction.commands;

import com.ledger.company.exceptions.LedgerCoException;

public interface Command {
    String execute(String[] params) throws LedgerCoException;
}
