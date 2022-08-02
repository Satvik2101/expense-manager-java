package Account;

import java.util.UUID;

public abstract class Account {
    public final UUID id;
    public String name;
    public double amount;

    //    static int x =5;
    Account(UUID id, String name, double amount){
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
    public Account(String name, double amount) {
//        nullAccount = new NullAccount();
//        this.name = name;
//        this.id = UUID.randomUUID();
//
//        this.amount = amount;
        this(UUID.randomUUID(),name,amount);
    }

    public Account(String name) {

        this(name, 0);
    }
    public Account(Account acc){
        this(acc.id,acc.name,acc.amount);
    }

    public abstract double withdraw(double amountToMove);

    public abstract double deposit(double amountToAdd);

    public abstract double setValue(double value);

    public abstract AccountType getType();
//    static void transfer(Account receiver, Account sender, double amtToTransfer){
//        receiver.addToAccount(amtToTransfer);
//        sender.moveFromAccount(amtToTransfer);
//    }

    public abstract Account copy();

    public static Account createAccount(UUID id,String name, double amount,String type){
        AccountType accType = AccountType.fromString(type);
        switch (accType){
            case Cash:
                return new CashAccount(id,name,amount);
            case Null:
                return new NullAccount(id);
        }
        return null;
    }

    public static Account createAccount(String name, double amount,String type){
        UUID id = UUID.randomUUID();
        return createAccount(id,name,amount,type);
    }

//    static NullAccount nullAccount = new NullAccount();
}
