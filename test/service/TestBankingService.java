package service;

import com.bank.*;
import com.bank.service.CreateAccountService;
import com.bank.service.MoneyDepositService;
import com.bank.service.MoneyWithdrawService;
import com.bank.service.TransferService;
import org.junit.*;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestBankingService {

    private static Map<String, Account> accountsMap = new HashMap<>();
    private static  Map<String, Customer> customersMap = new HashMap<>();
    private static  Map<String, Transaction> transactionsMap = new HashMap<>();


    private static String currentAccountId = "";
    private static String currentCustomerId = "";
    private static String currentTransactionId = "";


    @Test
    public void aTestCreateNewAccount(){

        new CreateAccountService().createNewAccount(
                1, 1,
                "Jaswant","Toronto",
                "1234567890",1,
                accountsMap,customersMap,transactionsMap//,
                //currentAccountId,currentCustomerId,currentTransactionId

        );

        System.out.println("****accountsMap.size()****"+accountsMap.size());
        Assert.assertTrue(accountsMap.size()==1);
        Assert.assertTrue(customersMap.size()==1);
    }

    @Test
    public void bTestMoneyWithDraw(){
        AbstractAccount account = (AbstractAccount) accountsMap.get("ACC-00001");
        new MoneyWithdrawService().withdrawMoney(account,500);
        Assert.assertTrue(account.getAccountBalance() == 1500.0D);
    }

    @Test
    public void cTestMoneyWithDraw(){
        AbstractAccount account = (AbstractAccount) accountsMap.get("ACC-00001");
        new MoneyDepositService().depositMoney(account,500);
        Assert.assertTrue(account.getAccountBalance() == 2000.0D);
    }

    @Test
    public void dTestTransferBetweenAccounts(){

        new CreateAccountService().createNewAccount(
                1, 1,
                "Kulwant","Saskatoon",
                "8847437575",1,
                accountsMap,customersMap,transactionsMap//,
                //currentAccountId,currentCustomerId,currentTransactionId
        );


        AbstractAccount sourceAccount = (AbstractAccount)accountsMap.get("ACC-00001");
        AbstractAccount destinationAccount = (AbstractAccount)accountsMap.get("ACC-00002");


        new TransferService().transferMoneyBetweenAccounts(sourceAccount, destinationAccount,500);
        Assert.assertTrue(sourceAccount.getAccountBalance() == 1500.0D);
        Assert.assertTrue(destinationAccount.getAccountBalance() == 2500.0D);
    }
}
