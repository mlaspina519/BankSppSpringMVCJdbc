package bank.springmvc.model;

import bank.springmvc.controller.BankingApplication;

import java.math.BigDecimal;

public class Transaction {
    int accountID;
    int userID;
    String transactionType;
    BigDecimal amount;

    // Generates a Transaction
    public Transaction(int accountID, int userID, String transactionType, BigDecimal amount) {
        this.accountID = accountID;
        this.userID = userID;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public void deleteTransaction() {
        // Create transaction (withdraw all money w/e else)
        BankingApplication.TRANSACTION_MANAGER.removeTransactions(this.userID);
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
