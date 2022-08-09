package DatabaseHandlers;

import Account.Account;
import Transaction.Transaction;
import javafx.util.Pair;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

public abstract class DatabaseHandler {
//     final Connection conn;
//    ArrayList<Transaction> transactions;

    protected DatabaseHandler() {

    }

    public abstract boolean recordTransaction(Transaction transaction,double newSenderAmount, double newReceiverAmount);
//    public abstract ArrayList<Transaction> getAccountTransactions(Account account);
    public abstract boolean updateAccountValue(UUID accountID,double newAmount);

    public abstract ArrayList<Transaction> fetchTransactions(int page,int count);

    public abstract boolean addAccount(Account acc);
    public abstract ArrayList<Account> fetchAccounts();

    public abstract ArrayList<Transaction> getTransactionsOf(UUID id,int page, int count);
    public abstract  ArrayList<String> getCategories();
    public abstract ArrayList<Pair<String, Double>> getCategoriesWithAmounts();
}
