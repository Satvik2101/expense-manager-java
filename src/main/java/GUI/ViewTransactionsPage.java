package GUI;

import Account.AccountsManager;
import Transaction.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class ViewTransactionsPage extends JFrame {
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
            System.out.println(tr.getName());

            model.addRow(new Object[]{senderName,receiverName,tr.getName(),tr.getAmount(),tr.getCategory(),
                    tr.getTimestamp()});
//            model.setro
//            transactions.notify();
        }
        System.out.println(model.getDataVector());
    }

    public ViewTransactionsPage(AccountsManager mgr) {
        super();
        this.mgr = mgr;
        setTitle("View Transactions");
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,400);
        setTable();
        backButton.addActionListener(e -> {
            setVisible(false);
            HomePage homePage = new HomePage(mgr);
            homePage.setVisible(true);
            dispose();
        });
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
        super();
        this.mgr = mgr;
        setTitle("View Transactions of "+mgr.getNameOfUUID(id));
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,400);
        page = 1;
        count = AccountsManager.countPerPage;
        loadMore(id);
        backButton.addActionListener(e -> {
            setVisible(false);
            ViewAccountsPage viewAccountsPage = new ViewAccountsPage(mgr);
            viewAccountsPage.setVisible(true);
            dispose();
        });
    }

    public ViewTransactionsPage(AccountsManager mgr, String category) {
        super();
        this.mgr = mgr;
        setTitle("View Transactions of "+category);
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,400);
        page = 1;
        count = AccountsManager.countPerPage;
        loadMore(category);
        backButton.addActionListener(e -> {
            setVisible(false);
            ViewCategories viewCategories = new ViewCategories(mgr);
            viewCategories.setVisible(true);
            dispose();
        });
    }


    private void createUIComponents() {
        String[] columns = {"Sender","Receiver","Name","Amount","Category","Timestamp"};

        TableModel model = new DefaultTableModel(columns,0);
        transactions = new JTable(model);
    }
}
