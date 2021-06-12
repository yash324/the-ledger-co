package com.ledger.company.exceptions;

import com.ledger.company.utils.MessageConstants;

public class DuplicateLoanException extends LedgerCoException {

    public DuplicateLoanException(){
        super(MessageConstants.MULTIPLE_LOANS_ERROR_MSG);
    }
}
