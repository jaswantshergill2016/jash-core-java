package com.bank;


import java.util.List;

public abstract class AbstractAccount implements Account {

    private Integer accontOwnerId;
    private Double accountBalance;
    private Integer accountId;
    private List<Transaction> listOfTransactions;

    //business methods
    @Override
    public  Double transferMoneyOut(Double moneyOut){

        this.setAccountBalance(this.getAccountBalance() - moneyOut);
        printTheAccountBalance();
        return this.accountBalance;

    }
    @Override
    public  Double transferMoneyIn(Double moneyIn){

        this.setAccountBalance(this.getAccountBalance() + moneyIn);
        printTheAccountBalance();
        return this.accountBalance;
    }

    @Override
    public boolean equals(Object  account) {
        return this.accountId.equals(((AbstractAccount)account).accountId);
    }

    @Override
    public int hashCode() {
        return this.accountId.hashCode();
    }

    @Override
    public String toString() {
        return "account Id "+ this.accountId+" Balance = "+this.accountBalance+"" +
                "account owner id "+this.accontOwnerId;
    }

    @Override
    public abstract String printTheAccountBalance();

    //getter setters
    public Integer getAccontOwnerId() {
        return accontOwnerId;
    }

    public void setAccontOwnerId(Integer accontOwnerId) {
        this.accontOwnerId = accontOwnerId;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public List<Transaction> getListOfTransactions() {
        return listOfTransactions;
    }

    public void setListOfTransactions(List<Transaction> listOfTransactions) {
        this.listOfTransactions = listOfTransactions;
    }
}
