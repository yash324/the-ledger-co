package com.ledger.company.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public abstract class ReadClient {
    private final BufferedReader inputReader;

    public ReadClient(BufferedReader inputReader) {
        this.inputReader = inputReader;
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

        // TODO: handle command
    }
}
