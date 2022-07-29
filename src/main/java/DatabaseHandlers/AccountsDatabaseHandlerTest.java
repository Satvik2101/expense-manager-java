package DatabaseHandlers;

import Account.Account;
import Transaction.Transaction;

import java.sql.Connection;
import java.util.ArrayList;

public class AccountsDatabaseHandlerTest extends AccountsDatabaseHandler{

    public AccountsDatabaseHandlerTest(Connection conn) {
        super(conn);
    }

    @Override
    public boolean recordTransaction(Transaction tr) {
//       //update amounts in db in real
        return true;
    }

    @Override
    public boolean updateAccountValue(Account account) {
        return false;
    }


//    @Override
//    public ArrayList<Transaction> getAccountTransactions(Account account) {
//
//        ArrayList<Transaction> ans = new ArrayList<Transaction>();
//
//        for (Transaction tr:transactions){
//            if (tr.isOfAccount(account)) ans.add(tr);
//        }
//        return ans;
//    }
}
