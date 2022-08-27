package GUI;

import Account.Account;
import Account.AccountsManager;
import Account.NullAccount;
import GUI.Helpers.NavigableFrame;
import GUI.Helpers.Navigator;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class ViewAccountsPage extends NavigableFrame {
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
        super("View Accounts",300,400);
        this.mgr = mgr;
        setContentPane(panel1);
        addAccounts();

        backButton.addActionListener(
                e -> {
                    System.out.println("popping view acc");
                    Navigator.instance().pop();
                }
        );
    }


    private void createUIComponents() {
        String[] columns = {"Id","Name","Amount"};

        TableModel model = new DefaultTableModel(columns,0){
            @Override

            public boolean isCellEditable(int row, int column)
            {
                return column == 2;
            }

            @Override
            public Class<?> getColumnClass(int idx){
                return idx==2?Double.class:super.getColumnClass(idx);
            }
        };
//
//
        accounts = new JTable(model);
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int col = e.getColumn();
            if (row<0 || col<0)return;
            System.out.println(row+" "+col);
            UUID id = (UUID) model.getValueAt(row,0);
            double newAmount = (double) model.getValueAt(row,col);

            if (mgr.updateAccountValue(id,newAmount)){
                JOptionPane.showMessageDialog(this,"Updated Amount");

            }else{
                JOptionPane.showMessageDialog(this,"");
            }
        });


        accounts.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {     // to detect doble click events
                    JTable target = (JTable)me.getSource();
                    int row = target.rowAtPoint(me.getPoint());
                    if (row==-1) {
                        System.out.println("Negative");
                        return;
                    }
                    System.out.println(target.getValueAt(row,0));
                    UUID id =(UUID) target.getValueAt(row,0);
                    ViewTransactionsPage viewTransactionsPage = new ViewTransactionsPage(mgr,id);
                   Navigator.instance().push(viewTransactionsPage);
                }
            }
        });
    }
}
