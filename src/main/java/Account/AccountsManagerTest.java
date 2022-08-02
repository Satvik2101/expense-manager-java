package Account;

import DatabaseHandlers.DatabaseHandlerTest;

public class AccountsManagerTest extends  AccountsManager{
    public AccountsManagerTest() {
        super(new DatabaseHandlerTest());
//        init();
    }
}
