package DatabaseHandlers;

import Account.Account;
import Transaction.Transaction;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

public abstract class DatabaseHandler {
//     final Connection conn;
//    ArrayList<Transaction> transactions;

    protected DatabaseHandler() {

    }

    public abstract boolean recordTransaction(Transaction transaction);
//    public abstract ArrayList<Transaction> getAccountTransactions(Account account);
    public abstract boolean updateAccountValue(UUID accountID,double newAmount);

    public abstract ArrayList<Transaction> fetchTransactions(int page,int count);

    public abstract boolean addAccount(Account acc);
    public abstract ArrayList<Account> fetchAccounts();


}
