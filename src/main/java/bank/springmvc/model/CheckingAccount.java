package bank.springmvc.model;

import java.math.BigDecimal;

public class CheckingAccount extends Account {
    public CheckingAccount(BigDecimal balance, int accountID, int userID) {
        super("checking", balance, accountID, userID);
    }
}
