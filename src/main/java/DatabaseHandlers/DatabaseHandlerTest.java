package DatabaseHandlers;

import Account.Account;
import Account.AccountsManager;
import Account.CashAccount;
import Transaction.Transaction;
import javafx.util.Pair;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
    public boolean recordTransaction(Transaction tr,double x,double y) {
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
                                         500,
                                         "stationary"
        );
        //create more dummy transactions like this
        dummyTransactions.add(tr);
        tr = new Transaction(cash.id, nullId,
                             "Cafe Crew Brew",
                             new Timestamp(122, 6, 28, 15, 30, 0, 0),
                             null,
                             390,
                             "food"
        );
        dummyTransactions.add(tr);
        tr = new Transaction(giftMoney.id, cash.id,
                             "",
                             new Timestamp(122, 6, 28, 14, 02, 0, 0),
                             null,
                             1000,
                             "category"
        );
        dummyTransactions.add(tr);
        tr = new Transaction( nullId,cash.id,
                             "",
                             new Timestamp(122, 6, 24, 15, 40, 0, 0),
                             null,
                             750,
                              "category"
        );
        dummyTransactions.add(tr);
        tr = new Transaction(cash.id, nullId,
                             "CafeCrewBrew",
                             new Timestamp(122, 6, 24, 16, 30, 0, 0),
                             null,
                             250,
                             "food"
        );
        dummyTransactions.add(tr);
        tr = new Transaction(cash.id, nullId,
                             "ButterChicken+Naan",
                             new Timestamp(122, 6, 21, 15, 30, 0, 0),
                             null,
                             450,
                             "food"
        );
        dummyTransactions.add(tr);
        tr = new Transaction( nullId,cash.id,
                             "For chicken",
                             new Timestamp(122, 6, 21, 15, 30, 0, 0),
                             null,
                             1000,
                              "category"
        );
        dummyTransactions.add(tr);
        tr = new Transaction(metroCard.id, nullId,
                             "Twice NSP",
                             new Timestamp(122, 6, 20, 12, 30, 0, 0),
                             null,
                             20,
                             "category"
        );
        dummyTransactions.add(tr);
        tr = new Transaction(cash.id, metroCard.id,
                             "MetroCard Recharge at NSP",
                             new Timestamp(122, 6, 20, 12, 30, 0, 0),
                             null,
                             300,
                             "category"
        );
        dummyTransactions.add(tr);
        tr = new Transaction(metroCard.id, nullId,
                             "Rithala Twice",
                             new Timestamp(122, 6, 19, 12, 30, 0, 0),
                             null,
                             40,
                             "category"
        );
        dummyTransactions.add(tr);

    }


    @Override
    public boolean updateAccountValue(UUID id, double newAmount) {
        int idx =-1;
        for (int i=0;i<dummyAccounts.size();i++){
            if (dummyAccounts.get(i).id==id){
                idx = i;
                break;
            }
        }
        if (idx==-1)return false;
        dummyAccounts.get(idx).setValue(newAmount);
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

    @Override
    public ArrayList<Transaction> getTransactionsOf(UUID id, int page, int count) {
        return null;
    }

    @Override
    public ArrayList<String> getCategories() {
        return new ArrayList<String>(
                List.of("Food","Rent","Stationary","category")
        );
    }

    @Override
    public ArrayList<Pair<String, Double>> getCategoriesWithAmounts() {
        double foodAmt = 0;
        double catAmt =0;
        double statAmt = 0;
        double rentAmt = 0;

        for (Transaction tr:dummyTransactions){
            switch (tr.getCategory()){
                case "Food":
                    foodAmt += tr.getAmount();
                            break;
                case "Rent":
                    rentAmt+= tr.getAmount();
                    break;
                case "Stationary":
                    statAmt+=tr.getAmount();
                    break;
                case "category":
                    catAmt+= tr.getAmount();
                    break;
                default:
                    break;
            }
        }
        ArrayList<Pair<String,Double>> ans = new ArrayList<>();
        ans.add(new Pair("Food",foodAmt));
        ans.add(new Pair("Rent",rentAmt));
        ans.add(new Pair("Stationary",statAmt));
        ans.add(new Pair("category",catAmt));

        return ans;
    }
}
