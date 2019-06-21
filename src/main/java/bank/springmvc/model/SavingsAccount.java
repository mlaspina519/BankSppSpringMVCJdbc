package bank.springmvc.model;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
    public SavingsAccount(BigDecimal balance, int accountID, int userID) {
        super("savings", balance, accountID, userID);
    }
}
