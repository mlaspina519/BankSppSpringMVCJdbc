package bank.springmvc.model;

import bank.springmvc.controller.BankingApplication;

import java.math.BigDecimal;
import java.util.Random;

public abstract class Account {
    private String type;
    private BigDecimal balance;
    private int accountID;
    private int userID;

    // Creates an account with only type given, sets balance to 0
    // Generates new acc ID
    Account(String type) {
        this.type = type;
        this.balance = BigDecimal.ZERO;

        // Assigns a random integer value for account ID
        Random rand = new Random();
        this.accountID = rand.nextInt(Integer.MAX_VALUE);
    }

    // Creates an account with all parameters given
    Account(String type, BigDecimal balance, int accountID, int userID) {
        this.type = type;
        this.balance = balance;
        this.accountID = accountID;
        this.userID = userID;
    }

    public void deleteAccount() {
        // Create transaction (withdraw all money w/e else)
        BankingApplication.ACCOUNT_MANAGER.closeAccount(this.accountID);
    }

    // Getters
    public BigDecimal getBalance() { return balance; }
    public String getType() { return type; }
    public int getAccountID() { return accountID; }
    public int getUserID() { return userID; }

    // Prints out representation of an account
    // ID : Balance : User
    @Override
    public String toString() {
        return this.accountID + " : " + this.type +
                " : " + this.balance + " : " + this.userID;
    }
}
