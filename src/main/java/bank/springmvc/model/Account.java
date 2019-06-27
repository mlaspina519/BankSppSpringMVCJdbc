package bank.springmvc.model;

import bank.springmvc.BankingApplication;
import java.math.BigDecimal;

public abstract class Account {
    private String type;
    private BigDecimal balance;
    private int accountID;
    private int userID;

    // Creates an account with all parameters given
    Account(String type, BigDecimal balance, int accountID, int userID) {
        this.type = type;
        this.balance = balance;
        this.accountID = accountID;
        this.userID = userID;
    }

    // Getters
    public BigDecimal getBalance() { return balance; }
    public String getType() { return type; }
    public int getAccountID() { return accountID; }
    public int getUserID() { return userID; }
}
