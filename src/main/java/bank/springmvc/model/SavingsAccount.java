package bank.springmvc.model;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
    /**
     *  Calls super (Account) to create a Savings Account
     * @param balance Balance in account
     * @param accountID Account ID
     * @param userID User ID of Account owner
     */
    public SavingsAccount(BigDecimal balance, int accountID, int userID) {
        super("savings", balance, accountID, userID);
    }
}