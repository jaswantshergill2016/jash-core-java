package com.bank.service;

import com.bank.AbstractAccount;
import com.bank.BankingApp;
import com.bank.ExceedingTotalBalanceException;
import com.bank.Transaction;
import com.bank.dao.TransactionDAO;
import com.bank.dao.WithDrawMoneyDAO;
import com.bank.util.DAOUtils;
import com.bank.util.DomainId;
import com.bank.util.DomainIdUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
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

    public void withDrawMoneyInDB(String accountID, int money2WithDraw) {

        Connection connection = DAOUtils.getConnection();

        int acctId = new DAOUtils().getAcctIdByAccountId(accountID, connection);
        double currentAccountBalance = new WithDrawMoneyDAO().getAccountBalance(acctId);

        double totalAccountBalance = currentAccountBalance - money2WithDraw;

        boolean isMoneyWithdrawn = new WithDrawMoneyDAO().withDrawMoneyFromTheAccount(acctId, totalAccountBalance);
        if(isMoneyWithdrawn){
            logger.debug("Money Withdrawn");
            String currentTransactionId = DomainIdUtils.generateDomainId(DomainId.TXN_BUSINESS_ID, connection);

            Transaction transaction = new Transaction(currentTransactionId,new Date(),true, totalAccountBalance,new Double(money2WithDraw));

            new TransactionDAO().createNewTransactionInDB(transaction, accountID,acctId,0, connection);

            DomainIdUtils.updateDomainIdInDB(DomainId.TXN_BUSINESS_ID, currentTransactionId, connection);
        }else{
            logger.debug("Problem in Money Withdraw");
        }

        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
