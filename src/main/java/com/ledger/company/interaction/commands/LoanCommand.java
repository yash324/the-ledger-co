package com.ledger.company.interaction.commands;

import com.ledger.company.handler.CommandHandler;

public class LoanCommand implements Command {
    private final CommandHandler commandHandler;
    public LoanCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void execute(String[] params) {
      // TODO
    }
}
