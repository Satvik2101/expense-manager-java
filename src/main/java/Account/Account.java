package Account;

public abstract class Account {
    String name;
    final String id;
    double amount;
//    static int x =5;
    public Account(String name,String id, double amount){
//        nullAccount = new NullAccount();
        this.name = name;
        this.id = id;
        this.amount = amount;
    }
    public Account(String name, String id) {

         this(name, id, 0);
    }

    abstract double withdraw(double amountToMove);
    abstract double deposit(double amountToAdd);

//    static void transfer(Account receiver, Account sender, double amtToTransfer){
//        receiver.addToAccount(amtToTransfer);
//        sender.moveFromAccount(amtToTransfer);
//    }

//    static NullAccount nullAccount = new NullAccount();
}
