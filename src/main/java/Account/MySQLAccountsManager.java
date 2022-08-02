package Account;

import DatabaseHandlers.MySQLDatabaseHandler;

import java.sql.Connection;

public class MySQLAccountsManager extends AccountsManager{
    final Connection conn;
    public MySQLAccountsManager(Connection conn) {
        super(new MySQLDatabaseHandler(conn));
        this.conn = conn;
    }
}
