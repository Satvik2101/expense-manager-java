package GUI;

import Account.Account;
import Account.AccountsManager;
import Account.NullAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class ViewAccountsPage extends JFrame {
    final AccountsManager mgr;
    private JPanel panel1;
    private JButton backButton;
    private JTable accounts;


    void addAccounts(){

        DefaultTableModel model = (DefaultTableModel)accounts.getModel();
        for (Account ac:mgr.getAccounts()){
            System.out.println(ac.name  );
          if (!(ac instanceof NullAccount)) {
              model.addRow(new Object[]{ac.id, ac.name, ac.amount});
          }
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

        backButton.addActionListener(e -> {
            setVisible(false);
            HomePage homePage = new HomePage(mgr);
            homePage.setVisible(true);
            dispose();
        });
    }


    private void createUIComponents() {
        String[] columns = {"Id","Name","Amount"};

        TableModel model = new DefaultTableModel(columns,0);
        accounts = new JTable(model);
        accounts.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {     // to detect doble click events
                    JTable target = (JTable)me.getSource();
                    int row = target.rowAtPoint(me.getPoint());
                    if (row==-1) System.out.println("Negative");
                    System.out.println(target.getValueAt(row,0));
                    UUID id =(UUID) target.getValueAt(row,0);
                    ViewTransactionsPage viewTransactionsPage = new ViewTransactionsPage(mgr,id);
                    setVisible(false);
                    viewTransactionsPage.setVisible(true);
                    dispose();
                }
            }
        });
    }
}
