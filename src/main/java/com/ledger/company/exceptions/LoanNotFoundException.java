package com.ledger.company.exceptions;

import com.ledger.company.utils.MessageConstants;

public class LoanNotFoundException extends LedgerCoException {
    public LoanNotFoundException(String bankName, String borrowerName) {
        super(String.format(MessageConstants.LOAN_NOT_FOUND_ERROR, borrowerName, bankName));
    }
}
