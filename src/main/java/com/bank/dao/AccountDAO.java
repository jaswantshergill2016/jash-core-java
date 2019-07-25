package com.bank.dao;

import com.bank.*;
import com.bank.util.DAOUtils;
import com.bank.util.DomainId;
import com.bank.util.DomainIdUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {

    private static Logger logger = Logger.getLogger(AccountDAO.class);

    public int createNewAccountInDB(AbstractAccount account, int accountType, int custId, String customerId, Connection connection) {

        String currentAccountId = DomainIdUtils.generateDomainId(DomainId.ACCT_BUSINESS_ID, connection);


        //Connection connection = DAOUtils.getConnection();
        PreparedStatement accountStmt = null;

        try {
            String insertAccountSQL = "INSERT INTO ACCOUNT(CUST_ID, ACCT_BALANCE,ACCOUNT_ID, ACCOUNT_TYPE) VALUES (?,?,?,?);";

            accountStmt = connection.prepareStatement(insertAccountSQL, Statement.RETURN_GENERATED_KEYS);

            accountStmt.setInt(1, custId);
            accountStmt.setDouble(2, account.getAccountBalance());
            accountStmt.setString(3, customerId + "-" + currentAccountId);
            accountStmt.setInt(4, accountType);

            accountStmt.executeUpdate();
            ResultSet rs = accountStmt.getGeneratedKeys();
            int generatedAccountId = 0;
            if (rs.next()) {
                generatedAccountId = rs.getInt(1);
            }

            logger.debug("========generatedAccountId " + generatedAccountId);

            String insertDebitOrSavingsOrCreditCardAccountSQL = "";
            if (accountType == 1) {
                insertDebitOrSavingsOrCreditCardAccountSQL = "INSERT INTO DEBIT_ACCOUNT (debit_acct_id,overdraft_limit) values (" + generatedAccountId + ",2000.0)";
            } else if (accountType == 2) {
                insertDebitOrSavingsOrCreditCardAccountSQL = "INSERT INTO SAVINGS_ACCOUNT (savings_acct_id,interest_rate) values (" + generatedAccountId + ",13.0)";
            } else if (accountType == 3) {
                insertDebitOrSavingsOrCreditCardAccountSQL = "INSERT INTO CREDIT_CARD_ACCOUNT (crdcard_acct_id,credit_card_limit) values (" + generatedAccountId + ",2000.0)";
            }

            PreparedStatement insertDebitOrSavingsOrCreditCardAccountStmt = connection.prepareStatement(insertDebitOrSavingsOrCreditCardAccountSQL);
            insertDebitOrSavingsOrCreditCardAccountStmt.executeUpdate();

            //connection.commit();
            DomainIdUtils.updateDomainIdInDB(DomainId.ACCT_BUSINESS_ID, currentAccountId, connection);
            return generatedAccountId;
        } catch (Exception e) {
            try {
                //connection.rollback();
                accountStmt.close();
            } catch (SQLException ex) {
                //System.out.println("Error during rollback");
                System.out.println(ex.getMessage());
            }
            e.printStackTrace(System.out);
        } finally {
            try {
                //connection.close();
                accountStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public List<AbstractAccount> getListOfAllAccounts(Connection connection) {

        String query = "select * from ACCOUNT";
        List<AbstractAccount> accountList = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                AbstractAccount account = null;
                int accountType = rs.getInt("ACCOUNT_TYPE");
                if(accountType==2 ){
                    account = new SavingsAccount();
                } else if(accountType == 1){
                    account = new DebitAccount();
                } else if(accountType==3){
                    account = new CreditCardAccount();
                }
                account.setAccountId(rs.getString("ACCOUNT_ID"));
                account.setAccountBalance(rs.getDouble("acct_balance"));

                accountList.add(account);

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
        return accountList;
    }
}