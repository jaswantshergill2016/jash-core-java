package com.bank;

public interface Account {

     Double transferMoneyOut(Double moneyOut) throws ExceedingTotalBalanceException;
     Double transferMoneyIn(Double moneyIn) throws ExceedingDailyLimitException;

     String printTheAccountBalance();

}
