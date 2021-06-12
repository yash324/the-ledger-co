package com.ledger.company;

import com.ledger.company.client.FileReadClient;
import com.ledger.company.client.ReadClient;
import com.ledger.company.handler.CommandHandler;
import com.ledger.company.interaction.CommandFactory;
import com.ledger.company.utils.MessageConstants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        CommandHandler commandHandler = new CommandHandler();
        CommandFactory commandFactory = CommandFactory.init(commandHandler);
        try {
            ReadClient readClient = new FileReadClient(new BufferedReader(new FileReader(args[0])), commandFactory);
            readClient.handleInput();
        } catch (FileNotFoundException ex) {
            System.out.println(MessageConstants.INPUT_FILE_NOT_FOUND);
        } catch (IOException ex) {
            System.out.println(MessageConstants.TRY_AGAIN);
        }
    }
}
