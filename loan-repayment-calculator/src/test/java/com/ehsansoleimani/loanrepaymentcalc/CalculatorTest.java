package com.ehsansoleimani.loanrepaymentcalc;

import com.ehsansoleimani.loanrepaymentcalc.valueobject.Money;
import com.ehsansoleimani.loanrepaymentcalc.valueobject.PaymentInfo;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ehsan on 8/1/19.
 */
public class CalculatorTest {
    @Test
    public void testGeneratePlan1() {
        // Setup
        final Money loanAmount = Money.of(new BigDecimal("5000.00"));
        final double nominalRate = 5.0;
        final int duration = 24;
        final LocalDate startDate = LocalDate.of(2018, 1, 1);

        // Run the test
        final PaymentInfo[] result = Calculator.generatePlan(loanAmount, nominalRate, duration, startDate);

        // Verify the results
        verifyGeneratedPlan(loanAmount, duration, result);

    }

    @Test
    public void testGeneratePlan2() {
        // Setup
        final Money loanAmount = Money.of(new BigDecimal("100000"));
        final double nominalRate = 0.0;
        final int duration = 120;
        final LocalDate startDate = LocalDate.of(2017, 1, 1);

        // Run the test
        final PaymentInfo[] result = Calculator.generatePlan(loanAmount, nominalRate, duration, startDate);

        // Verify the results
        verifyGeneratedPlan(loanAmount, duration, result);
    }

    @Test
    public void testGeneratePlan3() {
        // Setup
        final Money loanAmount = Money.of(new BigDecimal("100000"));
        final double nominalRate = 200.0;
        final int duration = 12;
        final LocalDate startDate = LocalDate.of(2019, 1, 2);

        // Run the test
        final PaymentInfo[] result = Calculator.generatePlan(loanAmount, nominalRate, duration, startDate);

        // Verify the results
        verifyGeneratedPlan(loanAmount, duration, result);
    }

    @Test
    public void testGeneratePlan_zeroLoanAmount() {
        // Setup
        final Money loanAmount = Money.of(new BigDecimal("0"));
        final double nominalRate = 10.0;
        final int duration = 12;
        final LocalDate startDate = LocalDate.of(2017, 1, 1);

        // Run the test
        try {
            Calculator.generatePlan(loanAmount, nominalRate, duration, startDate);
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            //ignore
        }

    }

    @Test
    public void testGeneratePlan_negativeLoanAmount() {
        // Setup
        final double nominalRate = 5.0;
        final int duration = 10;
        final LocalDate startDate = LocalDate.of(2017, 1, 1);

        // Run the test
        try {
            Calculator.generatePlan(Money.of(new BigDecimal("-1000")), nominalRate, duration, startDate);
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            //ignored
        }
    }

    @Test
    public void testGeneratePlan_negativeNominalRate() {
        // Setup
        final Money loanAmount = Money.of(new BigDecimal("10000"));
        final double nominalRate = -5.0;
        final int duration = 24;
        final LocalDate startDate = LocalDate.of(2017, 1, 1);
        final PaymentInfo[] expectedResult = new PaymentInfo[]{};

        // Run the test
        try {
            Calculator.generatePlan(loanAmount, nominalRate, duration, startDate);
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            //ignored
        }
    }

    @Test
    public void testGeneratePlan_nullDate() {
        // Setup
        final Money loanAmount = Money.of(new BigDecimal("1000"));
        final double nominalRate = 5.0;
        final int duration = 24;
        final LocalDate startDate = null;

        // Run the test
        try {
            Calculator.generatePlan(loanAmount, nominalRate, duration, startDate);
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            //ignored
        }
    }

    @Test
    public void testGeneratePlan_zeroDuration() {
        // Setup
        final Money loanAmount = Money.of(new BigDecimal("1000"));
        final double nominalRate = 5.0;
        final int duration = 0;
        final LocalDate startDate = LocalDate.of(2018, 1, 1);

        // Run the test
        try {
            Calculator.generatePlan(loanAmount, nominalRate, duration, startDate);
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            //ignored
        }
    }

    @Test
    public void testGeneratePlan_negative() {
        // Setup
        final Money loanAmount = Money.of(new BigDecimal("1000"));
        final double nominalRate = 5.0;
        final int duration = -24;
        final LocalDate startDate = LocalDate.of(2018, 1, 1);

        // Run the test
        try {
            Calculator.generatePlan(loanAmount, nominalRate, duration, startDate);
            assertTrue(true);
        } catch (IllegalArgumentException e) {
            //ignored
        }
    }

    private void verifyGeneratedPlan(Money loanAmount, int duration, PaymentInfo[] result) {
        assertNotNull(result);
        assertEquals(duration, result.length);

        assertEquals(loanAmount.getAmount(), result[0].getInitialOutstandingPrincipal().getAmount());
        assertEquals(new BigDecimal("0.00"), result[duration - 1].getRemainingOutstandingPrincipal().getAmount());

        BigDecimal sumPrincipal = new BigDecimal("0");
        for (PaymentInfo paymentInfo : result) {
            assertNotNull(paymentInfo.getDate());
            assertNotNull(paymentInfo.getBorrowerPaymentAmount());
            assertNotNull(paymentInfo.getInterest());
            assertNotNull(paymentInfo.getPrincipal());
            assertNotNull(paymentInfo.getInitialOutstandingPrincipal());
            assertNotNull(paymentInfo.getRemainingOutstandingPrincipal());


            assertTrue(paymentInfo.getInterest().getAmount().compareTo(paymentInfo.getPrincipal().getAmount()) <= 0);

            assertEquals(paymentInfo.getBorrowerPaymentAmount().getAmount(), paymentInfo.getInterest().getAmount().add(paymentInfo.getPrincipal().getAmount()));
            assertEquals(paymentInfo.getInitialOutstandingPrincipal().getAmount().subtract(paymentInfo.getPrincipal().getAmount()), paymentInfo.getRemainingOutstandingPrincipal().getAmount());
            sumPrincipal = sumPrincipal.add(paymentInfo.getPrincipal().getAmount());
        }
        assertEquals(loanAmount.getAmount(), sumPrincipal);
    }
}
