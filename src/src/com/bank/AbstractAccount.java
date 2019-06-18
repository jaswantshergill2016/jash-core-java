package com.bank;


import java.util.List;

public abstract class AbstractAccount implements Account {

    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    private Double accountBalance;
    private String accountId;
    private List<Transaction> listOfTransactions;
    private Double DAILY_LIMIT = 2000.0;

    //business methods
    @Override
    public  Double transferMoneyOut(Double moneyOut) throws ExceedingTotalBalanceException {

        if(moneyOut > this.getAccountBalance()){
            throw new ExceedingTotalBalanceException();
        }

        this.setAccountBalance(this.getAccountBalance() - moneyOut);
        printTheAccountBalance();
        return this.accountBalance;

    }
    @Override
    public  Double transferMoneyIn(Double moneyIn) throws ExceedingDailyLimitException {

        if(moneyIn > DAILY_LIMIT){
            throw new ExceedingDailyLimitException();
        }

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
                "account owner id "+this.customerId;
    }

    @Override
    public abstract String printTheAccountBalance();

    //getter setters

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<Transaction> getListOfTransactions() {
        return listOfTransactions;
    }

    public void setListOfTransactions(List<Transaction> listOfTransactions) {
        this.listOfTransactions = listOfTransactions;
    }
}
