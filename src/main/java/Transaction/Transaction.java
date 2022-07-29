package Transaction;

import Account.Account;

import java.sql.Timestamp;
import java.util.UUID;

public class Transaction {
    final UUID id;
    UUID senderId;
    UUID receiverId;

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(UUID receiverId) {
        this.receiverId = receiverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String name;
    Timestamp timestamp;
    String description;
    final double amount;
    public Transaction(
            UUID senderId,
            UUID receiverId,
            String name,
            Timestamp timestamp,
            String description, double amount) {
        this.amount = amount;
        this.id = UUID.randomUUID();
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.name = name;
        this.timestamp = timestamp;
        this.description = description;
    }
    public boolean isOfAccount(Account acc){
        return (senderId == acc.id || receiverId == acc.id);
    }
}
