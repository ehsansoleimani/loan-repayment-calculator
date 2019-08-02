package com.ehsansoleimani.loanrepaymentcalc.datatransferobject;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by ehsan on 8/1/19.
 */
@Getter
@Setter
public class CalculateRequestDTO {
    @NotNull(message = "loan amount cannot be null")
    @PositiveOrZero(message = "loan amount cannot be negative")
    private BigDecimal loanAmount;
    @PositiveOrZero(message = "nominal rate cannot be negative")
    private double nominalRate;
    @Positive(message = "duration must be positive")
    private int duration;
    @NotNull(message = "start date cannot be null")
    private LocalDate startDate;
}
