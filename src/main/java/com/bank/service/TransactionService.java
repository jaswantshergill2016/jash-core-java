package com.bank.service;

import com.bank.BankingApp;
import com.bank.Transaction;
import com.bank.dao.TransactionDAO;
import com.bank.util.DAOUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TransactionService {
    private static final String dateFormat = "dd/MM/yyyy HH-mm-ss";
    private static Logger logger = Logger.getLogger(TransactionService.class);

    public void printAllTransactions() {


        Connection connection = DAOUtils.getConnection();

        List<Transaction> transactionList = new TransactionDAO().getAllTransactionList(connection);
        for (Transaction transaction:transactionList) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            String txnDateStr = simpleDateFormat.format(transaction.getDate());

            logger.debug("Transaction Id "+ transaction.getTransactionId()+" Date "+txnDateStr
                         +" "+transaction.isDebitOrCredit()+
                    " Balance: "+transaction.getBalance());
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
