package com.ledger.company.exceptions;

import com.ledger.company.utils.MessageConstants;

public class BackDatedPaymentException extends LedgerCoException {
    public BackDatedPaymentException() {
        super(MessageConstants.BACKDATED_PAYMENT_ERROR);
    }
}
