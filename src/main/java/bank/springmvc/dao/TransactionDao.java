package bank.springmvc.dao;

import bank.springmvc.model.Transaction;
import java.math.BigDecimal;
import java.util.ArrayList;

public interface TransactionDao {
    void processTransaction(int accountID, int userID, String transactionType, BigDecimal amount);
    ArrayList<Transaction> findTransactionsByUserID(int userID);
    Integer numberOfTransactions(int userID);
    void removeTransactions(int userID);
}