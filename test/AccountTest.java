import com.bank.DebitAccount;
import com.bank.ExceedingDailyLimitException;
import org.junit.Assert;
import org.junit.Test;

public class AccountTest {
    @Test
    public void testTransferMoneyIn() throws ExceedingDailyLimitException {
        DebitAccount debitAccount = new DebitAccount();

        debitAccount.setAccountBalance(200.0);
        debitAccount.transferMoneyIn(100.0);

        Assert.assertTrue(debitAccount.getAccountBalance()==300.0);
    }

    @Test(expected = ExceedingDailyLimitException.class)
    public void testTransferMoneyInDailyLimit() throws ExceedingDailyLimitException {
        DebitAccount debitAccount = new DebitAccount();

        debitAccount.setAccountBalance(200.0);
        debitAccount.transferMoneyIn(4000.0);

        Assert.assertTrue(debitAccount.getAccountBalance()==4200.0);
    }
}
