package GUI;

import Account.Account;
import Account.AccountsManager;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ViewCategories extends JFrame{
    private JTable categoriesTable;
    private JPanel panel1;
    private JButton backButton;
    final AccountsManager mgr;

    void addCategories(){

        DefaultTableModel model = (DefaultTableModel)categoriesTable.getModel();
        for (Pair<String,Double> pr:mgr.getCategoriesWithAmounts()){


                model.addRow(new Object[]{pr.getKey(),pr.getValue()});

        }
        System.out.println(model.getDataVector());
    }
    public ViewCategories(AccountsManager mgr){
        super();
        this.mgr = mgr;
        setTitle("View Categories");
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,400);

        addCategories();
        backButton.addActionListener(e -> {
            setVisible(false);
            HomePage homePage = new HomePage(mgr);
            homePage.setVisible(true);
            dispose();
        });
    }
    private void createUIComponents() {
        String[] columns = {"Name","Amount"};

        TableModel model = new DefaultTableModel(columns, 0);
        categoriesTable = new JTable(model);
    }
}
