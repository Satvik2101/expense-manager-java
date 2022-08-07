package GUI;

import Account.AccountsManager;
import GUI.Helpers.DoubleFilter;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAccount extends JFrame {
    final AccountsManager mgr;
    private JTextField nameField;
    private JTextField amountField;
    private JButton backButton;
    private JButton addButton;
    private JComboBox<String> typeComboBox;
    private JPanel panel1;

    public AddAccount(AccountsManager mgr) {
        setContentPane(panel1);
        this.mgr = mgr;
        backButton.addActionListener(
                e -> {
                    setVisible(false);
                    HomePage homePage = new HomePage(mgr);
                    homePage.setVisible(true);
                    dispose();
                }
        );

        addButton.addActionListener(e -> {
            String name = nameField.getText();
//
            String amountStr = amountField.getText();
            String type = (String) typeComboBox.getSelectedItem();
            if (name == null || amountStr == null || name.isEmpty() || amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(addButton, "Please enter values");
                return;
            }

//                assert amountStr != null;
            double amount;
            try {
                amount  =Double.parseDouble(amountStr);
            } catch (NumberFormatException ex) {
                return;
            }
            mgr.createNewAccount(name,amount,type);
        });
    }

    private void createUIComponents() {
        amountField = new JTextField(10);
        PlainDocument doc = (PlainDocument) amountField.getDocument();
        doc.setDocumentFilter(new DoubleFilter());
    }


}
