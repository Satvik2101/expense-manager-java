package Account;

import java.util.UUID;

public class InfiniteAccount extends Account{
    InfiniteAccount(UUID id, String name) {
        super(id, name, Double.MAX_VALUE);
    }

    public InfiniteAccount(String name) {
        super(name, Double.MAX_VALUE);
    }

    public InfiniteAccount(InfiniteAccount acc) {
        super(acc);
    }

    @Override
    public double withdraw(double amountToMove) {
        return 0;
    }

    @Override
    public double deposit(double amountToAdd) {
        return 0;
    }

    @Override
    public double setValue(double value) {
        return 0;
    }

    @Override
    public AccountType getType() {
        return AccountType.Infinite;
    }

    @Override
    public Account copy() {
        return new InfiniteAccount(this);
    }
}
