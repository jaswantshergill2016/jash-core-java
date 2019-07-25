package com.bank.service;

import com.bank.*;
import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.util.DAOCustomerUtils;
import com.bank.util.DAOUtils;
import com.bank.util.DomainId;
import com.bank.util.DomainIdUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public class CreateAccountService {
    private static Logger logger = Logger.getLogger(CreateAccountService.class);

    public void createNewAccount(String customerId, int accountType){

        logger.debug("=========customerId=============>  "+customerId);

        Connection connection = DAOUtils.getConnection();

        int custId = DAOCustomerUtils.getCustIdFromCustomerId(customerId,connection);

        logger.debug("=========custId=============>  "+custId);

        if(custId > 0){

            String currentAccountId = DomainIdUtils.generateDomainId(DomainId.ACCT_BUSINESS_ID, connection);

            createNewAccountinDB(currentAccountId, accountType,custId,customerId, connection);
            DomainIdUtils.updateDomainIdInDB(DomainId.ACCT_BUSINESS_ID, currentAccountId, connection);
        }else{
            logger.debug("No customer found with this customer Id. Please try again");
        }

        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createNewAccountinDB(String currentAccountId, int accountType, int custId, String customerId, Connection connection) {

        AbstractAccount account = null;
        if (accountType == 1) {
            account = new DebitAccount();

        } else if (accountType == 2) {
            account = new SavingsAccount();
        }else if (accountType == 3) {
            account = new CreditCardAccount();
        }
        Double amount = 2000.0;

        if(account != null){
            account.setAccountId(currentAccountId);

            account.setAccountBalance(amount);
        }
        int acctId = new AccountDAO().createNewAccountInDB(account, accountType,custId,customerId, connection);
        if(acctId > 0){
            /*
            BankingApp.currentTransactionId = BankingApp.generateTransactionId(BankingApp.currentAccountId);
            Transaction transaction = null;
            if(account != null){
                transaction = new Transaction(BankingApp.currentTransactionId,new Date(),true, ((AbstractAccount) account).getAccountBalance(),amount);

            }
            transactionsMap.put(BankingApp.currentTransactionId,transaction);
            */

            String currentTransactionId = DomainIdUtils.generateDomainId(DomainId.TXN_BUSINESS_ID, connection);

            Transaction transaction = new Transaction(currentTransactionId,new Date(),false, ((AbstractAccount) account).getAccountBalance(),amount);

            new TransactionDAO().createNewTransactionInDB(transaction, currentAccountId,acctId,0, connection);

            DomainIdUtils.updateDomainIdInDB(DomainId.TXN_BUSINESS_ID, currentTransactionId, connection);

        }
    }


    public void /*Map<String, Account>*/ createNewAccount(int customerType, int accountType,
                                                          String customerName, String customerAddress,
                                                          String customerPhone, int typeOfMailPreference,
                                                          Map<String, Account> accountsMap,
                                                          Map<String, Customer> customersMap,
                                                          Map<String, Transaction> transactionsMap//,
                                                          //String currentAccountId,
                                                          //String currentCustomerId,
                                                          //String currentTransactionId
                                                    ){

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


        String accountId = generateAccountId(BankingApp.currentAccountId);
        BankingApp.currentAccountId = accountId;

        Account account = null;
        if (accountType == 1) {
            account = new DebitAccount();

        } else if (accountType == 2) {
            account = new SavingsAccount();

        }
        Double amount = 2000.0;

        if(account != null){
            ((AbstractAccount) account).setAccountId(accountId);

            ((AbstractAccount) account).setAccountBalance(amount);
        }



        BankingApp.currentTransactionId = BankingApp.generateTransactionId(BankingApp.currentAccountId);

        Transaction transaction = null;
        if(account != null){
            transaction = new Transaction(BankingApp.currentTransactionId,new Date(),true, ((AbstractAccount) account).getAccountBalance(),amount);

        }

        transactionsMap.put(BankingApp.currentTransactionId,transaction);

        if(account !=null ){
            accountsMap.put(((AbstractAccount) account).getAccountId(), account);
        }

        String customerId = generateCustomerId(BankingApp.currentCustomerId);
        BankingApp.currentCustomerId = customerId;


        ((AbstractCustomer) customer).setCustomerId(customerId);
        if(account !=null ) {
            ((AbstractAccount) account).setCustomerId(customerId);
        }

        ((AbstractCustomer) customer).getCustomerAccounts().add(account);
        customersMap.put(((AbstractCustomer) customer).getCustomerId(), customer);

        logger.debug("Creating Account...");
        logger.debug("Account Created.");

        logger.debug("========Account Details======");
        logger.debug("Owner Name: "+((AbstractCustomer) customer).getName());
        logger.debug("AccountId is : " + ((AbstractAccount) account).getAccountId());
        logger.debug("Account Balance: "+((AbstractAccount)account).getAccountBalance());

        //return accountsMap;
    }

    private static String generateAccountId(String currentAccountIdStr) {
        //String currentAccountIdStr = BankingApp.currentAccountId;
        String accountIdStr = null;
        if (currentAccountIdStr == null || currentAccountIdStr.equals("")) {
            Integer accountNumber = 1;
            String str = String.format("%05d", accountNumber);
            accountIdStr = "ACC-" + str;
        } else {
            String accountNumberStr = currentAccountIdStr.substring(currentAccountIdStr.indexOf('-') + 1);
            int accountNumberInt = Integer.parseInt(accountNumberStr);
            accountNumberInt++;
            Integer accountNumber = accountNumberInt;
            String str = String.format("%05d", accountNumber);
            accountIdStr = "ACC-" + str;
        }

        return accountIdStr;
    }

    private static String generateCustomerId(String currentCustomerIdStr) {
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
}
