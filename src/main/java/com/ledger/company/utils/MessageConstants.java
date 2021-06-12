package com.ledger.company.utils;

public interface MessageConstants {
    String INPUT_FILE_NOT_FOUND_ERROR = "Sorry! the supplied input file was not found!";
    String ERROR_MESSAGE = "Error: %s | line:%d";
    String TRY_AGAIN_ERROR = "Something went wrong. Please try again!";
    String COMMAND_NOT_FOUND_ERROR = "Command %s not found";
    String INTERNAL_SERVER_ERROR = "Internal Server Error | line:%d";
    String INVALID_DECIMAL_ERROR = "Please enter valid decimals";
    String COMMAND_REQUIRES_N_PARAMETERS_ERROR = "This command requires %d parameters";
    String MULTIPLE_LOANS_ERROR = "Cannot have multiple loans for borrower %s at bank %s";
    String NEGATIVE_VALUE_ERROR = "Please enter non-negative values only";
    String INVALID_INTEGER_ERROR = "Please enter valid integer value";
    String BANK_NOT_FOUND_ERROR = "Bank %s not found";
    String LOAN_NOT_FOUND_ERROR = "Loan not found for borrower %s at bank %s";
    String BACKDATED_PAYMENT_ERROR = "Lump Sum Payment cannot be made for an EMI prior to last payment";
    String EXCESSIVE_AMOUNT_ERROR = "Lump Sum Payment Amount exceeds remaining payable amount";
}
