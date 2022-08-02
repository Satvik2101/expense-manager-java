package DatabaseHandlers;

import Account.Account;
import Account.AccountsManager;
import Account.CashAccount;
import Transaction.Transaction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.UUID;

public class DatabaseHandlerTest extends DatabaseHandler {

    ArrayList<Account> dummyAccounts = new ArrayList<>();
    TreeSet<Transaction> dummyTransactions = new TreeSet<Transaction>();

    public DatabaseHandlerTest() {
        super();
        init();
    }


    @Override
    public boolean recordTransaction(Transaction tr) {
//       //update amounts in db in real
        dummyTransactions.add(tr);
        return true;
    }

    void init() {
        Account cash = new CashAccount("Cash", 1240);

        Account metroCard = new CashAccount("Metro Card", 138);
        Account giftMoney = new CashAccount("Gift", 3500);
        addAccount(cash);
        addAccount(metroCard);
        addAccount(giftMoney);
        UUID nullId = AccountsManager.nullAccount.id;

        Transaction tr = new Transaction(cash.id, nullId,
                                         "BatteredBooks Payment",
                                         new Timestamp(122, 6, 30, 15, 30, 0, 0),
                                         null,
                                         500
        );
        //create more dummy transactions like this
        dummyTransactions.add(tr);
        tr = new Transaction(cash.id, nullId,
                             "Cafe Crew Brew",
                             new Timestamp(122, 6, 28, 15, 30, 0, 0),
                             null,
                             390
        );
        dummyTransactions.add(tr);
        tr = new Transaction(giftMoney.id, cash.id,
                             "",
                             new Timestamp(122, 6, 28, 14, 02, 0, 0),
                             null,
                             1000
        );
        dummyTransactions.add(tr);
        tr = new Transaction( nullId,cash.id,
                             "",
                             new Timestamp(122, 6, 24, 15, 40, 0, 0),
                             null,
                             750
        );
        dummyTransactions.add(tr);
        tr = new Transaction(cash.id, nullId,
                             "CafeCrewBrew",
                             new Timestamp(122, 6, 24, 16, 30, 0, 0),
                             null,
                             250
        );
        dummyTransactions.add(tr);
        tr = new Transaction(cash.id, nullId,
                             "ButterChicken+Naan",
                             new Timestamp(122, 6, 21, 15, 30, 0, 0),
                             null,
                             450
        );
        dummyTransactions.add(tr);
        tr = new Transaction( nullId,cash.id,
                             "For chicken",
                             new Timestamp(122, 6, 21, 15, 30, 0, 0),
                             null,
                             1000
        );
        dummyTransactions.add(tr);
        tr = new Transaction(metroCard.id, nullId,
                             "Twice NSP",
                             new Timestamp(122, 6, 20, 12, 30, 0, 0),
                             null,
                             20
        );
        dummyTransactions.add(tr);
        tr = new Transaction(cash.id, metroCard.id,
                             "MetroCard Recharge at NSP",
                             new Timestamp(122, 6, 20, 12, 30, 0, 0),
                             null,
                             300
        );
        dummyTransactions.add(tr);
        tr = new Transaction(metroCard.id, nullId,
                             "Rithala Twice",
                             new Timestamp(122, 6, 19, 12, 30, 0, 0),
                             null,
                             40
        );
        dummyTransactions.add(tr);

    }


    @Override
    public boolean updateAccountValue(Account account) {
        int idx =-1;
        for (int i=0;i<dummyAccounts.size();i++){
            if (dummyAccounts.get(i).id==account.id){
                idx = i;
                break;
            }
        }
        if (idx==-1)return false;
        dummyAccounts.get(idx).setValue(account.amount);
        return true;
    }

    @Override
    public ArrayList<Transaction> fetchTransactions(int page, int count) {
        int skip = (page-1)*count;
        ArrayList<Transaction> ans = new ArrayList<>();
        ArrayList<Transaction> dummyTrAsList = new ArrayList<>(dummyTransactions);
        for (int i =skip;i<Math.min(dummyTrAsList.size(),skip+count);i++){
            ans.add(dummyTrAsList.get(i).copy());
        }
        return ans;
    }

    @Override
    public boolean addAccount(Account acc) {
        dummyAccounts.add(acc);
        return true;
    }

    @Override
    public ArrayList<Account> fetchAccounts(){
        ArrayList<Account> ans= new ArrayList<Account>();
        for (Account acc:dummyAccounts){
            ans.add(acc.copy());
        }
        return ans;
    }
}