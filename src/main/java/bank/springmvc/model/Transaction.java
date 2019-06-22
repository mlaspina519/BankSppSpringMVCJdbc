package bank.springmvc.model;

import java.math.BigDecimal;

public class Transaction {
    private int accountID;
    private int userID;
    private String transactionType;
    private BigDecimal amount;

    // Generates a Transaction
    public Transaction(int accountID, int userID, String transactionType, BigDecimal amount) {
        this.accountID = accountID;
        this.userID = userID;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public int getAccountID() {
        return accountID;
    }
    public int getUserID() {
        return userID;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public BigDecimal getAmount() {
        return amount;
    }
}