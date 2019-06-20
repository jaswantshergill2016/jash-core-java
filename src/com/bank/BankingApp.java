package com.bank;

import org.apache.log4j.Logger;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BankingApp {

    private static Logger logger = Logger.getLogger(BankingApp.class);

    //public static List<Account> accountList = new LinkedList<>();
    //public static List<Customer> customerList = new ArrayList<>();
    public static Map<String, Account> accountsMap = new HashMap<>();
    public static Map<String, Customer> customersMap = new HashMap<>();
    public static Map<String, Transaction> transactionsMap = new HashMap<>();


    public static String currentAccountId = "";
    public static String currentCustomerId = "";
    public static String currentTransactionId = "";


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

        //while(true) {
        switch (option) {
            case 1://System.out.println("What kind of account: Debit, Savings [1/2] : ");
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
                //System.out.println("Exiting from banking service");
                writeBankState2File();
                logger.debug("Exiting from banking service");
                System.exit(0);

            default:
                System.out.println("Please select right option from below");

        }
        //}
        //executeBankOperations();

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
                    System.out.println("====Account======");
                    //readLine = bufferedReader.readLine();
                    while((readLine = bufferedReader.readLine()) != null && !readLine.equals("::Customers")){
                        System.out.println(readLine);
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
                        }
                        System.out.println("accountId ====> "+accountId);
                        System.out.println("accountBalance ====> "+accountBalance);

                    }
                    if(readLine.equals("::Customers")){
                        System.out.println("====Customers======");
                        //while((readLine = bufferedReader.readLine())!= null && !readLine.equals("::Current Account Id")){
                        while((readLine = bufferedReader.readLine())!= null && !readLine.equals("::Transactions")){
                            System.out.println(readLine);
                            //:CUST-00001:1:1:1:PERSONAL_CUSTOMER
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
                            }
                        }
                    }

                    if(readLine.equals("::Transactions")) {
                        System.out.println("====::Transactions======");
                        while((readLine = bufferedReader.readLine())!= null && !readLine.equals("::Current Account Id")){
                            System.out.println(readLine);
                            //:ACC-00004-TX-00004:Wed Jun 19 17:01:20 EDT 2019:true:2000.0:2000.0
                            String [] transactionValues = readLine.split(":");
                            String transactionId = transactionValues[1];
                            //String sDate1="31/12/1998";
                            //Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
                            //Date transactionDate = new Date(transactionValues[2]);
                            Date transactionDate = null;
                            try {
                                transactionDate = new SimpleDateFormat("dd/MM/yyyy").parse(transactionValues[2]);
                            } catch (ParseException e) {
                                System.out.println("Unable to parse date string");
                                e.printStackTrace();
                            }
                            boolean isDebit = Boolean.parseBoolean(transactionValues[3]);
                            Double balance = Double.parseDouble(transactionValues[4]);
                            Double amount = Double.parseDouble(transactionValues[5]);

                            Transaction transaction = new Transaction(transactionId,transactionDate,isDebit,balance,amount);
                            transactionsMap.put(transactionId,transaction);

                        }

                    }

                    if(readLine != null && readLine.equals("::Current Account Id")){
                        System.out.println("====::Current Account Id======");
                        while((readLine = bufferedReader.readLine())!= null && !readLine.equals("::Current Customer Id")){
                            System.out.println(readLine);
                            currentAccountId = readLine;
                        }
                    }
                    if(readLine != null && readLine.equals("::Current Customer Id")){
                        System.out.println("====::Current Customer Id======");
                        while((readLine = bufferedReader.readLine())!= null && !readLine.equals("::Current Transaction Id")){
                            System.out.println(readLine);
                            currentCustomerId = readLine;
                        }
                    }
                    if(readLine != null && readLine.equals("::Current Transaction Id")){
                        System.out.println("====::Current Transaction Id======");
                        while((readLine = bufferedReader.readLine())!= null ){
                            System.out.println(readLine);
                            currentTransactionId = readLine;
                        }
                    }


                }
            }


            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeBankState2File() {
//        ::Customer
//        :C-00001:john:41678917:3367 Toronto
//         ::Account
//         :ACC-00001:5010.0

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

                    //bufferedWriter.write("::End");
                }

                bufferedWriter.write("::Transactions\n");

                if (transactionsMap.size() > 0) {
                    Collection<Transaction> transactionMapValues = transactionsMap.values();
                    Iterator<Transaction> iter1 = transactionMapValues.iterator();
                    while (iter1.hasNext()) {
                        Transaction transaction = iter1.next();
//transactionId, date,  isDebit,  balance,  amount)
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate= formatter.format(transaction.getDate());
//
                        bufferedWriter.write(":" + transaction.getTransactionId()+":"+ strDate+":"+transaction.isDebit()+":"+transaction.getBalance()+":"+transaction.getAmount() +"\n");
                    }

                    //bufferedWriter.write("::End");
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
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    private static void printAllAccountDetailsInBank() {
        /*
        Starting to show all Account Details:

        Showing 4 Accounts Details:
        AcctId: ACC-00001, AcctOwner: John Doe, AcctBalance: 3000
        AcctId: ACC-00002, AcctOwner: Rob Johnson, AcctBalance: 5524
        AcctId: ACC-00003, AcctOwner: Dwante Straits, AcctBalance: 300552340
        AcctId: ACC-00004, AcctOwner: Gwynyth Paltrow, AcctBalance: 3023400
        ---------------------------------------------------

         */
        System.out.println("Starting to show all Account Details:");
        System.out.println("Showing "+accountsMap.size()+" Accounts Details:");

        Collection <Account> accountValuesList = accountsMap.values();
        Iterator<Account> iter = accountValuesList.iterator();

        while(iter.hasNext()){
            AbstractAccount account = (AbstractAccount)iter.next();
            System.out.println("AcctId: "+account.getAccountId()+", AcctOwner: "+account.getCustomerId()+"" +
                    ", AcctBalance: "+account.getAccountBalance());
        }






    }

    private static void printTransactionsOfAccount() {
        System.out.println("============printing transactions ==================");

        //Showing 4 Transactions:6

        System.out.println("Showing "+transactionsMap.size()+" Transactions:");

        // using for-each loop for iteration over Map.entrySet()
        for (Map.Entry<String,Transaction> entry : transactionsMap.entrySet()) {
            Transaction transaction = entry.getValue();
            System.out.println("transactionID:   "+transaction.getTransactionId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = simpleDateFormat.format(transaction.getDate());
            System.out.println("Date: "+date);
            System.out.println(transaction.isDebitOrCredit()+": "+transaction.getAmount());
            System.out.println("Balance: "+transaction.getBalance());
            System.out.println("===========================================================");
        }

    }





    private static void transferMoneyBetweenAccounts() {
        /*
        Starting Money Transfer:
        Enter Source Account Id: ACC-0000&
        Account Not Found , Enter another AccountId : ACC-00002
        Found Source AccountID

        Enter Destination Account Id: ACC-0000&
        Account Not Found , Enter another AccountId : ACC-00002
        Found Destination AccountID

        How much money you want to transfer: W@#$
        Please enter a valid number.
        How much money you want to transfer: 400
        Checking the source account (if it has enough money)
        Source Account Balance is less than 400, please enter another amount: 300
        Transferring money...
        Money Transferred.
        Showing Account Details for source and destination accounts.
        Source: AcctId: ACC-00001, AcctOwner: John Doe, AcctBalance: 3000
        Destination: AcctId: ACC-00002, AcctOwner: Rob Johnson, AcctBalance: 5524
  */


        System.out.println("Starting Money Transfer:");

        String sourceAccountID = getAccountIdFromUser("Enter Source Account Id: ");

        System.out.println("%%%%%%%%%%%%%%%%%%%%% sourceAccountID " + sourceAccountID);


        String destinationAccountID = getAccountIdFromUser("Enter Destination Account Id: ");

        System.out.println("%%%%%%%%%%%%%%%%%%%%% destinationAccountID " + destinationAccountID);

        int money2Withdraw = optionFromUser("How much money you want to transfer:");

        AbstractAccount sourceAccount = (AbstractAccount)accountsMap.get(sourceAccountID);
        AbstractAccount destinationAccount = (AbstractAccount)accountsMap.get(destinationAccountID);


        while(money2Withdraw > sourceAccount.getAccountBalance()){
            System.out.println("Source Account Balance is less than "+money2Withdraw+", please enter another amount:");
            money2Withdraw = optionFromUser("How much money you want to transfer:");
        }

        System.out.println("money2Withdraw ===========> "+money2Withdraw);

        try {
            sourceAccount.transferMoneyOut(new Double(money2Withdraw));
            destinationAccount.transferMoneyIn(new Double(money2Withdraw));

            String transactionId = generateTransactionId(sourceAccount.getAccountId());
            currentTransactionId = transactionId;

            Transaction transaction = new Transaction(transactionId,new Date(),false, ((AbstractAccount) sourceAccount).getAccountBalance(),new Double(money2Withdraw));
            System.out.println("==Transaction ==> "+ transaction);

            transactionsMap.put(transactionId,transaction);
            //==========for second transaction
            transactionId = generateTransactionId(destinationAccount.getAccountId());
            currentTransactionId = transactionId;

            transaction = new Transaction(transactionId,new Date(),true, ((AbstractAccount) destinationAccount).getAccountBalance(),new Double(money2Withdraw));
            System.out.println("==Transaction ==> "+ transaction);

            transactionsMap.put(transactionId,transaction);


        } catch (ExceedingTotalBalanceException e) {
            System.out.println("Money to withdraw exceeds total balance");
            e.printStackTrace();
        } catch (ExceedingDailyLimitException e) {
            System.out.println("Money to withdraw exceeds daily limit");
            e.printStackTrace();
        }
        /*
        Transferring money...
        Money Transferred.
        Showing Account Details for source and destination accounts.
        Source: AcctId: ACC-00001, AcctOwner: John Doe, AcctBalance: 3000
        Destination: AcctId: ACC-00002, AcctOwner: Rob Johnson, AcctBalance: 5524
        */

        System.out.println("Transferring money...");
        System.out.println("Money Transferred.");

        System.out.println("Source: AcctId: "+sourceAccount.getAccountId()+", AcctOwner: "+sourceAccount.getCustomerId()+"" +
                ", AcctBalance: "+sourceAccount.getAccountBalance());

        System.out.println("Destination AcctId: "+destinationAccount.getAccountId()+", AcctOwner: "+destinationAccount.getCustomerId()+"" +
                ", AcctBalance: "+destinationAccount.getAccountBalance());

    }

    private static void doMoneyWithdraw() {
        System.out.println("Starting Money WithdrawL:");

        /*
        Starting Money WithdrawL:
        Enter Account Id: ACC-0000&
        Account Not Found , Enter another AccountId : ACC-00002
        Enter money to withdraw: 400
        Money Withdrawn..
        Showing Account Details:
        AcctId: ACC-00002, AcctOwner: John Doe, AcctBalance: 2600

         */
        String accountID = getAccountIdFromUser("Enter Account Id: ");

        System.out.println("%%%%%%%%%%%%%%%%%%%%% accountID " + accountID);

        int money2Withdraw = optionFromUser("Enter money to withdraw:");

        AbstractAccount account = (AbstractAccount)accountsMap.get(accountID);

        try {
            account.transferMoneyOut(new Double(money2Withdraw));
            System.out.println("Money Withdrawn..");

            String transactionId = generateTransactionId(account.getAccountId());
            currentTransactionId = transactionId;

            Transaction transaction = new Transaction(transactionId,new Date(),false, ((AbstractAccount) account).getAccountBalance(),new Double(money2Withdraw));
            System.out.println("==Transaction ==> "+ transaction);

            transactionsMap.put(transactionId,transaction);
        } catch (ExceedingTotalBalanceException e) {
            System.out.println("Drawn amount is more than total balance");
            e.printStackTrace();
        }
        System.out.println("Showing Account Details:");
        System.out.println("AcctId: "+account.getAccountId()+", AcctOwner: "+account.getCustomerId()+"" +
                ", AcctBalance: "+account.getAccountBalance());


    }

    private static void doMoneyDeposit() {
        System.out.println("=============> doMoneyDeposit()");
        System.out.println("Staring money deposit");

        /*
        Staring money deposit:
        Enter Account Id: ACC-0000&
        Account Not Found , Enter another AccountId : ACC-00002
        Enter money to deposit: 400
        Money Deposited..
        Showing Account Details:
        AcctId: ACC-00002, AcctOwner: John Doe, AcctBalance: 3400

         */

        String accountID = getAccountIdFromUser("Enter Account Id: ");

        System.out.println("%%%%%%%%%%%%%%%%%%%%% accountID " + accountID);

        int money2Deposit = optionFromUser("Enter money to deposit:");

        AbstractAccount account = (AbstractAccount)accountsMap.get(accountID);
        try {
            account.transferMoneyIn(new Double(money2Deposit));
            System.out.println("Money Deposited..");

            String transactionId = generateTransactionId(account.getAccountId());
            currentTransactionId = transactionId;

            Transaction transaction = new Transaction(transactionId,new Date(),true, ((AbstractAccount) account).getAccountBalance(),new Double(money2Deposit));
            System.out.println("==Transaction ==> "+ transaction);

            transactionsMap.put(transactionId,transaction);

        } catch (ExceedingDailyLimitException e) {
            System.out.println("Not Able to deposit money, the amount is exceeding Daily Limit");
            e.printStackTrace();
        }
        System.out.println("Showing Account Details:");
        System.out.println("AcctId: "+account.getAccountId()+", AcctOwner: "+account.getCustomerId()+"" +
                ", AcctBalance: "+account.getAccountBalance());

    }

    private static void showDetailsOfAccount() {
        System.out.println("=============> showDetailsOfAccount()");

        //Enter Account Id: ACC-0000&
        //Account Not Found , Enter another AccountId : ACC-00002

        //Showing Account Details:
        //AcctId: ACC-00002, AcctOwner: John Doe, AcctBalance: 3000

        String accountID = getAccountIdFromUser("Enter Account Id: ");

        System.out.println("%%%%%%%%%%%%%%%%%%%%% accountID " + accountID);

        AbstractAccount account = (AbstractAccount)accountsMap.get(accountID);

        System.out.println("Showing Account Details:");
        System.out.println("AcctId: "+account.getAccountId()+", AcctOwner: "+account.getCustomerId()+"" +
                ", AcctBalance: "+account.getAccountBalance());






    }

    private static void createNewAccount() {
        System.out.println("==============> creating new account");
        int accountType = typeOfAccount();

        int customerType = typeOfCustomer();

        String customerName = optionStrFromUser("Enter Customer Name ");
        String customerAddress = optionStrFromUser("Enter Customer Address ");
        String customerPhone = optionStrFromUser("Enter Customer Phone ");

        int typeOfMailPreference = typeOfMailPreference();
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
//            ((PersonalCustomer) customer).setName(customerName);
//            ((PersonalCustomer) customer).setAddress(customerAddress);
//            ((PersonalCustomer) customer).setPhone(customerPhone);
//            ((PersonalCustomer) customer).setMailPreference(mailPreference);
        } else if (customerType == 2) {
            customer = new BusinessCustomer();
//            ((BusinessCustomer) customer).setName(customerName);
//            ((PersonalCustomer) customer).setAddress(customerAddress);
//            ((BusinessCustomer) customer).setPhone(customerPhone);
//            ((BusinessCustomer) customer).setMailPreference(mailPreference);
        }

        ((AbstractCustomer) customer).setName(customerName);
        ((AbstractCustomer) customer).setAddress(customerAddress);
        ((AbstractCustomer) customer).setPhone(customerPhone);
        ((AbstractCustomer) customer).setMailPreference(mailPreference);


        String accountId = generateAccountId();
        BankingApp.currentAccountId = accountId;

        Account account = null;
        if (accountType == 1) {
            System.out.println("Debit Account");
            account = new DebitAccount();
            //((DebitAccount) account).setAccountType(AccountType.CHEQUING_ACCOUNT);
//            ((DebitAccount) account).setAccountId(accountId);
//            //((DebitAccount) account).setAccountOwnerId();
//            ((DebitAccount) account).setAccountBalance(2000.0);
//
//            String transactionId = generateTransactionId(BankingApp.currentAccountId);
//            currentTransactionId = transactionId;
//
//            Transaction transaction = new Transaction(transactionId,new Date(),true, ((DebitAccount) account).getAccountBalance());
//            System.out.println("==Transaction ==> "+ transaction);
//
//            transactionsMap.put(transactionId,transaction);
//
//            accountsMap.put(((DebitAccount) account).getAccountId(), account);

        } else if (accountType == 2) {
            System.out.println("Savings Account");
            account = new SavingsAccount();
            //((SavingsAccount) account).setAccountType(AccountType.SAVING_ACCOUNT);
//            ((SavingsAccount) account).setAccountId(accountId);
//            ((SavingsAccount) account).setAccountBalance(2000.0);
//            accountsMap.put(((SavingsAccount) account).getAccountId(), account);

        }


        ((AbstractAccount) account).setAccountId(accountId);
        //((AbstractAccount) account).setAccountOwnerId();
        Double amount = new Double(2000);
        ((AbstractAccount) account).setAccountBalance(amount);

        String transactionId = generateTransactionId(BankingApp.currentAccountId);
        currentTransactionId = transactionId;

        Transaction transaction = new Transaction(transactionId,new Date(),true, ((AbstractAccount) account).getAccountBalance(),amount);
        System.out.println("==Transaction ==> "+ transaction);

        transactionsMap.put(transactionId,transaction);

        accountsMap.put(((AbstractAccount) account).getAccountId(), account);



        String customerId = generateCustomerId();
        BankingApp.currentCustomerId = customerId;

//        if (customer instanceof PersonalCustomer) {
//            ((PersonalCustomer) customer).setCustomerId(customerId);
//            ((AbstractAccount)account).setCustomerId(customerId);
//
//            ((PersonalCustomer) customer).getCustomerAccounts().add(account);
//            customersMap.put(((PersonalCustomer) customer).getCustomerId(), customer);
//
//            System.out.println("Creating Account...");
//            System.out.println("Account Created.");
//
//            System.out.println("========Account Details======");
//            System.out.println("Owner Name: "+((PersonalCustomer) customer).getName());
//            if (accountType == 1) {
//                System.out.println("AccountId is : "+((DebitAccount)account).getAccountId());
//                System.out.println("Account Balance: "+((DebitAccount)account).getAccountBalance());
//            } else if (accountType == 2) {
//                System.out.println("AccountId is : "+((SavingsAccount)account).getAccountId());
//                System.out.println("Account Balance: "+((SavingsAccount)account).getAccountBalance());
//            }
//
//
//        } else if (customer instanceof BusinessCustomer) {
//            ((BusinessCustomer) customer).setCustomerId(customerId);
//            ((AbstractAccount)account).setCustomerId(customerId);
//            ((BusinessCustomer) customer).getCustomerAccounts().add(account);
//            customersMap.put(((BusinessCustomer) customer).getCustomerId(), customer);
//
//            System.out.println("Creating Account...");
//            System.out.println("Account Created.");
//
//            System.out.println("========Account Details======");
//            System.out.println("Owner Name: "+((BusinessCustomer) customer).getName());
//            if (accountType == 1) {
//                System.out.println("AccountId is : "+((DebitAccount)account).getAccountId());
//                System.out.println("Account Balance: "+((DebitAccount)account).getAccountBalance());
//            } else if (accountType == 2) {
//                System.out.println("AccountId is : "+((SavingsAccount)account).getAccountId());
//                System.out.println("Account Balance: "+((SavingsAccount)account).getAccountBalance());
//            }
//        }

        ((AbstractCustomer) customer).setCustomerId(customerId);
        ((AbstractAccount)account).setCustomerId(customerId);

        ((AbstractCustomer) customer).getCustomerAccounts().add(account);
        customersMap.put(((AbstractCustomer) customer).getCustomerId(), customer);

        System.out.println("Creating Account...");
        System.out.println("Account Created.");

        System.out.println("========Account Details======");
        System.out.println("Owner Name: "+((AbstractCustomer) customer).getName());

        System.out.println("AccountId is : "+((AbstractAccount)account).getAccountId());
        System.out.println("Account Balance: "+((AbstractAccount)account).getAccountBalance());


        //customerList.add(customer);

        //System.out.println(customer);
        //accountList.add(account);


        //System.out.println(account);


    }

    private static String generateTransactionId(String accountID) {
        //transactionID: ACC-00001-TX-00001
        String currentTransactionIdStr = BankingApp.currentTransactionId;
        String transactionIdStr = null;
        if (currentTransactionIdStr == null || currentTransactionIdStr.equals("")) {
            Integer accountNumber = new Integer(1);
            //accountNumber++;
            String str = String.format("%05d", accountNumber);
            transactionIdStr = accountID+"-TX-" + str;
        } else {
            String transactionNumberStr = currentTransactionIdStr.substring(currentTransactionIdStr.lastIndexOf("-") + 1);
            int transactionNumberInt = Integer.parseInt(transactionNumberStr);
            transactionNumberInt++;
            Integer transactionNumber = new Integer(transactionNumberInt);
            //accountNumber++;
            String str = String.format("%05d", transactionNumber);
            transactionIdStr = accountID+"-TX-" + str;
        }

        return transactionIdStr;

    }

    private static int typeOfMailPreference() {

        boolean inValidInput = true;
        while (inValidInput) {

            int typeOfMailPreference = optionFromUser("Provide Customer mail preference: Email, postalMail, TextMessage [1,2,3]:");
            //Provide Customer mail preference: Email, postalMail, TextMessage [1,2,3]:

            if (typeOfMailPreference == 1) {
                inValidInput = false;
                return 1;

            } else if (typeOfMailPreference == 2) {
                inValidInput = false;
                return 2;
            } else if (typeOfMailPreference == 3) {
                inValidInput = false;
                return 3;
            } else {
                inValidInput = true;
                System.out.println("Please select appropriate type of mail preference 1,2,3");

            }
        }

        return 0;

    }

    private static String generateAccountId() {
        String currentAccountIdStr = BankingApp.currentAccountId;
        String accountIdStr = null;
        if (currentAccountIdStr == null || currentAccountIdStr.equals("")) {
            Integer accountNumber = new Integer(1);
            //accountNumber++;
            String str = String.format("%05d", accountNumber);
            accountIdStr = "ACC-" + str;
        } else {
            String accountNumberStr = currentAccountIdStr.substring(currentAccountIdStr.indexOf("-") + 1);
            int accountNumberInt = Integer.parseInt(accountNumberStr);
            accountNumberInt++;
            Integer accountNumber = new Integer(accountNumberInt);
            //accountNumber++;
            String str = String.format("%05d", accountNumber);
            accountIdStr = "ACC-" + str;
        }

        return accountIdStr;
    }

    private static String generateCustomerId() {
        String currentCustomerIdStr = BankingApp.currentCustomerId;
        String customerIdStr = null;
        if (currentCustomerIdStr == null || currentCustomerIdStr.equals("")) {
            Integer customerNumber = new Integer(1);

            String str = String.format("%05d", customerNumber);
            customerIdStr = "CUST-" + str;
        } else {
            String customerNumberStr = currentCustomerIdStr.substring(currentCustomerIdStr.indexOf("-") + 1);
            int customerNumberInt = Integer.parseInt(customerNumberStr);
            customerNumberInt++;
            Integer customerNumber = new Integer(customerNumberInt);

            String str = String.format("%05d", customerNumber);
            customerIdStr = "CUST-" + str;
        }

        return customerIdStr;
    }
    private static String getAccountIdFromUser(String  str2Display) {
        boolean inValidInput = true;
        while (inValidInput) {
            String accountId = optionStrFromUser(str2Display);


                if(accountsMap.containsKey(accountId)){
                    return accountId;
                } else {
                    inValidInput = true;
                    System.out.println("Wrong AccountId :");

                }



        }

        return null;
    }

    private static int typeOfAccount() {
        boolean inValidInput = true;
        while (inValidInput) {
            //Get business or personal customer
            int typeOfAccount = optionFromUser("What kind of account: Debit, Savings [1/2] : ");
            //"What kind of account: Debit, Savings [1/2] : "

            if (typeOfAccount == 1) {
                inValidInput = false;
                return 1;

            } else if (typeOfAccount == 2) {
                inValidInput = false;
                return 2;
            } else {
                inValidInput = true;
                System.out.println("Please select appropriate type of account");
                //typeOfCustomer();
            }
        }

        return 0;
    }

    private static int typeOfCustomer() {
        boolean inValidInput = true;
        while (inValidInput) {
            //Get business or personal customer
            int typeOfCustomer = optionFromUser("Business or Personal Customer: Business, personal [1,2]:");
            //Business or Personal Customer: Business, personal [1,2]: 2

            if (typeOfCustomer == 1) {
                inValidInput = false;
                return 1;

            } else if (typeOfCustomer == 2) {
                inValidInput = false;
                return 2;
            } else {
                inValidInput = true;
                System.out.println("Please select appropriate type of customer");

            }
        }

        return 0;
    }

    private static int optionFromUser(String str2Display) {
        Scanner myObj = new Scanner(System.in);
        //System.out.println("please enter your option ");
        System.out.println(str2Display);
        int input = 0;
        try {
            input = myObj.nextInt();  // Read user input
        } catch (InputMismatchException ex) {
            System.out.println("Please enter correct input");
            input = 0;
        }
        System.out.println("user option is: " + input);

        return input;
    }

    private static String optionStrFromUser(String str2Display) {
        Scanner myObj = new Scanner(System.in);
        //System.out.println("please enter your option ");
        System.out.println(str2Display);
        String input = "";
        try {
            //changed this
            input = myObj.nextLine();  // Read user input
        } catch (InputMismatchException ex) {
            System.out.println("Please enter correct input");
            input = "";
        }
        System.out.println("user option is: " + input);

        return input;
    }

    public static void printBankOptions() {
        System.out.println("Menu:\n" +
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
