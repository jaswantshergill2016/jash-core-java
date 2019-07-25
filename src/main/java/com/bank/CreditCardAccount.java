package com.bank;

public class CreditCardAccount extends AbstractAccount{

    private Double creditCardLimit;

    public CreditCardAccount(){
        super.accountType = AccountType.CREDITCARD_ACCOUNT;
    }

    public Double getCreditCardLimit() {
        return creditCardLimit;
    }

    public void setCreditCardLimit(Double creditCardLimit) {
        this.creditCardLimit = creditCardLimit;
    }



    @Override
    public String toString() {
        return "Credit Card Limit "+this.getCreditCardLimit() +
                super.toString();
    }

    @Override
    public String printTheAccountBalance() {
        System.out.println("The credit card Account balance is " +
                this.getAccountBalance() +
                " for accountId" +
        this.getAccountId());

        return this.getAccountBalance()+"";

    }
}
