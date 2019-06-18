package com.bank;

public class DebitAccount extends AbstractAccount{

    private Double overDraftLimit;

    public Double getOverDraftLimit() {
        return overDraftLimit;
    }

    public void setOverDraftLimit(Double overDraftLimit) {
        this.overDraftLimit = overDraftLimit;
    }

    @Override
    public String toString() {
        return "Over Draft Limit "+this.getOverDraftLimit() + super.toString();
    }

    @Override
    public String printTheAccountBalance() {
        System.out.println("The debit Account balance is " +
                this.getAccountBalance() +
                " for accountId" +
        this.getAccountId());

        return this.getAccountBalance()+"";

    }
}
