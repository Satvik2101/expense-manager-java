import Account.Account;
import Account.AccountsManager;
import Account.CashAccount;
import Account.MySQLAccountsManager;
import Transaction.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
//    static AccountsManager manager = new ();

    static void outputTr(Transaction tr) {

        System.out.println(
                (tr.getSenderId()) + " " + (tr.getReceiverId()) + " " + tr.getName() + " " + tr.getDescription() + " " + tr.getTimestamp().toString() + " " + tr.getAmount()
        );
    }

    public static void main(String[] args) {
//        Connection connection =null;
        //            Connection conn = DriverManager.getConnection("jdbc:mysql://root:b6famsatvik55@localhost:3306/expense_manager");
         String url = "jdbc:mysql://root:b6famsatvik55@localhost:3306/expense_manager";
         AccountsManager manager = new MySQLAccountsManager(url);
       Scanner in = new Scanner(System.in);
//       System.out.println("Enter name");
//       String name = in.next();
//       System.out.println("Enter amt");
//       double amount = in.nextDouble();
//       manager.createNewAccount(name,amount,"Cash");

       List<Account> accounts = manager.getAccounts();
       int cId;
       int mId;
       int nId;
       int sId;
       for (Account acc:accounts){
           System.out.println(acc.name);
       }
       int fetchMore = 1;
       do {
           for (Transaction tr : manager.transactions) {
               outputTr(tr);
           }
           System.out.println("--------------------------------------------");
       }
       while ((fetchMore=in.nextInt())==1 && manager.fetchMoreTransactions()!=null);
    }

}
