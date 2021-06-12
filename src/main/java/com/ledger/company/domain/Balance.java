package com.ledger.company.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Balance {
    @Setter
    private String bankName;
    private final String borrowerName;
    private final Integer amountPaid;
    private final int emisRemaining;

    @Override
    public String toString() {
        return String.format("%s %s %s %s", bankName, borrowerName, amountPaid, emisRemaining);
    }
}
