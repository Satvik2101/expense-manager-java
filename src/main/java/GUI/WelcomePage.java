package GUI;

import Account.AccountsManager;
import Account.MySQLAccountsManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class WelcomePage extends JFrame{
    private JPanel panel1;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private AccountsManager manager;


    boolean login(String username,String pwd){
        if (manager!=null)return false;
        String jdbcUrl ="jdbc:mysql://"+username+":"+pwd+"@localhost:3306/expense_manager";
        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {
            manager = new MySQLAccountsManager(jdbcUrl);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public WelcomePage() {
        setTitle("Welcome");
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,400);
//        setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                String pwd = String.valueOf(password);
                if (!login(username,pwd)){
                    JOptionPane.showMessageDialog(loginButton,"Unsuccessful Login");

                }else{
//                    JOptionPane.show/*/MessageDialog(loginButton,"Successful Login");
                    setVisible(false);
                    HomePage homePage = new HomePage(manager);
                    homePage.setVisible(true);
                    dispose();

                }
            }
        });
    }

    public static void main(String[] args) {
        WelcomePage page = new WelcomePage();
        page.setVisible(true);
    }
}
