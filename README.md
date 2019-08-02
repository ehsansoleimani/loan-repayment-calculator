# loan-repayment-calculator
This is an application calculates the repayment plan for a loan by a REST web service. The calculations are based on [Annuity Payment Formula](https://financeformulas.net/Annuity_Payment_Formula.html#calcHeader). 
For the simplicity it is considered that every month has 30 days.
##Build
To build the application use the command `mvn clean package`.
##Run
After building the project navigate to `target/` and run the application by `java -jar loan-repayment-calculator-0.0.1-SNAPSHOT.jar`
##Calculation
To calculate the loan repayment plan you need to call the web method `POST http://localhost:8080/generate-plan` by passing these parameters in *JSON* format
- `loanAmount`: The loan amount as String or number value
- `nominalRate`: The nominal interest rate as String or number value
- `duration`: The number of installments as number value
- `startDate`: The date loan repayments start
###Returned value
The returned value consisted of repayment items by the fields below:
- `date`: The installment date
- `borrowerPaymentAmount`: The installment annuity (payment amount for the installment)
- `principal`: The principal share from the paid amount
- `interest`: The interest share from the paid amount
- `initialOutstandingPrincipal`: The initial remaining principal (before paying the installment)
- `remainingOutstandingPrincipal`: The remaining principal (after paying the installment)
