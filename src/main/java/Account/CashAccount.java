package Account;

import java.util.UUID;

public class CashAccount extends Account{

    public CashAccount(UUID id, String name, double amount){
        super (id,name,amount);
    }
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
    public AccountType getType() {
        return AccountType.Cash;
    }

    @Override
    public CashAccount copy() {
        return new CashAccount(this);
    }
}
