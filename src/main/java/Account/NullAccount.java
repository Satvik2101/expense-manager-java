package Account;

public class NullAccount extends Account{
    public NullAccount() {
        super("NULLACC", 0);
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
    public NullAccount copy() {
        return this;
    }
}
