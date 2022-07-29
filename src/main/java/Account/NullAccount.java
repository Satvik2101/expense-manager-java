package Account;

public class NullAccount extends Account{
    public NullAccount() {
        super("NULLACC", 0);
    }

    @Override
    double withdraw(double amountToMove) {
        return 0;
    }

    @Override
    double deposit(double amountToAdd) {
        return 0;
    }
}
