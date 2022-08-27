package GUI;

import Account.AccountsManager;
import GUI.Helpers.NavigableFrame;
import GUI.Helpers.Navigator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends NavigableFrame {
    final AccountsManager manager;
    private JPanel panel1;
    private JButton addAccountButton;
    private JButton addTransactionButton;
    private JButton viewTransactionsButton;
    private JButton logoutButton;
    private JButton viewAccountsButton;
    private JButton viewCategoriesButton;

    HomePage(AccountsManager mgr) {
        super("Home",300,200);

        manager = mgr;
        setContentPane(panel1);
        logoutButton.addActionListener(new LogoutListener());
        viewTransactionsButton.addActionListener(new ViewTransactionsListener());
        viewAccountsButton.addActionListener(new ViewAccountsListener());
        addAccountButton.addActionListener(new AddAccountListener());
        addTransactionButton.addActionListener(new AddTransactionListener());
        viewCategoriesButton.addActionListener(new ViewCategoriesListener());
    }

    class LogoutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            setVisible(false);
            WelcomePage welcomePage = new WelcomePage();
            welcomePage.setVisible(true);
            dispose();
        }
    }

    class ViewTransactionsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ViewTransactionsPage viewTransactionsPage = new ViewTransactionsPage(manager);
            Navigator.instance().push(viewTransactionsPage);
        }
    }

    class ViewAccountsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            ViewAccountsPage viewAccountsPage = new ViewAccountsPage(manager);
            Navigator.instance().push(viewAccountsPage);
        }
    }
    class AddAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddAccount addAccount = new AddAccount(manager);
           Navigator.instance().push(addAccount);
        }
    }

    class AddTransactionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddTransaction addTransaction = new AddTransaction(manager);
           Navigator.instance().push(addTransaction);
        }
    }
    class ViewCategoriesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ViewCategories viewCategories = new ViewCategories(manager);
            Navigator.instance().push(viewCategories);
        }
    }


}
