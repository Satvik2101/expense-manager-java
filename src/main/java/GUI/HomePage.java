package GUI;

import Account.AccountsManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    final AccountsManager manager;
    private JPanel panel1;
    private JButton addAccountButton;
    private JButton addTransactionButton;
    private JButton viewTransactionsButton;
    private JButton logoutButton;
    private JButton viewAccountsButton;

    HomePage(AccountsManager mgr) {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,400);
        manager = mgr;
        setContentPane(panel1);
        logoutButton.addActionListener(new LogoutListener());
        viewTransactionsButton.addActionListener(new ViewTransactionsListener());
        viewAccountsButton.addActionListener(new ViewAccountsListener());
        addAccountButton.addActionListener(new AddAccountListener());
        addTransactionButton.addActionListener(new AddTransactionListener());
    }

    //TODO: Add MVC Controller
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
            setVisible(false);
            viewTransactionsPage.setVisible(true);
            dispose();
        }
    }

    class ViewAccountsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            setVisible(false);
            ViewAccountsPage viewAccountsPage = new ViewAccountsPage(manager);
            viewAccountsPage.setVisible(true);
            dispose();
        }
    }
    class AddAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddAccount addAccount = new AddAccount(manager);
            setVisible(false);
            addAccount.setVisible(true);
            dispose();
        }
    }

    class AddTransactionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddTransaction addTransaction = new AddTransaction(manager);
            setVisible(false);
            addTransaction.setVisible(true);
            dispose();
        }
    }


}
