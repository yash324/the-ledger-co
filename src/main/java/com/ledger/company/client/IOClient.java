package com.ledger.company.client;

import com.ledger.company.exceptions.LedgerCoException;
import com.ledger.company.interaction.CommandFactory;
import com.ledger.company.utils.MessageConstants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;

public class IOClient {
    private final BufferedReader inputReader;
    private final BufferedWriter outputWriter;
    private final CommandFactory commandFactory;

    public IOClient(BufferedReader inputReader, BufferedWriter outputWriter, CommandFactory commandFactory) {
        this.inputReader = inputReader;
        this.commandFactory = commandFactory;
        this.outputWriter = outputWriter;
    }

    public void handleInput() throws IOException {
        int lineCount = 0;
        while (true) {
            String inputLine = this.inputReader.readLine();
            lineCount++;
            if (inputLine == null) {
                break;
            }
            inputLine = inputLine.trim();
            if (inputLine.isEmpty()) {
                continue;
            }
            processInputLine(inputLine, lineCount);
        }
    }

    private void processInputLine(String inputLine, int lineNumber) throws IOException {
        String[] inputChunks = inputLine.split(" ");

        String command = inputChunks[0];
        String[] params = Arrays.copyOfRange(inputChunks, 1, inputChunks.length);

        try {
            String result = commandFactory.executeCommand(command, params);
            if (result != null && !result.isEmpty()) {
                writeln(result);
            }
        } catch (Exception ex) {
            if (ex instanceof LedgerCoException) {
                writeln(String.format(MessageConstants.ERROR_MESSAGE, ex.getMessage(), lineNumber));
            } else {
                writeln(String.format(MessageConstants.INTERNAL_SERVER_ERROR, lineNumber));
            }
        }
    }

    private void writeln(final String output) throws IOException {
        outputWriter.write(output);
        outputWriter.newLine();
        outputWriter.flush();
    }
}
