package DatabaseHandlers;

import Account.Account;
import Transaction.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

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
        ArrayList<Account> ans = new ArrayList<>();
        String sql = "Select * from accounts";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                String name = rs.getString("name");
                String rawID = rs.getString("id");
                double amount = rs.getDouble("amount");
                String type = rs.getString("type");

                UUID id = UUID.fromString(rawID);
                Account acc = Account.createAccount(id,name,amount,type);
                ans.add(acc);
            }
            return ans;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

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
