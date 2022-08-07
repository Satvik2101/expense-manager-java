package GUI;

import Account.AccountsManager;

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

    static class DoubleFilter extends DocumentFilter {



        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                                 AttributeSet attr) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (test(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            } else {
                // do nothing
            }
        }

        private boolean test(String text) {
            try {
                Double.parseDouble(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
                            AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (test(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                // do nothing
            }

        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

          if (sb.length()==0|| test(sb.toString())) {
                super.remove(fb, offset, length);
            } else {
                // do nothing
            }

        }
    }
}
