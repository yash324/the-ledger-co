package com.ledger.company.interaction;

import com.ledger.company.exceptions.CommandNotFoundException;
import com.ledger.company.handler.CommandHandler;
import com.ledger.company.interaction.commands.BalanceCommand;
import com.ledger.company.interaction.commands.Command;
import com.ledger.company.interaction.commands.LoanCommand;
import com.ledger.company.interaction.commands.PaymentCommand;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CommandFactory {
    private final Map<String, Command> commands;

    private CommandFactory() {
        commands = new HashMap<>();
    }

    public static CommandFactory init(CommandHandler commandHandler) {
        final CommandFactory cf = new CommandFactory();
        cf.addCommand("LOAN", new LoanCommand(commandHandler));
        cf.addCommand("PAYMENT", new PaymentCommand(commandHandler));
        cf.addCommand("BALANCE", new BalanceCommand(commandHandler));
        return cf;
    }

    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    public void executeCommand(String name, String[] params) throws CommandNotFoundException {
        if(commands.containsKey(name)) {
            commands.get(name).execute(params);
        } else {
            throw new CommandNotFoundException(name);
        }
    }
}
