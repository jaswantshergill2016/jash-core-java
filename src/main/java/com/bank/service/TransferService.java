package com.bank.service;

import com.bank.*;
import com.bank.dao.DepositMoneyDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dao.WithDrawMoneyDAO;
import com.bank.util.DAOUtils;
import com.bank.util.DomainId;
import com.bank.util.DomainIdUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

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

    public void transferMoneyBetweenAccountsInDB(String sourceAccountID, String destinationAccountID, int money2Transfer) throws ExceedingTotalBalanceException {
        Connection connection = DAOUtils.getConnection();

        int sourceAcctId = new DAOUtils().getAcctIdByAccountId(sourceAccountID, connection);
        double srcCurrentAccountBalance = new WithDrawMoneyDAO().getAccountBalance(sourceAcctId);

        double srcTotalAccountBalance = srcCurrentAccountBalance - money2Transfer;
        if(srcCurrentAccountBalance < money2Transfer){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throw new ExceedingTotalBalanceException();
        }

        boolean isMoneyWithdrawn = new WithDrawMoneyDAO().withDrawMoneyFromTheAccount(sourceAcctId, srcTotalAccountBalance);

        int desitnationAcctId = new DAOUtils().getAcctIdByAccountId(destinationAccountID,connection);
        double destCurrentAccountBalance = new DepositMoneyDAO().getAccountBalance(desitnationAcctId, connection);

        double destTotalAccountBalance = destCurrentAccountBalance + money2Transfer;

        boolean isMoneyDeposited = new DepositMoneyDAO().depositMoneyInTheAccount(desitnationAcctId, destTotalAccountBalance, connection);

        if(isMoneyWithdrawn && isMoneyDeposited){

            String currentTransactionId = DomainIdUtils.generateDomainId(DomainId.TXN_BUSINESS_ID, connection);

            Transaction transaction = new Transaction(currentTransactionId,new Date(),true, srcTotalAccountBalance,new Double(money2Transfer));

            new TransactionDAO().createNewTransactionInDB(transaction, sourceAccountID,sourceAcctId,desitnationAcctId, connection);

            DomainIdUtils.updateDomainIdInDB(DomainId.TXN_BUSINESS_ID, currentTransactionId, connection);



            currentTransactionId = DomainIdUtils.generateDomainId(DomainId.TXN_BUSINESS_ID, connection);

            transaction = new Transaction(currentTransactionId,new Date(),false, destTotalAccountBalance,new Double(money2Transfer));

            new TransactionDAO().createNewTransactionInDB(transaction, destinationAccountID,desitnationAcctId,sourceAcctId, connection);

            DomainIdUtils.updateDomainIdInDB(DomainId.TXN_BUSINESS_ID, currentTransactionId, connection);
            try {
                connection.commit();
                logger.debug("Money Transferred successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }else {
            logger.debug("Problem in money transfer");
        }



    }
}
