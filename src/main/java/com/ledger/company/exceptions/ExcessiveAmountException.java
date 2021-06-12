package com.ledger.company.exceptions;

import com.ledger.company.utils.MessageConstants;

public class ExcessiveAmountException extends LedgerCoException {
    public ExcessiveAmountException() {
        super(MessageConstants.EXCESSIVE_AMOUNT_ERROR);
    }
}
