package GUI;

import Account.AccountsManager;
import Account.Account;
import Account.MySQLAccountsManager;
import GUI.Helpers.DoubleFilter;
import GUI.Helpers.NavigableFrame;
import GUI.Helpers.Navigator;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class AddTransaction extends NavigableFrame {
    private JPanel panel1;
    private JComboBox<Account> senderComboBox;
    private JComboBox<Account> receiverComboBox;
    private JRadioButton expenseRadioButton;
    private JRadioButton transferRadioButton;
    private JRadioButton incomeRadioButton;
    private JSpinner dateSpinner;
    private JTextField amountField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JButton addButton;
    private JButton backButton;
    private JComboBox<String> categoryComboBox;
    private ButtonGroup transactionTypeGroup;
    final AccountsManager mgr;
    public AddTransaction(AccountsManager mgr1) {
        super("Add Transaction", 400, 300);

        this.mgr = mgr1;

        setContentPane(panel1);

        expenseRadioButton.addActionListener(e -> {
            if (expenseRadioButton.isSelected()){
                receiverComboBox.setEnabled(false);
                senderComboBox.setEnabled(true);
                resetIfNull(senderComboBox);
                receiverComboBox.setSelectedItem(AccountsManager.nullAccount);
                categoryComboBox.setEnabled(true);
                categoryComboBox.setSelectedIndex(0);
            }
        });
        incomeRadioButton.addActionListener(e -> {
            if (incomeRadioButton.isSelected()){
                senderComboBox.setEnabled(false);
                receiverComboBox.setEnabled(true);
                resetIfNull(receiverComboBox);
                senderComboBox.setSelectedItem(AccountsManager.nullAccount);
                categoryComboBox.setEnabled(true);
                categoryComboBox.setSelectedIndex(0);
            }
        });
        transferRadioButton.addActionListener(e -> {
            if (transferRadioButton.isSelected()){
                senderComboBox.setEnabled(true);
                receiverComboBox.setEnabled(true);
                resetIfNull(senderComboBox);
                resetIfNull(receiverComboBox);
                categoryComboBox.setSelectedItem("transfer");
                categoryComboBox.setEnabled(false);
            }
        });
        expenseRadioButton.setSelected(true);


        backButton.addActionListener(
                e -> {
                    Navigator.instance().pop();
                }
        );
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                String amountStr = amountField.getText();
                Date date = (Date) dateSpinner.getValue();
                Timestamp timestamp= new Timestamp(date.getTime());
                UUID senderId = ((Account)senderComboBox.getSelectedItem()).id;
                UUID receiverId = ((Account)receiverComboBox.getSelectedItem()).id;
                String category = (String) categoryComboBox.getSelectedItem();
                double amount;
                try {
                     amount = Double.parseDouble(amountStr);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(addButton,"Invalid amount value");
                    return;
                }
                if (!mgr.recordTransaction(senderId,receiverId,amount,name,description,timestamp,category)){
                    JOptionPane.showMessageDialog(addButton,"Transaction not recorded");
                }else{
                    JOptionPane.showMessageDialog(addButton,"Transaction recorded successfully!");
                    categoryComboBox.addItem(category);

                }
            }
        });
    }

    void resetIfNull(JComboBox<Account> comboBox){
        if (comboBox.getSelectedItem().equals(AccountsManager.nullAccount)) {
            comboBox.setSelectedIndex(0);
        }
    }

    public static void main(String[] args) {
        String jdbcUrl ="jdbc:mysql://root:b6famsatvik55@localhost:3306/expense_manager";
        AccountsManager mgr = new MySQLAccountsManager(jdbcUrl);
        AddTransaction frame = new AddTransaction(mgr);
        frame.setVisible(true);

    }

    private void createUIComponents() {
        ArrayList<Account> accounts = mgr.getAccounts();
        senderComboBox = new JComboBox<>();
        receiverComboBox = new JComboBox<>();

        SpinnerDateModel spinMod=new SpinnerDateModel();
       dateSpinner=new JSpinner(spinMod);
        for (Account ac :accounts){
            senderComboBox.addItem(ac);
            receiverComboBox.addItem(ac);
        }
        amountField = new JTextField(10);
        PlainDocument doc = (PlainDocument) amountField.getDocument();
        doc.setDocumentFilter(new DoubleFilter());

        categoryComboBox = new JComboBox<>();
        for (String cat:mgr.getCategories()){
            categoryComboBox.addItem(cat);
        }

    }
}
