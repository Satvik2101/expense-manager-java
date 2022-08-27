package DatabaseHandlers;

import Account.Account;
import Transaction.Transaction;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class MySQLDatabaseHandler extends DatabaseHandler {
    final String connUrl;

    public MySQLDatabaseHandler(String url) {
        this.connUrl = url;
    }

    @Override
    public boolean recordTransaction(Transaction tr,double newSenderAmount, double newReceiverAmount) {
        String insertTransactionQuery = "insert into transactions (id,sender_id,receiver_id,name,description,time," +
                "amount,category) values (?,?,?," +
                "?,?,?,?,?)";
        String insertCategoryQuery = "insert into categories (category) values (?) on duplicate key update " +
                "category=category;";
        try  (Connection conn = DriverManager.getConnection(connUrl))
        {
            //nested so we can use rollback in the catch clause of this
            //if it wasn't nested, the catch block of the outer try
            //would not have access to the conn object as it would be closed
            //before we hit the catch
            try(PreparedStatement insertTrStatement = conn.prepareStatement(insertTransactionQuery);
                PreparedStatement insertCategoryStatement = conn.prepareStatement(insertCategoryQuery);
            ){
                conn.setAutoCommit(false);
                insertTrStatement.setString(1,tr.getId().toString());
                insertTrStatement.setString(2,tr.getSenderId().toString());
                insertTrStatement.setString(3,tr.getReceiverId().toString());
                insertTrStatement.setString(4,tr.getName());
                insertTrStatement.setString(5,tr.getDescription());
                insertTrStatement.setTimestamp(6,tr.getTimestamp());
                insertTrStatement.setDouble(7,tr.getAmount());
                insertTrStatement.setString(8,tr.getCategory());
                insertCategoryStatement.setString(1,tr.getCategory());
//                insertCategoryStatement.get
                System.out.println(tr.getCategory());
                insertCategoryStatement.executeUpdate();
                insertTrStatement.executeUpdate();
                setAccountValue(tr.getSenderId(),newSenderAmount,conn);
                setAccountValue(tr.getReceiverId(),newReceiverAmount,conn);
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
            setAccountValue(id,newAmount,conn);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
//        return false;
    }
    void setAccountValue(UUID uuid, double value ,Connection conn) throws SQLException {
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
//        ArrayList<Transaction> ans = new ArrayList<>();
        int skip = (page-1)*count;
        String sql = "Select * from transactions order by time desc limit ? offset ?";
        try(Connection conn = DriverManager.getConnection(connUrl);
            PreparedStatement stmt = conn.prepareStatement(sql);

                ) {
            stmt.setInt(1,count);
            stmt.setInt(2,skip);
            try(ResultSet rs = stmt.executeQuery()) {
                return transactionFromResultSet(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    private ArrayList<Transaction> transactionFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<Transaction> ans = new ArrayList<>();
        if (rs == null) return ans;
        while (rs.next()) {
            String rawId = rs.getString("id");
            String rawSenderId = rs.getString("sender_id");
            String rawReceiverId = rs.getString("receiver_id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            Timestamp time = rs.getTimestamp("time");
            String category = rs.getString("category");
            double amount = rs.getDouble("amount");
            Transaction tr = new Transaction(rawId,
                                             rawSenderId,
                                             rawReceiverId,
                                             name,
                                             time,
                                             description,
                                             amount,
                                             category
            );
            ans.add(tr);
        }
        return ans;
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
    public ArrayList<Transaction> getTransactionsOf(UUID id, int page, int count) {
        int skip = (page-1)*count;
        String sql = "Select * from transactions where sender_id=? or receiver_id=? order by time desc limit ? offset" +
                " ?";
        try(Connection conn = DriverManager.getConnection(connUrl);
            PreparedStatement stmt = conn.prepareStatement(sql);

        ) {
            stmt.setString(1,id.toString());
            stmt.setString(2,id.toString());
            stmt.setInt(3,count);
            stmt.setInt(4,skip);
            try(ResultSet rs = stmt.executeQuery()) {
                return transactionFromResultSet(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    @Override
    public ArrayList<String> getCategories() {
        String sql = "select * from categories";
        ArrayList<String> ans = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connUrl);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ){
            while (rs.next()){
                String cat = rs.getString("category");
                ans.add(cat);

            }
            return ans;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ans;
    }

    @Override
    public ArrayList<Pair<String, Double>> getCategoriesWithAmounts() {
        ArrayList<Pair<String, Double>> ans = new ArrayList<>();
        String sql = "select category,sum(amount) as amount from transactions group by category;";
        try(
                Connection conn = DriverManager.getConnection(connUrl);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while   (rs.next()){
                String cat = rs.getString("category");
                double amt = rs.getDouble("amount");
                ans.add(new Pair<>(cat,amt));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ans;
    }

    @Override
    public ArrayList<Transaction> getTransactionsOfCategory(String cat) {
        String sql = "select * from transactions where category = ? order by time desc";
        try (
                Connection conn = DriverManager.getConnection(connUrl);
                PreparedStatement stmt = conn.prepareStatement(sql)
                ) {
            stmt.setString(1,cat);
            try (ResultSet rs = stmt.executeQuery()){
                return transactionFromResultSet(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
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
