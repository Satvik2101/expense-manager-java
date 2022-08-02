package DatabaseHandlers;

import Account.Account;
import Transaction.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLDatabaseHandler extends DatabaseHandler {
    final Connection conn;

    public MySQLDatabaseHandler(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean recordTransaction(Transaction transaction) {
        return false;
    }

    @Override
    public boolean updateAccountValue(Account account) {
        return false;
    }

    @Override
    public ArrayList<Transaction> fetchTransactions(int page, int count) {
        return null;
    }

    @Override
    public ArrayList<Account> fetchAccounts() {
        return null;
    }

    @Override
    public boolean addAccount(Account acc) {
        String sql ="insert into accounts (id,name,amount) values (?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,acc.id.toString());
            stmt.setString(2,acc.name);
            stmt.setDouble(3,acc.amount);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
