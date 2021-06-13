package com.ledger.company.client;

import com.ledger.company.exceptions.LedgerCoException;
import com.ledger.company.handler.CommandHandler;
import com.ledger.company.interaction.CommandFactory;
import com.ledger.company.interaction.commands.Command;
import com.ledger.company.utils.MessageConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class IOClientTest {

    private static CommandFactory commandFactory;

    @BeforeAll
    public static void setup() {
        CommandHandler commandHandler = new CommandHandler();
        commandFactory = CommandFactory.init(commandHandler);
    }

    @Test
    public void processInputLine_commandOutputSuccess() {
        StringReader stringReader = new StringReader("LOAN IDIDI Dale 5000 1 6 \n BALANCE IDIDI Dale 3");
        StringWriter stringWriter = new StringWriter();
        IOClient ioClient = new IOClient(new BufferedReader(stringReader), new BufferedWriter(stringWriter), commandFactory);

        assertDoesNotThrow(ioClient::handleInput);
        assertEquals("IDIDI Dale 1326 9" + System.lineSeparator(), stringWriter.toString());
    }

    @Test
    public void processInputLineShouldPrintErrorWithLineNumberIfCommandFailed() {
        StringReader stringReader = new StringReader("\nADDBANK");
        StringWriter stringWriter = new StringWriter();
        IOClient ioClient = new IOClient(new BufferedReader(stringReader), new BufferedWriter(stringWriter), commandFactory);

        assertDoesNotThrow(ioClient::handleInput);
        assertEquals("Error: Command ADDBANK not found | line:2" + System.lineSeparator(), stringWriter.toString());
    }

    @Test
    public void processInputLineShouldPrintErrorIfUnknownExceptionOccurred() throws LedgerCoException {
        Command mockCommand = Mockito.mock(Command.class);
        StringReader stringReader = new StringReader("DUMMY");
        StringWriter stringWriter = new StringWriter();

        when(mockCommand.execute(any())).thenThrow(new NullPointerException());
        commandFactory.addCommand("DUMMY", mockCommand);
        IOClient ioClient = new IOClient(new BufferedReader(stringReader), new BufferedWriter(stringWriter), commandFactory);

        assertDoesNotThrow(ioClient::handleInput);
        verify(mockCommand, times(1)).execute(any());
        assertEquals(String.format(MessageConstants.INTERNAL_SERVER_ERROR, 1) + System.lineSeparator(), stringWriter.toString());
    }
}
