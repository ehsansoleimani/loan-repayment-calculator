package com.ehsansoleimani.loanrepaymentcalc.controller;

import com.ehsansoleimani.loanrepaymentcalc.datatransferobject.RepaymentItemDTO;
import com.ehsansoleimani.loanrepaymentcalc.valueobject.PaymentInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by ehsan on 8/2/19.
 */
@Mapper
public abstract class RepaymentItemMapper {

    public static RepaymentItemMapper instance = Mappers.getMapper(RepaymentItemMapper.class);


    public RepaymentItemDTO convertToRepaymentItem(PaymentInfo paymentInfo) {
        if (paymentInfo == null) {
            return null;
        }
        return new RepaymentItemDTO(
                paymentInfo.getDate(),
                paymentInfo.getBorrowerPaymentAmount().toString(),
                paymentInfo.getPrincipal().toString(),
                paymentInfo.getInterest().toString(),
                paymentInfo.getInitialOutstandingPrincipal().toString(),
                paymentInfo.getRemainingOutstandingPrincipal().toString());
    }

    public abstract RepaymentItemDTO[] convertToRepaymentItemArray(PaymentInfo[] paymentInfoArray);
}
