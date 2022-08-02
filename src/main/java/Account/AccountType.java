package Account;

public enum AccountType {
    Cash,
    Null,
    ;  @Override
    public String toString() {
        switch (this){
            case Cash:return "Cash";
            case Null:return "NullAcc";
//            case None:return "NONE";
            default:throw new IllegalArgumentException("Enum AccountType provided with invalid value");
        }

    }

    static AccountType fromString(String arg){
        switch (arg){
            case "Cash": return AccountType.Cash;
            case "NullAcc": return AccountType.Null;
//            case "NONE": return DiscountType.None;
            default:throw new IllegalArgumentException("Enum AccountType fromString provided with invalid value");

        }
    }
}
