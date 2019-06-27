package bank.springmvc.model;

import java.math.BigDecimal;

public class Transaction {
    private int accountID;
    private int userID;
    private String transactionType;
    private BigDecimal amount;

    /**
     * Constructor for a generated Transaction
     * @param accountID The account ID that is processing the Transaction
     * @param userID The user ID that is processing the Transaction
     * @param transactionType The type of Transaction (withdrawal/deposit/transfer)
     * @param amount The amount being transferred
     */
    public Transaction(int accountID, int userID, String transactionType, BigDecimal amount) {
        this.accountID = accountID;
        this.userID = userID;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    // Getters
    public int getAccountID() { return accountID; }
    public int getUserID() { return userID; }
    public String getTransactionType() { return transactionType; }
    public BigDecimal getAmount() { return amount; }
}