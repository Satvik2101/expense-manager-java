package Account;

public class CashAccount extends Account{

    public CashAccount(String name, double amount) {
        super(name, amount);
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
