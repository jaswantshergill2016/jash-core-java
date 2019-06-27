package service;

import com.bank.BankingApp;
import com.bank.service.CreateAccountService;
import org.junit.Assert;
import org.junit.Test;

public class CreateAccountServiceTest {

    @Test
    public void testCreateNewAccount(){

        /*BankingApp.accountsMap =*/ new CreateAccountService().createNewAccount(
                1, 1,
                "Jaswant","Toronto",
                "1234567890",1
                /*BankingApp.accountsMap*/);
        Assert.assertTrue(BankingApp.accountsMap.size()==1);
        Assert.assertTrue(BankingApp.customersMap.size()==1);
    }




}
