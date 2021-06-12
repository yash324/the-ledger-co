package com.ledger.company.utils;

public interface MessageConstants {
    String INPUT_FILE_NOT_FOUND = "Sorry! the supplied input file was not found!";
    String TRY_AGAIN = "Something went wrong. Please try again!";
    String COMMAND_NOT_FOUND = "Command %s not found";
    String ERROR_MESSAGE = "Error: %s | line:%d";
    String INTERNAL_SERVER_ERROR = "Internal Server Error | line:%d";
    String VALID_DECIMALS = "Please enter valid decimals";
    String COMMAND_REQUIRES_N_PARAMETERS = "This command requires %d parameters";
    String MULTIPLE_LOANS_ERROR_MSG = "Cannot have multiple loans for a customer at a single bank";
    String CANNOT_BE_ZERO = "Please enter non-zero value";
}
