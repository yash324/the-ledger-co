package com.ledger.company.client;

import com.ledger.company.exceptions.LedgerCoException;
import com.ledger.company.handler.CommandHandler;
import com.ledger.company.interaction.CommandFactory;
import com.ledger.company.interaction.commands.Command;
import com.ledger.company.utils.MessageConstants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ReadClientTest {

    private static CommandFactory commandFactory;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static PrintStream sysOut;

    @AfterAll
    public static void revertStreams() {
        System.setOut(sysOut);
    }

    @BeforeEach
    public void resetStream() {
        outContent.reset();
    }

    @BeforeAll
    public static void setup() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
        CommandHandler commandHandler = new CommandHandler();
        commandFactory = CommandFactory.init(commandHandler);
    }

    @Test
    public void handleInput_shouldHandleInput() {
        BufferedReader inputReader = new BufferedReader(new StringReader(" "));
        ReadClient readClient = new FileReadClient(inputReader, commandFactory);

        assertDoesNotThrow(readClient::handleInput);
        // verify that inputReader is closed - subsequent calls to readline will throw IOException
        assertThrows(IOException.class, inputReader::readLine);
    }

    @Test
    public void processInputLine_commandSuccess() throws LedgerCoException {
        Command mockCommand = Mockito.mock(Command.class);
        commandFactory.addCommand("DUMMY", mockCommand);
        ReadClient readClient = new FileReadClient(new BufferedReader(new StringReader("DUMMY")), commandFactory);

        assertDoesNotThrow(readClient::handleInput);
        verify(mockCommand, times(1)).execute(any());
    }

    @Test
    public void processInputLineShouldPrintErrorIfCommandFailed() {
        ReadClient readClient = new FileReadClient(new BufferedReader(new StringReader("ADDBANK")), commandFactory);
        assertDoesNotThrow(readClient::handleInput);
        assertEquals("Error: Command ADDBANK not found | line:1" + System.lineSeparator(), outContent.toString());
    }

    @Test
    public void processInputLineShouldPrintErrorIfUnknownExceptionOccurred() throws LedgerCoException {
        Command mockCommand = Mockito.mock(Command.class);
        when(mockCommand.execute(any())).thenThrow(new NullPointerException());
        commandFactory.addCommand("DUMMY", mockCommand);
        ReadClient readClient = new FileReadClient(new BufferedReader(new StringReader("DUMMY")), commandFactory);

        assertDoesNotThrow(readClient::handleInput);
        verify(mockCommand, times(1)).execute(any());
        assertEquals(String.format(MessageConstants.INTERNAL_SERVER_ERROR, 1) + System.lineSeparator(), outContent.toString());
    }
}
