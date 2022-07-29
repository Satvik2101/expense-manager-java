package DatabaseHandlers;

import Account.Account;
import Transaction.Transaction;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class AccountsDatabaseHandler {
     final Connection conn;
//    ArrayList<Transaction> transactions;

    protected AccountsDatabaseHandler(Connection conn) {
        this.conn = conn;
    }

    public abstract boolean recordTransaction(Transaction transaction);
//    public abstract ArrayList<Transaction> getAccountTransactions(Account account);
    public abstract boolean updateAccountValue(Account account);
}
