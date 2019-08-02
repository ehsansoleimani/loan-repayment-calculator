package com.ehsansoleimani.loanrepaymentcalc.valueobject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

/**
 * Created by ehsan on 8/1/19.
 */
@Value
@AllArgsConstructor
@EqualsAndHashCode
public class PaymentInfo {
    private LocalDate date;
    private Money borrowerPaymentAmount;
    private Money principal;
    private Money interest;
    private Money initialOutstandingPrincipal;
    private Money remainingOutstandingPrincipal;
}
