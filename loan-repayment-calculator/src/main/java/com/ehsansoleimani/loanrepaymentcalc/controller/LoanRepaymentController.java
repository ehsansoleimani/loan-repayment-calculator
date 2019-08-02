package com.ehsansoleimani.loanrepaymentcalc.controller;

import com.ehsansoleimani.loanrepaymentcalc.Calculator;
import com.ehsansoleimani.loanrepaymentcalc.datatransferobject.CalculateRequestDTO;
import com.ehsansoleimani.loanrepaymentcalc.datatransferobject.RepaymentItemDTO;
import com.ehsansoleimani.loanrepaymentcalc.valueobject.Money;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by ehsan on 8/1/19.
 */
@RestController
public class LoanRepaymentController {
    private RepaymentItemMapper mapper = RepaymentItemMapper.instance;

    @PostMapping("/generate-plan")
    public RepaymentItemDTO[] generatePlan(@Valid @RequestBody CalculateRequestDTO request) {
        return mapper.convertToRepaymentItemArray(Calculator.generatePlan(Money.of(request.getLoanAmount()), request.getNominalRate(), request.getDuration(), request.getStartDate()));
    }
}
