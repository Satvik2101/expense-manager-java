package Account;

import java.util.UUID;

public abstract class Account {
    public final UUID id;
    public String name;
    public double amount;

    //    static int x =5;
    public Account(String name, double amount) {
//        nullAccount = new NullAccount();
        this.name = name;
        this.id = UUID.randomUUID();

        this.amount = amount;
    }

    public Account(String name) {

        this(name, 0);
    }

    abstract double withdraw(double amountToMove);

    abstract double deposit(double amountToAdd);

//    static void transfer(Account receiver, Account sender, double amtToTransfer){
//        receiver.addToAccount(amtToTransfer);
//        sender.moveFromAccount(amtToTransfer);
//    }

//    static NullAccount nullAccount = new NullAccount();
}
