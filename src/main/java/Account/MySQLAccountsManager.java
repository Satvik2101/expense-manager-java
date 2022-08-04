package Account;

import DatabaseHandlers.MySQLDatabaseHandler;

import java.sql.Connection;

public class MySQLAccountsManager extends AccountsManager{
    final String connectionUrl;
    public MySQLAccountsManager(String url) {
        super(new MySQLDatabaseHandler(url));
        this.connectionUrl = url;
    }
}
