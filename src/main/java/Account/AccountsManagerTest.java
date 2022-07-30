package Account;

import DatabaseHandlers.AccountsDatabaseHandler;
import DatabaseHandlers.AccountsDatabaseHandlerTest;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.UUID;

public class AccountsManagerTest extends  AccountsManager{
    public AccountsManagerTest() {
        super(new AccountsDatabaseHandlerTest());
//        init();
    }
}
