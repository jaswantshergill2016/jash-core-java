package com.bank.service;

import com.bank.*;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Map;

public class TransferService {

    private static Logger logger = Logger.getLogger(TransferService.class);

    public void transferMoneyBetweenAccounts(AbstractAccount sourceAccount, AbstractAccount destinationAccount,
                                             int money2Withdraw){

        try {
            sourceAccount.transferMoneyOut(new Double(money2Withdraw));
            destinationAccount.transferMoneyIn(new Double(money2Withdraw));

            BankingApp.currentTransactionId = BankingApp.generateTransactionId(sourceAccount.getAccountId());

            Transaction transaction = new Transaction(BankingApp.currentTransactionId,new Date(),false,  sourceAccount.getAccountBalance(),new Double(money2Withdraw));

            BankingApp.transactionsMap.put(BankingApp.currentTransactionId,transaction);
            //==========for second transaction
            BankingApp.currentTransactionId = BankingApp.generateTransactionId(destinationAccount.getAccountId());

            transaction = new Transaction(BankingApp.currentTransactionId,new Date(),true, destinationAccount.getAccountBalance(),new Double(money2Withdraw));

            BankingApp.transactionsMap.put(BankingApp.currentTransactionId,transaction);


        } catch (ExceedingTotalBalanceException e) {
            logger.debug("Money to withdraw exceeds total balance");
            e.printStackTrace();
        } catch (ExceedingDailyLimitException e) {
            logger.debug("Money to withdraw exceeds daily limit");
            e.printStackTrace();
        }
        logger.debug("Transferring money...");
        logger.debug("Money Transferred.");
        logger.debug("Source: AcctId: "+sourceAccount.getAccountId()+", AcctOwner: "+sourceAccount.getCustomerId()+"" +
                ", AcctBalance: "+sourceAccount.getAccountBalance());

        logger.debug("Destination AcctId: "+destinationAccount.getAccountId()+", AcctOwner: "+destinationAccount.getCustomerId()+"" +
                ", AcctBalance: "+destinationAccount.getAccountBalance());

    }

}
