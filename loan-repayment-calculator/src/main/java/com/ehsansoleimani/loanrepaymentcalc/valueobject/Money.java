package com.ehsansoleimani.loanrepaymentcalc.valueobject;

import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by ehsan on 8/2/19.
 */
@Value
public class Money {
    private static DecimalFormat formatter = new DecimalFormat();

    static {
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(0);
        formatter.setGroupingUsed(false);
    }

    private BigDecimal amount;

    private Money(BigDecimal amount) {
        if (amount == null || amount.compareTo(new BigDecimal("0")) < 0)
            throw new IllegalArgumentException("Invalid value for amount: " + String.valueOf(amount));
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return formatter.format(amount);
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }


}
