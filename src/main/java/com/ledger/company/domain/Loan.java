package com.ledger.company.domain;

import com.ledger.company.exceptions.BackDatedPaymentException;
import com.ledger.company.exceptions.ExcessiveAmountException;
import com.ledger.company.exceptions.LedgerCoException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Loan {
    private final String borrowerName;
    private final Float principalAmount;
    private final Float periodInYears;
    private final Float interestRate;
    private final List<LumpSumPayment> lumpSumPayments;

    public Loan(String borrowerName, float principalAmount, float periodInYears, float interestRate) {
        this.borrowerName = borrowerName;
        this.principalAmount = principalAmount;
        this.periodInYears = periodInYears;
        this.interestRate = interestRate;
        this.lumpSumPayments = new ArrayList<>();
    }

    public void addLumpSumPayment(float amount, int emiNumber) throws LedgerCoException {
        float totalPayableAmount = getTotalPayableAmount();
        if (lumpSumPayments.size() > 0 && lumpSumPayments.get(lumpSumPayments.size() - 1).getEmiNumber() > emiNumber) {
            throw new BackDatedPaymentException();
        }
        if (getAmountPaid(emiNumber) + amount > totalPayableAmount) {
            throw new ExcessiveAmountException();
        }
        lumpSumPayments.add(new LumpSumPayment(emiNumber, amount));
    }

    public float getAmountPaid(int emiNumber) {
        float totalPayableAmount = getTotalPayableAmount();
        Float lumpSumPaymentsTotal = lumpSumPayments.stream()
                .filter(lumpSumPayment -> lumpSumPayment.getEmiNumber() <= emiNumber)
                .map(LumpSumPayment::getAmount)
                .reduce(0f, Float::sum);
        return Math.min(getEmiAmount(totalPayableAmount) * emiNumber + lumpSumPaymentsTotal, totalPayableAmount);
    }

    private float getEmiAmount(float totalPayableAmount){
        return (float) Math.ceil(totalPayableAmount / (periodInYears * 12));
    }

    private float getTotalPayableAmount(){
        return principalAmount + (principalAmount * periodInYears * interestRate / 100);
    }

}
