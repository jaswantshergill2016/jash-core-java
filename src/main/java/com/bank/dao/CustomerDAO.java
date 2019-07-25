package com.bank.dao;


import com.bank.AbstractCustomer;
import com.bank.util.DAOUtils;
import com.bank.util.DomainId;
import com.bank.util.DomainIdUtils;
import org.apache.log4j.Logger;

import java.sql.*;

public class CustomerDAO {

    private static Logger logger = Logger.getLogger(CustomerDAO.class);

    //private Connection connection;

    public void createNewCustomer(AbstractCustomer customer, int customerType, Connection connection) {
        logger.debug("======= In createNewCustomer()========");

        String currentCustomerId = DomainIdUtils.generateDomainId(DomainId.CUST_BUSINESS_ID, connection);

        createNewCustomerInDB(customer, currentCustomerId, customerType, connection);
        DomainIdUtils.updateDomainIdInDB(DomainId.CUST_BUSINESS_ID, currentCustomerId, connection);
    }

    public void createNewCustomerInDB(AbstractCustomer customer, String currentCustomerId, int customerType, Connection connection){

        //Connection connection = DAOUtils.getConnection();
        PreparedStatement customerStmt = null;
        try {
            String insertCustomerSQL = "INSERT INTO CUSTOMER(CUSTOMER_ID, NAME, ADDRESS,PHONE, MAIL_PREFERENCE, CUSTOMER_TYPE) VALUES (?,?,?,?,?,?);";

            customerStmt = connection.prepareStatement(insertCustomerSQL,Statement.RETURN_GENERATED_KEYS);

            customerStmt.setString(1,currentCustomerId);
            customerStmt.setString(2,customer.getName());
            customerStmt.setString(3,customer.getAddress());
            customerStmt.setString(4,customer.getPhone());
            customerStmt.setString(5,customer.getMailPreference().toString());
            customerStmt.setInt(6,customerType);

            customerStmt.executeUpdate();
            ResultSet rs =  customerStmt.getGeneratedKeys();
            int generatedCustId=0;
            if(rs.next()){
                //generatedCustId = rs.getInt("cust_id");
                generatedCustId = rs.getInt(1);
            }

            logger.debug("========generatedCustId "+generatedCustId);

            //connection.commit();
            String insertPersonalOrBusinessCustomerSQL="";
            if(customerType ==1){
                insertPersonalOrBusinessCustomerSQL ="INSERT INTO BUSINESS_CUSTOMER (BUSINESS_CUST_ID,business_name,annual_revenue,incorporation_date,directors_list) VALUES ("+generatedCustId+",'Business Name',120.0,'18/07/2019 12-04-50','directors list')";
            } else if(customerType ==2){

                insertPersonalOrBusinessCustomerSQL ="INSERT INTO PERSONAL_CUSTOMER (PERSONAL_CUST_ID,lock_box_type,no_of_missed_bill_payments) VALUES ("+generatedCustId+",'TYPE1',0)";
            }

            PreparedStatement personalOrBusinessCustomerStmt = connection.prepareStatement(insertPersonalOrBusinessCustomerSQL);
            personalOrBusinessCustomerStmt.executeUpdate();

            //connection.commit();
        } catch (Exception e) {
            try {
                //connection.rollback();
                customerStmt.close();
            } catch (SQLException ex) {
                //System.out.println("Error during rollback");
                System.out.println(ex.getMessage());
            }
            e.printStackTrace(System.out);
        } finally{
            try {
                //connection.close();
                customerStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
