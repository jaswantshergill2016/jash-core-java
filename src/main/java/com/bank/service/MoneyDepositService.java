package com.bank.service;

import com.bank.AbstractAccount;
import com.bank.BankingApp;
import com.bank.ExceedingDailyLimitException;
import com.bank.Transaction;
import com.bank.dao.DepositMoneyDAO;
import com.bank.dao.TransactionDAO;
import com.bank.util.DAOUtils;
import com.bank.util.DomainId;
import com.bank.util.DomainIdUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
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

    public void depositMoneyInDB(String accountID, int money2Deposit) {

        Connection connection = DAOUtils.getConnection();

        int acctId = new DAOUtils().getAcctIdByAccountId(accountID,connection);
        double currentAccountBalance = new DepositMoneyDAO().getAccountBalance(acctId, connection);

        double totalAccountBalance = currentAccountBalance + money2Deposit;

        boolean isMoneyDeposited = new DepositMoneyDAO().depositMoneyInTheAccount(acctId, totalAccountBalance, connection);
        if(isMoneyDeposited){
            logger.debug("Money Deposited");
            String currentTransactionId = DomainIdUtils.generateDomainId(DomainId.TXN_BUSINESS_ID, connection);

            Transaction transaction = new Transaction(currentTransactionId,new Date(),false, totalAccountBalance,new Double(money2Deposit));

            new TransactionDAO().createNewTransactionInDB(transaction, accountID,acctId,0, connection);

            DomainIdUtils.updateDomainIdInDB(DomainId.TXN_BUSINESS_ID, currentTransactionId, connection);
        }else{
            logger.debug("Problem in Money Deposit");
        }
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
