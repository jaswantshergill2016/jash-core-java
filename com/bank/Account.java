package com.bank;

public interface Account {

     Double transferMoneyOut(Double moneyOut);
     Double transferMoneyIn(Double moneyIn);

     String printTheAccountBalance();

}
