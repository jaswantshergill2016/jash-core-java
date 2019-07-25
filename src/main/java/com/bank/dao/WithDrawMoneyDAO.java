package com.bank.dao;

import com.bank.util.DAOUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WithDrawMoneyDAO {

    public double getAccountBalance(int acctId) {

        String query ="select * from ACCOUNT where acct_id = "+acctId;

        PreparedStatement ps = null;
        ResultSet rs = null;
        double accountBalance =0;
        Connection con = null;
        try{
            con = DAOUtils.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            if(rs.next()){
                accountBalance = rs.getDouble("acct_balance");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return accountBalance;


    }

    public boolean withDrawMoneyFromTheAccount(int acctId, double totalAccountBalance) {

        Connection connection = DAOUtils.getConnection();
        int updatedRecords=0;
        try {
            String updateAccountSQL = "UPDATE ACCOUNT SET ACCT_BALANCE = ? WHERE ACCT_ID=?";

            PreparedStatement accountStmt = connection.prepareStatement(updateAccountSQL);

            accountStmt.setDouble(1,totalAccountBalance);
            accountStmt.setInt(2,acctId);

            updatedRecords = accountStmt.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error during rollback");
                System.out.println(ex.getMessage());
            }
            e.printStackTrace(System.out);
        } finally{
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updatedRecords>0?true:false;
    }
}
