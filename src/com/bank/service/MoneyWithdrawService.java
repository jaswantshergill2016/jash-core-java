package com.bank.service;

import com.bank.AbstractAccount;
import com.bank.BankingApp;
import com.bank.ExceedingTotalBalanceException;
import com.bank.Transaction;
import org.apache.log4j.Logger;

import java.util.Date;

public class MoneyWithdrawService {

    private static Logger logger = Logger.getLogger(MoneyWithdrawService.class);

    public void withdrawMoney(AbstractAccount account,int money2Withdraw){
        try {
            account.transferMoneyOut(new Double(money2Withdraw));
            logger.debug("Money Withdrawn..");

            String transactionId = BankingApp.generateTransactionId(account.getAccountId());
            BankingApp.currentTransactionId = transactionId;

            Transaction transaction = new Transaction(transactionId,new Date(),false, account.getAccountBalance(),new Double(money2Withdraw));

            BankingApp.transactionsMap.put(transactionId,transaction);
        } catch (ExceedingTotalBalanceException e) {
            logger.debug("Drawn amount is more than total balance");
            e.printStackTrace();
        }
        logger.debug("Showing Account Details:");
        logger.debug("AcctId: "+account.getAccountId()+", AcctOwner: "+account.getCustomerId()+"" +
                ", AcctBalance: "+account.getAccountBalance());
    }
}
