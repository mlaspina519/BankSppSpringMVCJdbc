package bank.springmvc.daoimpl;

import bank.springmvc.dao.TransactionDao;
import bank.springmvc.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods to act upon Transactions in the Bank
 * Process, Remove, NumberOf, ArrayList<Transactions>
 */
@Component
public class TransactionDaoImpl implements TransactionDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Processes a Transaction for a User when something is done
     * @param accountID Account ID that called the Transaction
     * @param userID User ID of Account owner
     * @param transactionType Type of Transaction (Withdrawal/Deposit/Transfer)
     * @param amount Amount of money
     */
    @Override
    public void processTransaction(int accountID, int userID, String transactionType, BigDecimal amount) {
        jdbcTemplate.update(
                "INSERT INTO bank_transactions " +
                "VALUES(" + accountID + ", " + userID + ", '" + transactionType + "', " + amount + ")");
    }

    /**
     * Returns all Transactions for a User
     * @param userID Unique user ID to search for
     * @return ArrayList of Transactions
     */
    @Override
    public ArrayList<Transaction> findTransactionsByUserID(int userID) {
        List<Transaction> transactionList = jdbcTemplate.query(
                "SELECT * " +
                "FROM bank_transactions " +
                "WHERE user_id = " + userID,
                (rs, columnNum) -> new Transaction(rs.getInt("account_id"), rs.getInt("user_id"), rs.getString("transaction_type"), rs.getBigDecimal("amount")));

        ArrayList<Transaction> transactions = new ArrayList<>(transactionList);

        return transactions;
    }

    /**
     * Returns the number of transactions a user has
     * @param userID ID of user that is being searched for
     * @return number of transactions a User has
     */
    @Override
    public Integer numberOfTransactions(int userID) {
        return jdbcTemplate.queryForObject(
                "SELECT count(*) " +
                "FROM bank_transactions " +
                "WHERE user_id = " + userID ,
                Integer.class);
    }

    /**
     * Deletes all Transactions for a User
     * @param userID Unique User ID
     */
    @Override
    public void removeTransactions(int userID) {
        jdbcTemplate.update(
                "DELETE FROM bank_transactions " +
                "WHERE user_id = " + userID);
    }
}