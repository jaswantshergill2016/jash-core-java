package com.bank.service;

import com.bank.*;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Map;

public class CreateAccountService {
    private static Logger logger = Logger.getLogger(CreateAccountService.class);


    public void /*Map<String, Account>*/ createNewAccount(int customerType, int accountType,
                                                 String customerName, String customerAddress,
                                                 String customerPhone, int typeOfMailPreference
                                                 /*Map<String,Account> accountsMap*/){

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
            customer = new PersonalCustomer();
        } else if (customerType == 2) {
            customer = new BusinessCustomer();
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


        BankingApp.transactionsMap.put(BankingApp.currentTransactionId,transaction);

        if(account !=null ){
            BankingApp.accountsMap.put(((AbstractAccount) account).getAccountId(), account);
        }

        String customerId = generateCustomerId(BankingApp.currentCustomerId);
        BankingApp.currentCustomerId = customerId;


        ((AbstractCustomer) customer).setCustomerId(customerId);
        if(account !=null ) {
            ((AbstractAccount) account).setCustomerId(customerId);
        }

        ((AbstractCustomer) customer).getCustomerAccounts().add(account);
        BankingApp.customersMap.put(((AbstractCustomer) customer).getCustomerId(), customer);

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
