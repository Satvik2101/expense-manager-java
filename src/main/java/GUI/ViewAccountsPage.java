package GUI;

import Account.Account;
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

public class ViewAccountsPage extends JFrame {
    final AccountsManager mgr;
    private JPanel panel1;
    private JButton backButton;
    private JTable accounts;


    void addAccounts(){

        DefaultTableModel model = (DefaultTableModel)accounts.getModel();
        for (Account ac:mgr.getAccounts()){
            System.out.println(ac.name  );

          model.addRow(new Object[]{ac.id,ac.name,ac.amount});
        }
        System.out.println(model.getDataVector());
    }

    public ViewAccountsPage(AccountsManager mgr) {
        super();
        this.mgr = mgr;
        setTitle("View Accounts");
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,400);
        addAccounts();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                HomePage homePage = new HomePage(mgr);
                homePage.setVisible(true);
                dispose();
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        String[] columns = {"Id","Name","Amount"};

        TableModel model = new DefaultTableModel(columns,0);
        accounts = new JTable(model);
    }
}
