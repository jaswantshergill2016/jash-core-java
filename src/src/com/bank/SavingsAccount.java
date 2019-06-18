package com.bank;

public class SavingsAccount extends AbstractAccount{

    private Double interestRateOnSavings;

    public Double getInterestRateOnSavings() {
        return interestRateOnSavings;
    }

    public void setInterestRateOnSavings(Double interestRateOnSavings) {
        this.interestRateOnSavings = interestRateOnSavings;
    }

    @Override
    public String toString() {
        return "Interest rate on savings "+this.getInterestRateOnSavings() +
                super.toString();
    }

    @Override
    public String printTheAccountBalance() {
        System.out.println("The Savings Account balance is " +
                this.getAccountBalance() +
                " for accountId" +
        this.getAccountId());

        return this.getAccountBalance()+"";

    }
}
