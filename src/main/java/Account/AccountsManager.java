package Account;

import java.sql.Connection;

public abstract class AccountsManager {
    static NullAccount nullAccount = new NullAccount();

    final Connection conn;

    public AccountsManager(Connection conn) {
        this.conn = conn;
    }

    void transfer(Account sender, Account receiver,int amount){
        sender.withdraw(amount);
        receiver.deposit(amount);
        try {
            //change values in database, if error, then revert
        } catch (Exception e) {
            sender.deposit(amount);
            receiver.withdraw(amount);
            e.printStackTrace();
        }
    }


}
