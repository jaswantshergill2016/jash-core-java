package com.bank.dao;

import com.bank.*;
import com.bank.util.DAOUtils;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionDAO {

    private static final String dateFormat = "dd/MM/yyyy HH-mm-ss";


    public void createNewTransactionInDB(Transaction transaction, String currentAccountId, int acctId, int otherAcctId, Connection connection) {

        //Connection connection = DAOUtils.getConnection();
        PreparedStatement transactionStmt = null;

        try {
            String insertTransactionSQL = "INSERT INTO TRANSACTIONS(transaction_id, acct_id," +
                    " date, debit_credit, balance, other_acct_id) VALUES (?,?,?,?,?,?);";

            transactionStmt = connection.prepareStatement(insertTransactionSQL, Statement.RETURN_GENERATED_KEYS);

            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            String strDate= formatter.format(transaction.getDate());

            transactionStmt.setString(1,currentAccountId+"-"+transaction.getTransactionId());
            transactionStmt.setInt(2,acctId);
            transactionStmt.setString(3,strDate);
            transactionStmt.setString(4,transaction.isDebitOrCredit());
            transactionStmt.setDouble(5,transaction.getBalance());
            transactionStmt.setInt(6,otherAcctId);


            transactionStmt.executeUpdate();

            //connection.commit();
        } catch (Exception e) {
            try {
                //connection.rollback();
                transactionStmt.close();
            } catch (SQLException ex) {
                System.out.println("Error during rollback");
                System.out.println(ex.getMessage());
            }
            e.printStackTrace(System.out);
        } finally{
            try {
                //connection.close();
                transactionStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Transaction> getAllTransactionList(Connection connection) {

        String query = "select * from TRANSACTIONS";
        List<Transaction> transactionList = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getString("transaction_id"));
                transaction.setBalance(rs.getDouble("balance"));
                String txnDateStr = rs.getString("date");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                Date txnDate = null;
                try {
                    txnDate = simpleDateFormat.parse(txnDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                transaction.setDate(txnDate);
                boolean isDebit = rs.getString("debit_credit").equalsIgnoreCase("debit")?true:false;
                transaction.setDebit(isDebit);

                transactionList.add(transaction);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transactionList;
    }
}

