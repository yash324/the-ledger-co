package com.ledger.company.interaction;

import com.ledger.company.exceptions.CommandNotFoundException;
import com.ledger.company.handler.CommandHandler;
import com.ledger.company.interaction.commands.Command;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CommandFactoryTest {
    private static CommandFactory commandFactory;

    @BeforeAll
    public static void setupCommandFactory() {
        CommandHandler commandHandler = new CommandHandler();
        commandFactory = CommandFactory.init(commandHandler);
    }

    @Test
    public void init_shouldInitializeAllCommands() {
        assertTrue(commandFactory.getCommands().containsKey("BALANCE"));
        assertTrue(commandFactory.getCommands().containsKey("PAYMENT"));
        assertTrue(commandFactory.getCommands().containsKey("LOAN"));
    }

    @Test
    public void executeCommand_shouldThrowErrorForInvalidCommand() {
        assertThrows(CommandNotFoundException.class, () -> commandFactory.executeCommand("PENALIZE", null));
    }

    @Test
    public void executeCommand_shouldExecuteCommand() {
        Command mockCommand = Mockito.mock(Command.class);
        commandFactory.addCommand("DUMMY", mockCommand);
        assertDoesNotThrow(() -> commandFactory.executeCommand("DUMMY", null));
        verify(mockCommand, times(1)).execute(any());
    }
}