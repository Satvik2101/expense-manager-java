package DatabaseHandlers;

import Account.Account;
import Account.CashAccount;
import Account.AccountsManager;
import Transaction.Transaction;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public class AccountsDatabaseHandlerTest extends AccountsDatabaseHandler{

    public AccountsDatabaseHandlerTest(Connection conn) {
        super(conn);
    }

    @Override
    public boolean recordTransaction(Transaction tr) {
//       //update amounts in db in real
        dummyTransactions.add(tr);
        return true;
    }
    ArrayList<Account> dummyAccounts = new ArrayList<>();
    ArrayList<Transaction> dummyTransactions = new ArrayList<>();


    void init(){
        Account cash = new CashAccount("Cash", 1000);
        Account supplementary = new CashAccount("Supplementary", 3000);
        Account metroCard = new CashAccount("Metro Card", 300);
        Account giftMoney = new CashAccount("Gift", 5000);
        addAccount(cash);
        addAccount(supplementary);
        addAccount(metroCard);
        addAccount(giftMoney);
        UUID nullId = AccountsManager.nullAccount.id;
        Transaction a = new Transaction(cash.id,nullId,
                                        "Starbucks",
                                        new Timestamp(2022,7,22,12,30,0,0),
                                        null,
                                        290
                                        );
        //create more dummy transactions like this
        dummyTransactions.add(a);
    }


    @Override
    public boolean updateAccountValue(Account account) {
        return true;
    }

    @Override
    public ArrayList<Transaction> fetchTransactions(int page, int count) {
        return null;
    }

    @Override
    public boolean addAccount(Account acc) {
        dummyAccounts.add(acc);
        return true;
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
