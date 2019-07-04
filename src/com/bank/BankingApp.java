package com.bank;

import com.bank.service.CreateAccountService;
import com.bank.service.MoneyDepositService;
import com.bank.service.MoneyWithdrawService;
import com.bank.service.TransferService;
import org.apache.log4j.Logger;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BankingApp {

    private static Logger logger = Logger.getLogger(BankingApp.class);

    private static final String dateFormat = "dd/MM/yyyy HH-mm-ss";

    public static  Map<String, Account> accountsMap = new HashMap<>();
    public static  Map<String, Customer> customersMap = new HashMap<>();
    public static  Map<String, Transaction> transactionsMap = new HashMap<>();


    public static String currentAccountId = "";
    public static String currentCustomerId = "";
    public static String currentTransactionId = "";

    private static final String ENTER_ACCOUNT_ID = "Enter Account Id: ";


    public static void main(String[] args) {
        readBankStateFromFile();
        executeBankOperations();
    }

    private static void executeBankOperations() {
        while (true) {
            printBankOptions();
            int option = optionFromUser("Please enter your option");
            case4BankOperations(option);
        }
    }

    private static void case4BankOperations(int option) {
        switch (option) {
            case 1:
                createNewAccount();
                break;
            case 2:
                showDetailsOfAccount();
                break;
            case 3:
                doMoneyDeposit();
                break;
            case 4:
                doMoneyWithdraw();
                break;
            case 5:
                transferMoneyBetweenAccounts();
                break;
            case 6:
                printTransactionsOfAccount();
                break;
            case 7:
                printAllAccountDetailsInBank();
                break;
            case 8:
                logger.debug("Exiting from banking service");
                writeBankState2File();
                System.exit(0);
                break;
            default:
                logger.debug("Please select right option from below");
        }

    }

    private static void readBankStateFromFile() {
        BufferedReader bufferedReader  = null;

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(new File("Bank.txt"));
            bufferedReader = new BufferedReader(fileReader);
            String readLine ="";
            while ((readLine = bufferedReader.readLine() )!= null){
                if(readLine.equals("::Accounts")){
                    while((readLine = bufferedReader.readLine()) != null && !readLine.equals("::Customers")){
                        String [] accountValues = readLine.split(":");
                        String accountId = accountValues[1];
                        String accountBalance = accountValues[2];
                        String customerId = accountValues[3];
                        String accountType = accountValues[4];

                        switch (accountType){
                            case "CHEQUING_ACCOUNT":
                                DebitAccount debitAccount = new DebitAccount();
                                debitAccount.setAccountId(accountId);
                                debitAccount.setAccountBalance(Double.parseDouble(accountBalance));
                                debitAccount.setCustomerId(customerId);
                                accountsMap.put(accountId,debitAccount);
                                break;
                            case "SAVING_ACCOUNT":
                                SavingsAccount savingsAccount = new SavingsAccount();
                                savingsAccount.setAccountId(accountId);
                                savingsAccount.setAccountBalance(Double.parseDouble(accountBalance));
                                savingsAccount.setCustomerId(customerId);
                                accountsMap.put(accountId,savingsAccount);
                                break;

                            default:
                                break;
                        }

                    }
                    if(readLine.equals("::Customers")){
                        while((readLine = bufferedReader.readLine())!= null && !readLine.equals("::Transactions")){
                            String [] customerValues = readLine.split(":");
                            String customerId = customerValues[1];
                            String customerName = customerValues[2];
                            String customerAddress = customerValues[3];
                            String customerPhone = customerValues[4];
                            String accountType = customerValues[5];



                            switch (accountType){
                                case "BUSINESS_CUSTOMER":
                                    BusinessCustomer businessCustomer = new BusinessCustomer();
                                    businessCustomer.setCustomerId(customerId);
                                    businessCustomer.setName(customerName);
                                    businessCustomer.setAddress(customerAddress);
                                    businessCustomer.setPhone(customerPhone);

                                    customersMap.put(customerId,businessCustomer);
                                    break;
                                case "PERSONAL_CUSTOMER":
                                    PersonalCustomer personalCustomer = new PersonalCustomer();
                                    personalCustomer.setCustomerId(customerId);
                                    personalCustomer.setName(customerName);
                                    personalCustomer.setAddress(customerAddress);
                                    personalCustomer.setPhone(customerPhone);

                                    customersMap.put(customerId,personalCustomer);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    if(readLine.equals("::Transactions")) {
                        while((readLine = bufferedReader.readLine())!= null && !readLine.equals("::Current Account Id")){
                            String [] transactionValues = readLine.split(":");
                            String transactionId = transactionValues[1];
                            Date transactionDate = null;
                            try {
                                transactionDate = new SimpleDateFormat(dateFormat).parse(transactionValues[2]);
                            } catch (ParseException e) {
                                logger.debug("Unable to parse date string " +e);

                            }
                            boolean isDebit = Boolean.parseBoolean(transactionValues[3]);
                            Double balance = Double.parseDouble(transactionValues[4]);
                            Double amount = Double.parseDouble(transactionValues[5]);

                            Transaction transaction = new Transaction(transactionId,transactionDate,isDebit,balance,amount);
                            transactionsMap.put(transactionId,transaction);

                        }

                    }

                    if(readLine != null && readLine.equals("::Current Account Id")){
                        while((readLine = bufferedReader.readLine())!= null && !readLine.equals("::Current Customer Id")){
                            currentAccountId = readLine;
                        }
                    }
                    if(readLine != null && readLine.equals("::Current Customer Id")){
                        while((readLine = bufferedReader.readLine())!= null && !readLine.equals("::Current Transaction Id")){
                            currentCustomerId = readLine;
                        }
                    }
                    if(readLine != null && readLine.equals("::Current Transaction Id")){
                        while((readLine = bufferedReader.readLine())!= null ){
                            currentTransactionId = readLine;
                        }
                    }


                }
            }



        } catch (FileNotFoundException e) {
            logger.debug("Exception occured "+e);
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("Exception occured "+e);
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                logger.debug(e);
            }
        }

    }

    private static void writeBankState2File() {

        if(accountsMap.size() > 0 && customersMap.size() > 0) {
            BufferedWriter bufferedWriter = null;
            try {

                FileWriter fileWriter = new FileWriter(new File("Bank.txt"));
                bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write("::Accounts\n");
                if (accountsMap.size() > 0) {
                    Collection<Account> accountMapValues = accountsMap.values();
                    Iterator<Account> iter = accountMapValues.iterator();
                    while (iter.hasNext()) {
                        AbstractAccount account = (AbstractAccount)iter.next();
                        bufferedWriter.write(":" + account.getAccountId() +":"+account.getAccountBalance()+":"+account.getCustomerId()+":"+account.getAccountType() +"\n");
                    }
                }
                bufferedWriter.write("::Customers\n");

                if (customersMap.size() > 0) {
                    Collection<Customer> customerMapValues = customersMap.values();
                    Iterator<Customer> iter1 = customerMapValues.iterator();
                    while (iter1.hasNext()) {
                        AbstractCustomer customer = (AbstractCustomer)iter1.next();
                        bufferedWriter.write(":" + customer.getCustomerId()+":"+ customer.getName()+":"+customer.getPhone()+":"+customer.getAddress()+":"+customer.getCustomerType() +"\n");
                    }

                }

                bufferedWriter.write("::Transactions\n");

                if (transactionsMap.size() > 0) {
                    Collection<Transaction> transactionMapValues = transactionsMap.values();
                    Iterator<Transaction> iter1 = transactionMapValues.iterator();
                    while (iter1.hasNext()) {
                        Transaction transaction = iter1.next();
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        String strDate= formatter.format(transaction.getDate());
                        bufferedWriter.write(":" + transaction.getTransactionId()+":"+ strDate+":"+transaction.isDebit()+":"+transaction.getBalance()+":"+transaction.getAmount() +"\n");
                    }

                }


                bufferedWriter.write("::Current Account Id\n");
                bufferedWriter.write(currentAccountId+"\n");

                bufferedWriter.write("::Current Customer Id\n");
                bufferedWriter.write(currentCustomerId+"\n");

                bufferedWriter.write("::Current Transaction Id\n");
                bufferedWriter.write(currentTransactionId+"\n");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException  | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void printAllAccountDetailsInBank() {
        logger.debug("Starting to show all Account Details:");
        logger.debug("Showing "+accountsMap.size()+" Accounts Details:");

        Collection <Account> accountValuesList = accountsMap.values();
        Iterator<Account> iter = accountValuesList.iterator();

        while(iter.hasNext()){
            AbstractAccount account = (AbstractAccount)iter.next();
            logger.debug("AcctId: "+account.getAccountId()+", AcctOwner: "+account.getCustomerId()+"" +
                    ", AcctBalance: "+account.getAccountBalance());
        }
    }

    private static void printTransactionsOfAccount() {
        logger.debug("============printing transactions ==================");


        logger.debug("Showing "+transactionsMap.size()+" Transactions:");

        for (Map.Entry<String,Transaction> entry : transactionsMap.entrySet()) {
            Transaction transaction = entry.getValue();
            logger.debug("transactionID:   "+transaction.getTransactionId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            String date = simpleDateFormat.format(transaction.getDate());
            logger.debug("Date: "+date);
            logger.debug(transaction.isDebitOrCredit()+": "+transaction.getAmount());
            logger.debug("Balance: "+transaction.getBalance());
            logger.debug("===========================================================");
        }
    }

    private static void transferMoneyBetweenAccounts() {
        logger.debug("Starting Money Transfer:");

        String sourceAccountID = getAccountIdFromUser("Enter Source Account Id: ");

        String destinationAccountID = getAccountIdFromUser("Enter Destination Account Id: ");

        int money2Withdraw = optionFromUser("How much money you want to transfer:");

        AbstractAccount sourceAccount = (AbstractAccount)accountsMap.get(sourceAccountID);
        AbstractAccount destinationAccount = (AbstractAccount)accountsMap.get(destinationAccountID);


        while(money2Withdraw > sourceAccount.getAccountBalance()){
            logger.debug("Source Account Balance is less than "+money2Withdraw+", please enter another amount:");
            money2Withdraw = optionFromUser("How much money you want to transfer:");
        }

        new TransferService().transferMoneyBetweenAccounts(sourceAccount, destinationAccount,money2Withdraw);

    }

    private static void doMoneyWithdraw() {
        logger.debug("Starting Money WithdrawL:");

        String accountID = getAccountIdFromUser(ENTER_ACCOUNT_ID);

        int money2Withdraw = optionFromUser("Enter money to withdraw:");

        AbstractAccount account = (AbstractAccount)accountsMap.get(accountID);
        new MoneyWithdrawService().withdrawMoney(account,money2Withdraw);
    }

    private static void doMoneyDeposit() {

        logger.debug("Staring money deposit");

        String accountID = getAccountIdFromUser(ENTER_ACCOUNT_ID);

        int money2Deposit = optionFromUser("Enter money to deposit:");

        AbstractAccount account = (AbstractAccount)accountsMap.get(accountID);

        new MoneyDepositService().depositMoney(account,money2Deposit);
    }

    private static void showDetailsOfAccount() {
        logger.debug("Showing account details");

        String accountID = getAccountIdFromUser(ENTER_ACCOUNT_ID);

        AbstractAccount account = (AbstractAccount)accountsMap.get(accountID);

        logger.debug("Showing Account Details:");

        logger.debug("AcctId: "+account.getAccountId()+", AcctOwner: "+account.getCustomerId()+"" +
                ", AcctBalance: "+account.getAccountBalance());
    }

    private static void createNewAccount() {
        logger.debug("creating new account");
        int accountType = typeOfAccount();

        int customerType = typeOfCustomer();

        String customerName = optionStrFromUser("Enter Customer Name ");
        String customerAddress = optionStrFromUser("Enter Customer Address ");
        String customerPhone = optionStrFromUser("Enter Customer Phone ");

        int typeOfMailPreference = typeOfMailPreference();

        new CreateAccountService().createNewAccount(
                                            customerType, accountType,
                                            customerName,customerAddress,
                                          customerPhone,typeOfMailPreference,
                BankingApp.accountsMap,BankingApp.customersMap,BankingApp.transactionsMap//,
                //BankingApp.currentAccountId,BankingApp.currentCustomerId,BankingApp.currentTransactionId
                );
    }

    public static String generateTransactionId(String accountID) {
        String currentTransactionIdStr = BankingApp.currentTransactionId;
        String transactionIdStr = null;
        if (currentTransactionIdStr == null || currentTransactionIdStr.equals("")) {
            Integer accountNumber = 1;
            String str = String.format("%05d", accountNumber);
            transactionIdStr = accountID+"-TX-" + str;
        } else {
            String transactionNumberStr = currentTransactionIdStr.substring(currentTransactionIdStr.lastIndexOf('-') + 1);
            int transactionNumberInt = Integer.parseInt(transactionNumberStr);
            transactionNumberInt++;
            Integer transactionNumber = transactionNumberInt;
            String str = String.format("%05d", transactionNumber);
            transactionIdStr = accountID+"-TX-" + str;
        }

        return transactionIdStr;

    }

    private static int typeOfMailPreference() {

        while (true) {

            int typeOfMailPreference = optionFromUser("Provide Customer mail preference: Email, postalMail, TextMessage [1,2,3]:");

            if (typeOfMailPreference == 1) {
                return 1;

            } else if (typeOfMailPreference == 2) {
                return 2;
            } else if (typeOfMailPreference == 3) {
                return 3;
            } else {
                logger.debug("Please select appropriate type of mail preference 1,2,3");

            }
        }
    }

    private static String getAccountIdFromUser(String  str2Display) {
        while (true) {
            String accountId = optionStrFromUser(str2Display);
                if(accountsMap.containsKey(accountId)){
                    return accountId;
                } else {
                    logger.debug("Wrong AccountId :");

                }
        }


    }

    private static int typeOfAccount() {
        while (true) {
            int typeOfAccount = optionFromUser("What kind of account: Debit, Savings [1/2] : ");

            if (typeOfAccount == 1) {
                return 1;

            } else if (typeOfAccount == 2) {
                return 2;
            } else {
                logger.debug("Please select appropriate type of account");
            }
        }

    }

    private static int typeOfCustomer() {
        while (true) {
            int typeOfCustomer = optionFromUser("Business or Personal Customer: Business, personal [1,2]:");

            if (typeOfCustomer == 1) {
                return 1;

            } else if (typeOfCustomer == 2) {
                return 2;
            } else {
                logger.debug("Please select appropriate type of customer");

            }
        }

    }

    private static int optionFromUser(String str2Display) {
        Scanner myObj = new Scanner(System.in);
        logger.debug(str2Display);
        int input = 0;
        try {
            input = myObj.nextInt();
        } catch (InputMismatchException ex) {
            logger.debug("Please enter correct input");
            input = 0;
        }

        return input;
    }

    private static String optionStrFromUser(String str2Display) {
        Scanner myObj = new Scanner(System.in);
        logger.debug(str2Display);
        String input = "";
        try {
            input = myObj.nextLine();
        } catch (InputMismatchException ex) {
            logger.debug("Please enter correct input");
            input = "";
        }

        return input;
    }

    public static void printBankOptions() {
        logger.debug("Menu:\n" +
                "1. Create new Account\n" +
                "2. Show Details of an Account\n" +
                "3. Do Money Deposit\n" +
                "4. Do Money Withdraw\n" +
                "5. Transfer Money between two accounts\n" +
                "6. Print Transactions of an account\n" +
                "7. Print All Account Details in the bank.\n" +
                "8. Quit\n" +
                "--------------------------------------------");


    }
}