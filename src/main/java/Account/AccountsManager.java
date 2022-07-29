package Account;

import DatabaseHandlers.AccountsDatabaseHandler;
import Transaction.Transaction;

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

    static int countPerPage = 10;
    static int page = 1;
    //are more transactions left?
    public static boolean moreTransactions = true;

    //store transactions here, don't fetch all at once, use paging
    ArrayList<Transaction> transactions;

    public AccountsManager(AccountsDatabaseHandler databaseHandler, Connection conn) {
        this.databaseHandler = databaseHandler;
        this.conn = conn;
        transactions = new ArrayList<>();
    }

//    void transfer(Account sender, Account receiver,double amount){
//        if (sender==receiver)return;
//        sender.withdraw(amount);
//        receiver.deposit(amount);
//
//    }

    void addAccount(Account acc){
        accountMap.put(acc.id,acc);
    }

//    void transfer(UUID senderId, UUID receiverId, double amount){
//        Account sender = accountMap.getOrDefault(senderId,nullAccount);
//        Account receiver = accountMap.getOrDefault(receiverId,nullAccount);
//
////        if (sender==receiver)return;
//        transfer(sender,receiver,amount);
//    }

    //this should record values in database:
    //add to list of transactions, and update amount in accounts
    //if all of those are successful only then should local values
    //be updated.
     public void recordTransaction(
            UUID senderId, UUID receiverId, double amount,
            String name, String description, Timestamp timestamp
    ){
        Transaction tr = new Transaction(senderId, receiverId, name, timestamp, description, amount);
        databaseHandler.recordTransaction(tr);
     }

    //fetch next page of transactions
    public void fetchMoreTransactions(){
        if (!moreTransactions) return;
        ArrayList<Transaction> newTransactions = databaseHandler.fetchTransactions(page,countPerPage);

        if (newTransactions==null || newTransactions.size()<countPerPage){
            moreTransactions = false;
        }
        if (newTransactions==null || newTransactions.isEmpty())return;
        transactions.addAll(newTransactions);
        page++;
    }
}
