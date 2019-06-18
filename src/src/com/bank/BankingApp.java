package com.bank;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

public class BankingApp {

    private static Logger logger = Logger.getLogger(BankingApp.class);

    //public static List<Account> accountList = new LinkedList<>();
    //public static List<Customer> customerList = new ArrayList<>();
    public static Map<String, Account> accountsMap = new HashMap<>();
    public static Map<String, Customer> customersMap = new HashMap<>();

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



                        System.out.println("accountId ====> "+accountId);
                        System.out.println("accountBalance ====> "+accountBalance);

                    }
                    if(readLine.equals("::Customers")){
                        System.out.println("====Customers======");
                        while((readLine = bufferedReader.readLine())!= null && !readLine.equals("::Current Account Id")){
                            System.out.println(readLine);
                            //String [] accountValues = readLine.split(":");
                        }
                    }
                    if(readLine != null && readLine.equals("::Current Account Id")){
                        System.out.println("====::Current Account Id======");
                        while((readLine = bufferedReader.readLine())!= null && !readLine.equals("::Current Customer Id")){
                            System.out.println(readLine);
                            //String [] accountValues = readLine.split(":");
                        }
                    }
                    if(readLine != null && readLine.equals("::Current Customer Id")){
                        System.out.println("====::Current Customer Id======");
                        while((readLine = bufferedReader.readLine())!= null && !readLine.equals("::Current Transaction Id")){
                            System.out.println(readLine);
                            //String [] accountValues = readLine.split(":");
                        }
                    }
                    if(readLine != null && readLine.equals("::Current Transaction Id")){
                        System.out.println("====::Current Transaction Id======");
                        while((readLine = bufferedReader.readLine())!= null ){
                            System.out.println(readLine);
                            //String [] accountValues = readLine.split(":");
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

                        bufferedWriter.write(":" + account.getAccountId() +":"+account.getAccountBalance()+ "\n");
                    }
                }
                bufferedWriter.write("::Customers\n");

                if (customersMap.size() > 0) {
                    Collection<Customer> customerMapValues = customersMap.values();
                    Iterator<Customer> iter1 = customerMapValues.iterator();
                    while (iter1.hasNext()) {
                        AbstractCustomer customer = (AbstractCustomer)iter1.next();
                        bufferedWriter.write(":" + customer.getCustomerId()+":"+ customer.getName()+":"+customer.getPhone()+":"+customer.getAddress() +"\n");
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
            ((PersonalCustomer) customer).setName(customerName);
            ((PersonalCustomer) customer).setPhone(customerPhone);
            ((PersonalCustomer) customer).setMailPreference(mailPreference);
        } else if (customerType == 2) {
            customer = new BusinessCustomer();
            ((BusinessCustomer) customer).setName(customerName);
            ((BusinessCustomer) customer).setAddress(customerAddress);
            ((PersonalCustomer) customer).setAddress(customerAddress);
                        ((BusinessCustomer) customer).setPhone(customerPhone);
            ((BusinessCustomer) customer).setMailPreference(mailPreference);
        }


        String accountId = generateAccountId();
        BankingApp.currentAccountId = accountId;

        Account account = null;
        if (accountType == 1) {
            System.out.println("Debit Account");
            account = new DebitAccount();
            ((DebitAccount) account).setAccountId(accountId);
            //((DebitAccount) account).setAccountOwnerId();
            ((DebitAccount) account).setAccountBalance(2000.0);

            accountsMap.put(((DebitAccount) account).getAccountId(), account);

        } else if (accountType == 2) {
            System.out.println("Savings Account");
            account = new SavingsAccount();
            ((SavingsAccount) account).setAccountId(accountId);
            ((SavingsAccount) account).setAccountBalance(2000.0);
            accountsMap.put(((SavingsAccount) account).getAccountId(), account);

        }


        String customerId = generateCustomerId();
        BankingApp.currentCustomerId = customerId;

        if (customer instanceof PersonalCustomer) {
            ((PersonalCustomer) customer).setCustomerId(customerId);
            ((AbstractAccount)account).setCustomerId(customerId);

            ((PersonalCustomer) customer).getCustomerAccounts().add(account);
            customersMap.put(((PersonalCustomer) customer).getCustomerId(), customer);

            System.out.println("Creating Account...");
            System.out.println("Account Created.");

            System.out.println("========Account Details======");
            System.out.println("Owner Name: "+((PersonalCustomer) customer).getName());
            if (accountType == 1) {
                System.out.println("AccountId is : "+((DebitAccount)account).getAccountId());
                System.out.println("Account Balance: "+((DebitAccount)account).getAccountBalance());
            } else if (accountType == 2) {
                System.out.println("AccountId is : "+((SavingsAccount)account).getAccountId());
                System.out.println("Account Balance: "+((SavingsAccount)account).getAccountBalance());
            }


        } else if (customer instanceof BusinessCustomer) {
            ((BusinessCustomer) customer).setCustomerId(customerId);
            ((AbstractAccount)account).setCustomerId(customerId);
            ((BusinessCustomer) customer).getCustomerAccounts().add(account);
            customersMap.put(((BusinessCustomer) customer).getCustomerId(), customer);

            System.out.println("Creating Account...");
            System.out.println("Account Created.");

            System.out.println("========Account Details======");
            System.out.println("Owner Name: "+((BusinessCustomer) customer).getName());
            if (accountType == 1) {
                System.out.println("AccountId is : "+((DebitAccount)account).getAccountId());
                System.out.println("Account Balance: "+((DebitAccount)account).getAccountBalance());
            } else if (accountType == 2) {
                System.out.println("AccountId is : "+((SavingsAccount)account).getAccountId());
                System.out.println("Account Balance: "+((SavingsAccount)account).getAccountBalance());
            }
        }

        //customerList.add(customer);

        //System.out.println(customer);
        //accountList.add(account);


        //System.out.println(account);


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
            input = myObj.next();  // Read user input
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
