package com.ledger.company.client;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadClientTest {

    @Test
    public void handleInput_shouldHandleInput() {
        BufferedReader inputReader = new BufferedReader(new StringReader(" "));
        ReadClient readClient = new FileReadClient(inputReader);
        assertDoesNotThrow(readClient::handleInput);
        // verify that inputReader is closed - subsequent calls to readline will throw IOException
        assertThrows(IOException.class, inputReader::readLine);
    }
}
