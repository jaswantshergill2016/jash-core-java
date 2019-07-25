package com.bank.util;

import com.bank.AbstractCustomer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCustomerUtils {


    public static int getCustIdFromCustomerId(String customerId, Connection connection) {

        String query ="select cust_id from CUSTOMER where customer_id ='"+customerId+"'";

        PreparedStatement ps = null;
        ResultSet rs = null;
        int custid=0;
        //Connection con = null;
        try{
            //con = DAOUtils.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if(rs.next()){
                custid = rs.getInt("cust_id");
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

        return custid;
    }
}
