package Account;

public enum AccountType {
    Cash,
    Null,
    Infinite,
    ;  @Override
    public String toString() {
        switch (this){
            case Cash:return "Cash";
            case Null:return "Null";
            case Infinite:return "Infinite";
//            case None:return "NONE";
            default:throw new IllegalArgumentException("Enum AccountType provided with invalid value");
        }

    }

    static AccountType fromString(String arg){
        switch (arg){
            case "Cash": return AccountType.Cash;
            case "Null": return AccountType.Null;
            case "Infinite":return AccountType.Infinite;
//            case "NONE": return DiscountType.None;
            default:throw new IllegalArgumentException("Enum AccountType fromString provided with invalid value");

        }
    }
}
