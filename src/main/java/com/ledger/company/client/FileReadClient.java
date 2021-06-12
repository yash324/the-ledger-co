package com.ledger.company.client;

import com.ledger.company.interaction.CommandFactory;

import java.io.BufferedReader;

public class FileReadClient extends ReadClient {
    public FileReadClient(BufferedReader inputReader, CommandFactory commandFactory) {
        super(inputReader, commandFactory);
    }
}
