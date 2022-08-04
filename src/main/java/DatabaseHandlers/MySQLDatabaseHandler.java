package DatabaseHandlers;

import Account.Account;
import Transaction.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class MySQLDatabaseHandler extends DatabaseHandler {
    final String connUrl;

    public MySQLDatabaseHandler(String url) {
        this.connUrl = url;
    }

    @Override
    public boolean recordTransaction(Transaction tr) {
        String sql = "insert into transactions (id,sender_id,receiver_id,name,description,time,amount) values (?,?,?," +
                "?,?,?,?)";

        try  (Connection conn = DriverManager.getConnection(connUrl))
        {
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                conn.setAutoCommit(false);
                stmt.setString(1,tr.getId().toString());
                stmt.setString(2,tr.getSenderId().toString());
                stmt.setString(3,tr.getReceiverId().toString());
                stmt.setString(4,tr.getName());
                stmt.setString(5,tr.getDescription());
                stmt.setTimestamp(6,tr.getTimestamp());
                stmt.setDouble(7,tr.getAmount());
                stmt.executeUpdate();
                updateAccountValue(tr.getSenderId(),tr.getAmount(),conn,UpdateMode.DECR);
                updateAccountValue(tr.getReceiverId(),tr.getAmount(),conn,UpdateMode.INCR);
                conn.commit();
            } catch (SQLException throwables) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                throwables.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean updateAccountValue(UUID id, double newAmount ) {

        try(
                Connection conn = DriverManager.getConnection(connUrl)
//                PreparedStatement stmt = conn.prepareStatement(sql)
                ) {
            updateAccountValue(id,newAmount,conn);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
//        return false;
    }
    void updateAccountValue(UUID uuid, double value ,Connection conn) throws SQLException {
        updateAccountValue(uuid,value,conn,UpdateMode.SET);
    }

    void updateAccountValue(UUID uuid, double value ,Connection conn,UpdateMode mode) throws SQLException {
        String id = uuid.toString();

        String sql = getAccountUpdateStatement(mode);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1,value);
        stmt.setString(2,id);
        stmt.executeUpdate();
        stmt.close();
    }

    String getAccountUpdateStatement(UpdateMode mode){
        switch (mode){
            case SET:
                return "update accounts set amount=? where id=?";
            case INCR:
                return "update accounts set amount=amount+? where id=?";
            case DECR:
                return "update accounts set amount=amount-? where id=?";
        }
        return null;
    }

    @Override
    public ArrayList<Transaction> fetchTransactions(int page, int count) {
        return null;
    }

    @Override
    public ArrayList<Account> fetchAccounts() {
        ArrayList<Account> ans = new ArrayList<>();
        String sql = "Select * from accounts";

        //try-with-resources
        try(
                Connection conn = DriverManager.getConnection(connUrl);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
                )
        {

            if (rs==null) return ans;
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
//
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean addAccount(Account acc) {
        String sql ="insert into accounts (id,name,amount) values (?,?,?)";

        try
                (
                        Connection conn = DriverManager.getConnection(connUrl);
                        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,acc.id.toString());
            stmt.setString(2,acc.name);
            stmt.setDouble(3,acc.amount);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
