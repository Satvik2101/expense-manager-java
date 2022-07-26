package Transaction;

import Account.Account;

import java.sql.Timestamp;

public class Transaction {
    Account sender;
    Account receiver;
    String name;
    Timestamp timestamp;
}
