package com.bank.service;

import com.bank.*;
import com.bank.dao.CustomerDAO;
import com.bank.util.DAOUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class CreateCustomerService {
    public void createNewCustomer(int customerType, String customerName,
                                  String customerAddress, String customerPhone,
                                  int typeOfMailPreference) {

        MailPreference mailPreference = null;
        if(typeOfMailPreference == 1){
            mailPreference = MailPreference.EMAIL;
        } else if(typeOfMailPreference == 2){
            mailPreference = MailPreference.POSTALMAIL;
        }else if(typeOfMailPreference == 3){
            mailPreference = MailPreference.TEXTMESSAGE;
        }



        Customer customer = null;
        if (customerType == 1) {
            customer = new BusinessCustomer();
        } else if (customerType == 2) {
            customer = new PersonalCustomer();
        }

        ((AbstractCustomer) customer).setName(customerName);
        ((AbstractCustomer) customer).setAddress(customerAddress);
        ((AbstractCustomer) customer).setPhone(customerPhone);
        ((AbstractCustomer) customer).setMailPreference(mailPreference);

        Connection connection = DAOUtils.getConnection();

        new CustomerDAO().createNewCustomer((AbstractCustomer)customer, customerType,connection);

        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
