package Transaction;

import Account.Account;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class Transaction implements Comparable <Transaction>{
    final UUID id;
    UUID senderId;
    UUID receiverId;
    public UUID getId(){
        return id;
    }

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
            UUID id,
            UUID senderId,
            UUID receiverId,
            String name,
            Timestamp timestamp,
            String description, double amount) {
        this.amount = amount;
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.name = name;
        this.timestamp = timestamp;
        this.description = description;
    }
    public Transaction(
            UUID senderId,
            UUID receiverId,
            String name,
            Timestamp timestamp,
            String description, double amount) {
        this(UUID.randomUUID(),senderId,receiverId,name,timestamp,description,amount);

    }

    public Transaction(String rawId, String rawSenderId, String rawReceiverId, String name, Timestamp time,
                       String description,
                       double amount){

        this(UUID.fromString(rawId),
             UUID.fromString(rawSenderId),
             UUID.fromString(rawReceiverId),name,time,description,amount);

    }
    public boolean isOfAccount(Account acc){
        return (senderId == acc.id || receiverId == acc.id);
    }

    public double getAmount() {
        return amount;
    }


    public Transaction copy(){
//        Transaction clone = (Transaction) Object.clone();
        return new Transaction(senderId,receiverId,name,timestamp,description,amount);
    }

    @Override
    public int compareTo(Transaction o) {
        int timeCompare= o.timestamp.compareTo(timestamp);
        if (timeCompare!=0)return timeCompare;
        return id.compareTo(o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
