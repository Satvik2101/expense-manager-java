package GUI;

import Account.AccountsManager;
import Transaction.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class ViewTransactionsPage extends JFrame {
    final AccountsManager mgr;
    private JPanel panel1;
    private JButton fetchMoreButton;
    private JButton backButton;
    private JTable transactions;

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
            int h = model.getRowCount();
//            model.setRowCount(h+1);
            model.addRow(new Object[]{senderName,receiverName,tr.getName(),tr.getAmount(),tr.getTimestamp()});
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
        setSize(300,400);
        setTable();
        fetchMoreButton.addActionListener(e -> {
//                System.out.println("HERE");
            addToTable();
        });
        backButton.addActionListener(e -> {
            setVisible(false);
            HomePage homePage = new HomePage(mgr);
            homePage.setVisible(true);
            dispose();
        });
    }


    private void createUIComponents() {
        String[] columns = {"Sender","Receiver","Name","Amount","Timestamp"};

        TableModel model = new DefaultTableModel(columns,0);
        transactions = new JTable(model);
    }
}
