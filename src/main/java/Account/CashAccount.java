package Account;

public class CashAccount extends Account{

    public CashAccount(String name, double amount) {
        super(name, amount);
    }
    public CashAccount(CashAccount acc){
        super(acc);
    }
    @Override
    public double withdraw(double amountToMove) {
        amount -= amountToMove;
        return amount;
    }

    @Override
    public double deposit(double amountToAdd) {
        amount += amountToAdd;
        return amount;
    }

    @Override
    public double setValue(double value) {
        amount= value;
        return amount;
    }

    @Override
    public CashAccount makeCopy() {
        return new CashAccount(this);
    }
}
