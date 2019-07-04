package com.bank.service;

import com.bank.AbstractAccount;
import com.bank.BankingApp;
import com.bank.ExceedingDailyLimitException;
import com.bank.Transaction;
import org.apache.log4j.Logger;

import java.util.Date;

public class MoneyDepositService {

    private static Logger logger = Logger.getLogger(MoneyDepositService.class);


    public void depositMoney(AbstractAccount account,int money2Deposit){
        try {
            account.transferMoneyIn(new Double(money2Deposit));
            logger.debug("Money Deposited..");

            String transactionId = BankingApp.generateTransactionId(account.getAccountId());
            BankingApp.currentTransactionId = transactionId;

            Transaction transaction = new Transaction(transactionId,new Date(),true, account.getAccountBalance(),new Double(money2Deposit));

            BankingApp.transactionsMap.put(transactionId,transaction);

        } catch (ExceedingDailyLimitException e) {
            logger.debug("Not Able to deposit money, the amount is exceeding Daily Limit");
            e.printStackTrace();
        }
        logger.debug("Showing Account Details:");
        logger.debug("AcctId: "+account.getAccountId()+", AcctOwner: "+account.getCustomerId()+"" +
                ", AcctBalance: "+account.getAccountBalance());

    }
}
