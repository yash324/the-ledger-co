package com.ledger.company.exceptions;

import com.ledger.company.utils.MessageConstants;

public class DuplicateLoanException extends LedgerCoException {

    public DuplicateLoanException(String bankName, String borrowerName){
        super(String.format(MessageConstants.MULTIPLE_LOANS_ERROR, borrowerName, bankName));
    }
}
