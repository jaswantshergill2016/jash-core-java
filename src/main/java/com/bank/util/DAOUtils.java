package com.bank.util;

import java.sql.*;

public class DAOUtils {

    private static Connection connection;

    public static Connection getConnection(){
        try {
//            Class.forName("org.h2.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDb", "root", "robin");
            //Class.forName("org.postgresql.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            //connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/springdb", "postgres", "bootcamp");
            //jdbc:mysql://localhost:3306/RB_DBO?serverTimezone=EST5EDT
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?serverTimezone=EST5EDT", "root", "bootcamp");
            connection.setAutoCommit(false);

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return connection;
    }


    public static String generateCustomerId(String currentCustomerIdStr) {
        //String currentCustomerIdStr = BankingApp.currentCustomerId;
        String customerIdStr = null;
        if (currentCustomerIdStr == null || currentCustomerIdStr.equals("")) {
            Integer customerNumber = 1;

            String str = String.format("%05d", customerNumber);
            customerIdStr = "CUST-" + str;
        } else {
            String customerNumberStr = currentCustomerIdStr.substring(currentCustomerIdStr.indexOf('-') + 1);
            int customerNumberInt = Integer.parseInt(customerNumberStr);
            customerNumberInt++;
            Integer customerNumber = customerNumberInt;

            String str = String.format("%05d", customerNumber);
            customerIdStr = "CUST-" + str;
        }

        return customerIdStr;
    }


    public int getAcctIdByAccountId(String accountID, Connection connection) {

        String query ="select * from ACCOUNT where ACCOUNT_ID like '%"+accountID+"'";

        PreparedStatement ps = null;
        ResultSet rs = null;
        int acctId=0;
        //Connection con = null;
        try{
            //con = DAOUtils.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if(rs.next()){
                acctId = rs.getInt("acct_id");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                //con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return acctId;

    }
}
