package com.ledger.company;

import com.ledger.company.utils.MessageConstants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static PrintStream sysOut;

    @BeforeAll
    public static void setupStreams() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    public static void revertStreams() {
        System.setOut(sysOut);
    }

    @BeforeEach
    public void resetStream() {
        outContent.reset();
    }

    @Test
    void mainShouldPrintErrorIfFileNotFound() {
        App.main(new String[]{"some-non-existent-file.txt"});
        assertEquals(MessageConstants.INPUT_FILE_NOT_FOUND + System.lineSeparator(), outContent.toString());
    }

    @Test
    void mainShouldPrintErrorIfIOExceptionOccurs() {
        // TODO
    }

    @Test
    void mainShouldProcessInput() throws IOException {
        File tempFile = File.createTempFile("test", ".txt");
        PrintWriter writer = new PrintWriter(tempFile.getAbsolutePath(), "UTF-8");
        writer.println("exit");
        writer.close();
        assertDoesNotThrow(() -> App.main(new String[]{tempFile.getAbsolutePath()}));
        assertTrue(tempFile.delete());
    }
}