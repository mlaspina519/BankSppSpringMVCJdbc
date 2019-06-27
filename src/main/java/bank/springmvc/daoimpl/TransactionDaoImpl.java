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
 * 
 */
@Component
public class TransactionDaoImpl implements TransactionDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     *
     * @param accountID
     * @param userID
     * @param transactionType
     * @param amount
     */
    @Override
    public void processTransaction(int accountID, int userID, String transactionType, BigDecimal amount) {
        jdbcTemplate.update("insert into bank_transactions " + "values(" + accountID + ", " + userID + ", '" + transactionType + "', " + amount + ")");
    }

    @Override
    public ArrayList<Transaction> findTransactionsByUserID(int userID) {
        List<Transaction> transactionList = jdbcTemplate.query("select * from bank_transactions where user_id = " + userID, (rs, columnNum) ->
                new Transaction(rs.getInt("account_id"), rs.getInt("user_id"), rs.getString("transaction_type"), rs.getBigDecimal("amount")));

        ArrayList<Transaction> transactions = new ArrayList<>(transactionList);

        return transactions;
    }

    /**
     * Returns the number of transactions a user has
     * @param userID - ID of user that is being searched for
     * @return - number of transactions for user
     */
    @Override
    public int numberOfTransactions(int userID) {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bank_transactions where user_id = " + userID , Integer.class);

        return count;
    }

    @Override
    public void removeTransactions(int userID) {
        jdbcTemplate.update("delete from bank_transactions" + " where user_id = " + userID);
    }
}