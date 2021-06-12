package com.ledger.company.interaction.commands;

import com.ledger.company.handler.CommandHandler;

public class BalanceCommand implements Command {
    private final CommandHandler commandHandler;

    public BalanceCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public String execute(String[] params) {
        // TODO
        return "";
    }
}
