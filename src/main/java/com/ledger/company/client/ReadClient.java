package com.ledger.company.client;

import com.ledger.company.exceptions.CommandNotFoundException;
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
        try {
            while (true) {
                String inputLine = this.inputReader.readLine();
                if(inputLine == null) {
                    break;
                }
                inputLine = inputLine.trim();
                if(inputLine.isEmpty()) {
                    continue;
                }
                processInputLine(inputLine);
            }
        } finally {
            inputReader.close();
        }
    }

    private void processInputLine(String inputLine) {
        String[] inputChunks = inputLine.split(" ");

        String command = inputChunks[0];
        String[] params = Arrays.copyOfRange(inputChunks, 1, inputChunks.length);

        try {
            commandFactory.executeCommand(command, params);
        } catch (Exception ex) {
            if (ex instanceof CommandNotFoundException) {
                System.out.println(String.format(MessageConstants.ERROR_MESSAGE, ex.getMessage()));
            } else {
                System.out.println(MessageConstants.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
