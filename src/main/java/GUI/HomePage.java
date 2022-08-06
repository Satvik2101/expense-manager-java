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
}
