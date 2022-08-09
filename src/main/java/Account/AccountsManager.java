package Account;

import DatabaseHandlers.DatabaseHandler;
import Transaction.Transaction;
import javafx.util.Pair;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.*;
//import JOption

public abstract class AccountsManager {
    public static NullAccount nullAccount = new NullAccount();
    final DatabaseHandler databaseHandler;
//    final Connection conn;
    Map<UUID,Account> accountMap = new HashMap<>();

    public static int countPerPage = Integer.MAX_VALUE;
    public static int page = 1;

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

    public boolean updateAccountValue(UUID id, double newAmount){
        Account acc = accountMap.get(id);
        //TODO:Throw custom exceptions here
        if (acc==null)return false;
        if (acc.getType()==AccountType.Null)return false;
        double oldAmount = acc.amount;
        acc.setValue(newAmount);
        if (!databaseHandler.updateAccountValue(id,newAmount)){
            acc.setValue(oldAmount);
            return false;
        }
        return true;
    }

    public void createNewAccount(String name, double amount,String type){
        if (name==null || type==null) return;
        Account acc = Account.createAccount(name,amount,type);
        addAccount(acc);
    }
    //this should record values in database:
    //add to list of transactions, and update amount in accounts
    //if all of those are successful only then should local values
    //be updated.
     public boolean recordTransaction(
            UUID senderId, UUID receiverId, double amount,
            String name, String description, Timestamp timestamp,
            String category
    ){
        Transaction tr = new Transaction(senderId, receiverId, name, timestamp, description, amount, category);
        Account sender = accountMap.get(senderId);
        Account receiver = accountMap.get(receiverId);
        if (sender==receiver) return false;
        if (sender==null ||receiver==null)return false;

        double oldSValue = sender.amount;
        double oldRValue = receiver.amount;

        sender.setValue(oldSValue-amount);
        receiver.setValue(oldRValue+amount);


        if (!databaseHandler.recordTransaction(tr,sender.amount,receiver.amount)) {
            sender.setValue(oldSValue);
            receiver.setValue(oldRValue);
            return false;
        }
        if (transactions.isEmpty()){
            transactions.add(tr);
//            return true;
        }
        int compare = tr.getTimestamp().compareTo( transactions.last().getTimestamp());
//         System.out.println(compare);
        if ((compare >=0)){
//            System.out.println("here");
            transactions.add(tr);
        }
        return true;
     }

    //fetch next page of transactions
    public ArrayList<Transaction>  fetchMoreTransactions(){
        ArrayList<Transaction> newTransactions = databaseHandler.fetchTransactions(page,countPerPage);
//        System.out.println(newTransactions.size());
        if (newTransactions==null || newTransactions.isEmpty())return null;
        transactions.addAll(newTransactions);

        page++;
        return newTransactions;
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

    public  ArrayList<Transaction> getTransactionsOf(UUID accountId,int page, int count){
        return databaseHandler.getTransactionsOf(accountId, page, count);
    }

    public ArrayList<String> getCategories(){
        return databaseHandler.getCategories();
    }

    public ArrayList<Pair<String, Double>> getCategoriesWithAmounts(){
        return databaseHandler.getCategoriesWithAmounts();
    }
}
