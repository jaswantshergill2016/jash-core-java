package com.bank.service;

import com.bank.AbstractAccount;
import com.bank.Account;
import com.bank.dao.AccountDAO;
import com.bank.util.DAOUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccountDetailsService {

    public void printAllAccountDetails() {

        Connection connection = DAOUtils.getConnection();

        List<AbstractAccount> accountsList = new AccountDAO().getListOfAllAccounts(connection);

        for (AbstractAccount account:accountsList) {
            System.out.println("Account Id "+ account.getAccountId()+" === Account Balance"+account.getAccountBalance());
        }
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
