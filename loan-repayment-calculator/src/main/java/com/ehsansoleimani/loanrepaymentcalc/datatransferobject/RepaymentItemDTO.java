package com.ehsansoleimani.loanrepaymentcalc.datatransferobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by ehsan on 8/2/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentItemDTO {
    private LocalDate date;
    private String borrowerPaymentAmount;
    private String principal;
    private String interest;
    private String initialOutstandingPrincipal;
    private String remainingOutstandingPrincipal;
}
