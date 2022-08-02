package Account;

import java.util.UUID;

public class NullAccount extends Account{

    public NullAccount(UUID id){
        super(id,"NULLACC",0);
    }
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
    public AccountType getType() {
        return AccountType.Null;
    }

    @Override
    public NullAccount copy() {
        return this;
    }
}
