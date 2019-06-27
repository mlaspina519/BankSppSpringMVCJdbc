package bank.springmvc.model;

import java.math.BigDecimal;

public class CheckingAccount extends Account {
    /**
     *  Calls super (Account) to create a Checking Account
     * @param balance Balance in account
     * @param accountID Account ID
     * @param userID User ID of Account owner
     */
    public CheckingAccount(BigDecimal balance, int accountID, int userID) {
        super("checking", balance, accountID, userID);
    }
}