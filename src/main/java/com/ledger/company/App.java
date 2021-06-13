package com.ledger.company;

import com.ledger.company.client.IOClient;
import com.ledger.company.handler.CommandHandler;
import com.ledger.company.interaction.CommandFactory;
import com.ledger.company.utils.MessageConstants;

import java.io.*;

public class App {

    public static void main(String[] args) {
        CommandHandler commandHandler = new CommandHandler();
        CommandFactory commandFactory = CommandFactory.init(commandHandler);
        try (BufferedReader inputReader = new BufferedReader(new FileReader(args[0]))){
            BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(System.out));
            IOClient ioClient = new IOClient(inputReader, outputWriter, commandFactory);
            ioClient.handleInput();
        } catch (FileNotFoundException ex) {
            System.out.println(MessageConstants.INPUT_FILE_NOT_FOUND_ERROR);
        } catch (IOException ex) {
            System.out.println(MessageConstants.TRY_AGAIN_ERROR);
        }
    }
}
