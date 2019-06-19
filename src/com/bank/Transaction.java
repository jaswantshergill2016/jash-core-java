package com.bank;

import java.util.Date;

public class Transaction {
    private String transactionId;
    private Date date;
    private boolean isDebit;
    private Double balance;
    private Double amount;


    public Transaction() {
    }

    public Transaction(String transactionId, Date date, boolean isDebit, Double balance, Double amount) {
        this.transactionId = transactionId;
        this.date = date;
        this.isDebit = isDebit;
        this.balance = balance;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", date=" + date +
                ", isDebit=" + isDebit +
                ", balance=" + balance +
                ", amount=" + amount +
                '}';
    }

    public String isDebitOrCredit(){
        if(this.isDebit){
            return "Debit";
        }
        return "credit";
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDebit() {
        return isDebit;
    }

    public void setDebit(boolean debit) {
        isDebit = debit;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
