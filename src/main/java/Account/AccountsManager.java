package Account;

import DatabaseHandlers.DatabaseHandler;
import Transaction.Transaction;

import java.sql.Timestamp;
import java.util.*;
//import JOption

public abstract class AccountsManager {
    public static NullAccount nullAccount = new NullAccount();
    final DatabaseHandler databaseHandler;
//    final Connection conn;
    Map<UUID,Account> accountMap = new HashMap<>();

    static int countPerPage = 3;
    static int page = 1;

    public String getNameOfUUID(UUID id){
        if (accountMap.get(id)==null)return null;
        return accountMap.get(id).name;
    }
    //store transactions here, don't fetch all at once, use paging
    public SortedSet<Transaction> transactions;
    public ArrayList<Account> getAccounts(){
        ArrayList<Account> ans = new ArrayList<>();
        for (Map.Entry<UUID, Account> element:accountMap.entrySet()){
            ans.add(element.getValue());
        }
        return ans;
    }

    public AccountsManager(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
//        this.conn = conn;
        transactions = new TreeSet<>();
        fetchAccounts();
        fetchMoreTransactions();
//        addAccount(nullAccount);
//        System.out.println("HERE");
    }

    void putAccountInAccMap(Account acc){
        accountMap.put(acc.id,acc);
    }
    void addAccount(Account acc){
        putAccountInAccMap(acc);
        databaseHandler.addAccount(acc);

    }



    //TODO: Change later to get account type as well
    public void addAccount(String name, double amount){
        Account acc = new CashAccount(name,amount);
        addAccount(acc);
    }
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
        int compare = tr.getTimestamp().compareTo( transactions.last().getTimestamp());
         System.out.println(compare);
        if (transactions.isEmpty() ||(compare >=0)){
//            System.out.println("here");
            transactions.add(tr);
        }
     }

    //fetch next page of transactions
    public void fetchMoreTransactions(){
        ArrayList<Transaction> newTransactions = databaseHandler.fetchTransactions(page,countPerPage);
//        System.out.println(newTransactions.size());
        if (newTransactions==null || newTransactions.isEmpty())return ;
        transactions.addAll(newTransactions);

        page++;
    }
    public void fetchAccounts(){
        ArrayList<Account> newAccounts = databaseHandler.fetchAccounts();
        if (newAccounts==null)return;
        for (Account acc:newAccounts){
            putAccountInAccMap(acc);
            if (acc instanceof NullAccount){
                nullAccount = (NullAccount)acc;
            }
        }
    }
}
