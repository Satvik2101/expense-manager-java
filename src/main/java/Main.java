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
//            Scanner in = new Scanner(System.in);
//            System.out.println("Enter name");
//            String name = in.next();
//            System.out.println("Enter amt");
//            double amount = in.nextDouble();
        for (Transaction tr:manager.transactions){
            outputTr(tr);
        }
        System.out.println("---------------------------------------");
//            manager.addAccount(name,amount);
        ArrayList<Account> accounts = manager.getAccounts();
        for (Account ac:accounts){
            System.out.println(ac.name+ " "+ac.id+" "+ac.amount);
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Enter sender idx");
        int sIdx = in.nextInt();
        System.out.println("Enter receiver idx");
        int rIdx = in.nextInt();
        System.out.println("Enter amount");
        double amount = in.nextDouble();
        System.out.println("Enter name");
        String name = in.next();
        Timestamp time = Timestamp.from(Instant.now());
        manager.recordTransaction(accounts.get(sIdx).id,accounts.get(rIdx).id,amount,name,null,time);

//        UUID id = accounts.get(idx).id;
//        manager.updateAccountValue(id,1200);
//        System.out.println(accounts.get(idx).amount);
        accounts = manager.getAccounts();
        for (Account ac:accounts){
            System.out.println(ac.name+ " "+ac.id+" "+ac.amount);
        }
        for (Transaction tr:manager.transactions){
            outputTr(tr);
        }

        System.out.println("DONE");

        //

    }

}
