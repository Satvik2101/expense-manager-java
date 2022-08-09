package GUI;

import Account.Account;
import Account.AccountsManager;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

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

        categoriesTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {     // to detect doble click events
                    JTable target = (JTable)me.getSource();
                    int row = target.rowAtPoint(me.getPoint());
                    if (row==-1) {
                        System.out.println("Negative");
                        return;
                    }
                    System.out.println(target.getValueAt(row,0));
                    String category =(String) target.getValueAt(row, 0);
                    ViewTransactionsPage viewTransactionsPage = new ViewTransactionsPage(mgr,category);
                    setVisible(false);
                    viewTransactionsPage.setVisible(true);
                    dispose();
                }
            }
        });
    }
}
