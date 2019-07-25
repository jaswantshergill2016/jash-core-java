package com.bank.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DomainIdUtils {

    public static String generateDomainId(DomainId domainId, Connection connection){

        //Connection con = DAOUtils.getConnection();

        String lastDomainIdValue = getLastDomainIdValue(domainId, connection);
        String newDomainId = generateNewDomainId(domainId,lastDomainIdValue);

        System.out.println("*******newDomainId ******> "+newDomainId);


        return newDomainId;
    }

    private static String generateNewDomainId(DomainId domainId,String lastDomainIdValue) {

        String lastDomainIdValueStr = lastDomainIdValue.substring(lastDomainIdValue.indexOf('-') + 1);
        int newDomainIdValueInt = Integer.parseInt(lastDomainIdValueStr);
        newDomainIdValueInt++;
        //Integer customerNumber = lastDomainIdValueInt;

        String newDomainIdValueStr = String.format("%05d", newDomainIdValueInt);

        String newDomainIdValue ="";
        if(domainId == DomainId.ACCT_BUSINESS_ID){
            newDomainIdValue = "ACCT-" + newDomainIdValueStr;
        } else if(domainId == DomainId.CUST_BUSINESS_ID){
            newDomainIdValue = "CUST-" + newDomainIdValueStr;
        } else if(domainId == DomainId.TXN_BUSINESS_ID){
            newDomainIdValue = "TXN-" + newDomainIdValueStr;
        }

        return newDomainIdValue;
    }

    public static String getLastDomainIdValue(DomainId domainId, Connection connection){
        String query ="";
        if(domainId == DomainId.ACCT_BUSINESS_ID){
            query = "select ID_LAST_VALUE from DOMAIN_ID where ID_TYPE ='"+DomainId.ACCT_BUSINESS_ID+"'";
        } else if(domainId == DomainId.CUST_BUSINESS_ID){
            query = "select ID_LAST_VALUE from DOMAIN_ID where ID_TYPE ='"+DomainId.CUST_BUSINESS_ID+"'";
        } else if(domainId == DomainId.TXN_BUSINESS_ID){
            query = "select ID_LAST_VALUE from DOMAIN_ID where ID_TYPE ='"+DomainId.TXN_BUSINESS_ID+"'";
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        String domainIdLastValue="";
        //Connection con = null;
        try{
            //con = DAOUtils.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            if(rs.next()){
                domainIdLastValue = rs.getString("ID_LAST_VALUE");
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

        return domainIdLastValue;
    }

    public static void updateDomainIdInDB(DomainId domainId, String currentDomainId, Connection connection) {
        String query ="";

        if(domainId == DomainId.ACCT_BUSINESS_ID){
            query = "update DOMAIN_ID set ID_LAST_VALUE = ? where ID_TYPE ='"+DomainId.ACCT_BUSINESS_ID+"'";
        } else if(domainId == DomainId.CUST_BUSINESS_ID){
            query = "update DOMAIN_ID set ID_LAST_VALUE = ? where ID_TYPE ='"+DomainId.CUST_BUSINESS_ID+"'";
        } else if(domainId == DomainId.TXN_BUSINESS_ID){
            query = "update DOMAIN_ID set ID_LAST_VALUE = ? where ID_TYPE ='"+DomainId.TXN_BUSINESS_ID+"'";
        }

        PreparedStatement ps = null;
        //Connection connection = null;
        try{
            //connection = DAOUtils.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, currentDomainId);
            System.out.println("#########updateDomainIdInDB##############");
            ps.executeUpdate();
            //connection.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                //connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
