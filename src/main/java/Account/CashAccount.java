package Account;

public class CashAccount extends Account{

    public CashAccount(String name, String id, double amount) {
        super(name, id, amount);
    }

    @Override
    double withdraw(double amountToMove) {
        amount -= amountToMove;
        return amount;
    }

    @Override
    double deposit(double amountToAdd) {
        amount += amountToAdd;
        return amount;
    }
}
