package Account;

import DatabaseHandlers.AccountsDatabaseHandler;
import Transaction.Transaction;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
//import JOption

public abstract class AccountsManager {
    public static NullAccount nullAccount = new NullAccount();
      final AccountsDatabaseHandler databaseHandler;
    final Connection conn;
    Map<UUID,Account> accountMap = new HashMap<UUID, Account>();
    ArrayList<Transaction> transactions;
    public AccountsManager(AccountsDatabaseHandler databaseHandler, Connection conn) {
        this.databaseHandler = databaseHandler;
        this.conn = conn;
        transactions = new ArrayList<Transaction>();
    }

    void transfer(Account sender, Account receiver,double amount){
        if (sender==receiver)return;
        sender.withdraw(amount);
        receiver.deposit(amount);

    }

    void addAccount(Account acc){
        accountMap.put(acc.id,acc);
    }
    void transfer(UUID senderId, UUID receiverId, double amount){
        Account sender = accountMap.getOrDefault(senderId,nullAccount);
        Account receiver = accountMap.getOrDefault(receiverId,nullAccount);

//        if (sender==receiver)return;
        transfer(sender,receiver,amount);
    }

    //this should record values in database:
    //add to list of transactions, and update amount in accounts
    //if all of those are successful only then should local values
    //be updated.
    abstract void recordTransaction(
            UUID senderId, UUID receiverId, double amount,
            String name, String description, Timestamp timestamp
    );
//    abstract void performTransaction();
}
