package com.ledger.company.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LumpSumPayment {
    private final Integer emiNumber;
    private final Float amount;
}
