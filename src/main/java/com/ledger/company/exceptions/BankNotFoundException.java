package com.ledger.company.exceptions;

import com.ledger.company.utils.MessageConstants;

public class BankNotFoundException extends LedgerCoException {

    public BankNotFoundException(String bankName) {
        super(String.format(MessageConstants.BANK_NOT_FOUND_ERROR, bankName));
    }
}
