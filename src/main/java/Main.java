import Account.AccountsManager;
import Account.AccountsManagerTest;
import Transaction.Transaction;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main {

    static void outputTr(Transaction tr){
        System.out.println(
                tr.getSenderId()+" "+tr.getReceiverId()+" "+tr.getName()+" "+tr.getDescription()+" "+tr.getTimestamp().toString()+" "+tr.getAmount()
        );
    }
    public static void main(String[] args) {
//        Connection connection =null;
        AccountsManager manager = new AccountsManagerTest();
        HashMap<String,UUID> namesToIds = manager.getAccountNamesWithIDs();
        for (Map.Entry<String,UUID> entry:namesToIds.entrySet()){
            System.out.println(entry.getValue()+" -> "+entry.getKey());
        }
        System.out.println();
//        System.out.println("hello world");
        for (Transaction tr: manager.transactions){
            outputTr(tr);
        }
        System.out.println();
        manager.fetchMoreTransactions();
        for (Transaction tr: manager.transactions){
            outputTr(tr);
        }
    }

}
