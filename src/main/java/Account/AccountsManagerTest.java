package Account;

import DatabaseHandlers.AccountsDatabaseHandler;
import DatabaseHandlers.AccountsDatabaseHandlerTest;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.UUID;

public class AccountsManagerTest extends  AccountsManager{
    public AccountsManagerTest(Connection conn) {
        super(new AccountsDatabaseHandlerTest(conn), conn);
        init();
    }
    void init(){
        Account cash = new CashAccount("Cash",1000);
        Account supplementary = new CashAccount("Supplementary",3000);
        Account metroCard = new CashAccount("Metro Card",300);
        Account giftMoney = new CashAccount("Gift",5000);
        addAccount(cash);
        addAccount(supplementary);
        addAccount(metroCard);
        addAccount(giftMoney);
    }


    @Override
    void recordTransaction(UUID senderId,
                           UUID receiverId,
                           double amount,
                           String name,
                           String description,
                           Timestamp timestamp) {

    }
}
