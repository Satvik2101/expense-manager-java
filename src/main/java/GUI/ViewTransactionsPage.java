package GUI;

import Account.AccountsManager;
import GUI.Helpers.NavigableFrame;
import GUI.Helpers.Navigator;
import Transaction.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class ViewTransactionsPage extends NavigableFrame {
    final AccountsManager mgr;
    private JPanel panel1;
//    private JButton fetchMoreButton;
    private JButton backButton;
    private JTable transactions;
    int page=-1;
    int count=-1;
    void setTable(){
        addFromCollection(mgr.transactions);
    }

    void addToTable(){
        ArrayList<Transaction> newTransactions = mgr.fetchMoreTransactions();
        if (newTransactions!=null)
        addFromCollection(newTransactions);
    }

    void addFromCollection(Collection<Transaction> trs){
        DefaultTableModel model = (DefaultTableModel)transactions.getModel();
        for (Transaction tr:trs){
            String senderName = mgr.getNameOfUUID(tr.getSenderId());
            String receiverName = mgr.getNameOfUUID(tr.getReceiverId());
            System.out.println("Adding "+tr.getName());

            model.addRow(new Object[]{senderName,receiverName,tr.getName(),tr.getAmount(),tr.getCategory(),
                    tr.getTimestamp()});
//            model.setro
//            transactions.notify();

        }
        System.out.println(model.getDataVector());
    }

    public ViewTransactionsPage(AccountsManager mgr) {
        super("View Transactions",800,400);
        this.mgr = mgr;
        setTable();
        setContentPane(panel1);
        backButton.addActionListener(
                e -> {
                    Navigator.instance().pop();
                }
        );
    }

    void loadMore(UUID id){
        ArrayList<Transaction> lst = mgr.getTransactionsOf(id,page,count);
        if (lst==null||lst.isEmpty()){
            return;
        }
            page++;
            addFromCollection(lst);

    }

    void loadMore(String category){
        ArrayList<Transaction> lst = mgr.getTransactionsOfCategory(category);
        if (lst==null||lst.isEmpty()){
            return;
        }
        page++;
        addFromCollection(lst);
    }
    public ViewTransactionsPage(AccountsManager mgr, UUID id) {
        super("View Transactions of "+mgr.getNameOfUUID(id),800,400);
        this.mgr = mgr;
        setContentPane(panel1);
        page = 1;
        count = AccountsManager.countPerPage;
        loadMore(id);
        backButton.addActionListener(e -> {
            Navigator.instance().pop();
        });
    }

    public ViewTransactionsPage(AccountsManager mgr, String category) {
        super("View Transactions of "+category,800,400);

        this.mgr = mgr;
        setContentPane(panel1);


        page = 1;
        count = AccountsManager.countPerPage;
        loadMore(category);
        backButton.addActionListener(e -> {
            Navigator.instance().pop();
        });
    }


    private void createUIComponents() {
        String[] columns = {"Sender","Receiver","Name","Amount","Category","Timestamp"};

        TableModel model = new DefaultTableModel(columns,0);
        transactions = new JTable(model);
    }
}
