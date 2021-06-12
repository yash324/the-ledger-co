package com.ledger.company.client;

import com.ledger.company.exceptions.LedgerCoException;
import com.ledger.company.interaction.CommandFactory;
import com.ledger.company.utils.MessageConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public abstract class ReadClient {
    private final BufferedReader inputReader;
    private final CommandFactory commandFactory;

    public ReadClient(BufferedReader inputReader, CommandFactory commandFactory) {
        this.inputReader = inputReader;
        this.commandFactory = commandFactory;
    }

    public void handleInput() throws IOException {
        int lineCount = 0;
        try {
            while (true) {
                String inputLine = this.inputReader.readLine();
                lineCount++;
                if(inputLine == null) {
                    break;
                }
                inputLine = inputLine.trim();
                if(inputLine.isEmpty()) {
                    continue;
                }
                processInputLine(inputLine, lineCount);
            }
        } finally {
            inputReader.close();
        }
    }

    private void processInputLine(String inputLine, int lineNumber) {
        String[] inputChunks = inputLine.split(" ");

        String command = inputChunks[0];
        String[] params = Arrays.copyOfRange(inputChunks, 1, inputChunks.length);

        try {
            String result = commandFactory.executeCommand(command, params);
            if (result != null && !result.isEmpty()){
                System.out.println(result);
            }
        } catch (Exception ex) {
            if (ex instanceof LedgerCoException) {
                System.out.println(String.format(MessageConstants.ERROR_MESSAGE, ex.getMessage(), lineNumber));
            } else {
                System.out.println(String.format(MessageConstants.INTERNAL_SERVER_ERROR, lineNumber));
            }
        }
    }
}
