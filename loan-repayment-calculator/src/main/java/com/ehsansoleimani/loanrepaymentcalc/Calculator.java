package com.ehsansoleimani.loanrepaymentcalc;

import com.ehsansoleimani.loanrepaymentcalc.valueobject.Money;
import com.ehsansoleimani.loanrepaymentcalc.valueobject.PaymentInfo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by ehsan on 8/1/19.
 */
public class Calculator {

    /**
     * Generate a repayment plan for the loan by the given items
     * @param loanAmount the loan amount
     * @param nominalRate the nominal interest rate (in percents)
     * @param duration the duration of the loan in terms of months
     * @param startDate the loan start date
     * @return an array of PaymentInfo represents the repayment items for each installment
     */
    public static PaymentInfo[] generatePlan(Money loanAmount, double nominalRate, int duration, LocalDate startDate) {
        if (loanAmount == null){
            throw new IllegalArgumentException("Loan amount is null");
        }
        if(loanAmount.getAmount().compareTo(new BigDecimal("0")) <= 0) {
            throw new IllegalArgumentException("Invalid loan amount");
        }
        if (nominalRate < 0) {
            throw new IllegalArgumentException("Invalid nominal rate: " + nominalRate);
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Invalid duration: " + duration);
        }
        if(startDate == null) {
            throw new IllegalArgumentException("Invalid start date: null");
        }
        double monthlyRate = nominalRate / 12 / 100;
        Money annuity = calculateAnnuity(loanAmount, monthlyRate, duration);
        PaymentInfo[] paymentInfo = new PaymentInfo[duration];
        LocalDate nextPaymentDate = startDate;
        Money presentValue = loanAmount;
        for (int i = 0; i < duration - 1; ++i) {
            paymentInfo[i] = calculatePaymentInfo(annuity, presentValue, monthlyRate, nextPaymentDate);
            presentValue = Money.of(paymentInfo[i].getRemainingOutstandingPrincipal().getAmount());
            nextPaymentDate = nextPaymentDate.plusMonths(1);
        }
        Money lastAnnuity = calculateAnnuityForTheLastInstallment(presentValue, monthlyRate);
        paymentInfo[duration - 1] = calculatePaymentInfo(lastAnnuity, presentValue, monthlyRate, nextPaymentDate);
        return paymentInfo;
    }

    private static Money calculateAnnuityForTheLastInstallment(Money presentValue, double monthlyRate) {
        Money interest = Money.of(presentValue.getAmount().multiply(new BigDecimal(monthlyRate)));
        return Money.of(interest.getAmount().add(presentValue.getAmount()));
    }

    /**
     * Calculates a repayment item by the given parameters
     * @param annuity the calculated annuity for the installment
     * @param presentValue the remaining of principal
     * @param monthlyRate the monthly interest rate
     * @param nextPaymentDate next payment date
     * @return
     */
    private static PaymentInfo calculatePaymentInfo(Money annuity, Money presentValue, double monthlyRate, LocalDate nextPaymentDate) {
        Money interest = Money.of(presentValue.getAmount().multiply(new BigDecimal(monthlyRate)));
        Money principal = Money.of(annuity.getAmount().subtract(interest.getAmount()));

        if (interest.getAmount().compareTo(principal.getAmount()) > 0) {
            interest = Money.of(principal.getAmount());
        }

        Money paymentAmount = Money.of(interest.getAmount().add(principal.getAmount()));
        Money remainingOutstandingPrincipal = Money.of(presentValue.getAmount().subtract(principal.getAmount()));
        return new PaymentInfo(nextPaymentDate, paymentAmount, principal, interest, presentValue, remainingOutstandingPrincipal);
    }

    /**
     * Calculates the annuity of the loan installments by the given paraeters
     * @param presentValue the remaining of principal
     * @param monthlyRate the monthly interest rate
     * @param periods the number of remaining installments
     * @return
     */
    private static Money calculateAnnuity(Money presentValue, double monthlyRate, int periods) {
        if(monthlyRate > 0) {
            return Money.of(presentValue.getAmount().multiply(new BigDecimal(monthlyRate / (1 - (1 / Math.pow(1 + monthlyRate, periods))))));
        } else if(monthlyRate == 0) {
            return Money.of(presentValue.getAmount().divide(new BigDecimal(periods), 2, BigDecimal.ROUND_HALF_UP));
        } else {
            return null;
        }
    }
}
