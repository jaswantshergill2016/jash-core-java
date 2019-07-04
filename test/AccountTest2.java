
import com.bank.BankingApp;
import com.bank.DebitAccount;
import com.bank.ExceedingDailyLimitException;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class AccountTest2 {
    public AccountTest2() {
//        BankingApp.accountsMap = new HashMap<>();
//        BankingApp.transactionsMap = new HashMap<>();
//        BankingApp.customersMap = new HashMap<>();
    }

    public void testCreateNewAccount(){

    }

    @Test
    public void testTransferMoneyIn() throws ExceedingDailyLimitException {
        DebitAccount debitAccount = new DebitAccount();
        debitAccount.setAccountBalance(200.0D);
        debitAccount.transferMoneyIn(100.0D);
        Assert.assertTrue(debitAccount.getAccountBalance() == 300.0D);
    }

    @Test(expected = ExceedingDailyLimitException.class)
    public void testTransferMoneyInDailyLimit() throws ExceedingDailyLimitException {
        DebitAccount debitAccount = new DebitAccount();
        debitAccount.setAccountBalance(200.0D);
        debitAccount.transferMoneyIn(4000.0D);
        Assert.assertTrue(debitAccount.getAccountBalance() == 4200.0D);
    }
}
