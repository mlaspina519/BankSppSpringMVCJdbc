package bank.springmvc.daoimpl;

import bank.springmvc.dao.AccountDao;
import bank.springmvc.model.Account;
import bank.springmvc.model.CheckingAccount;
import bank.springmvc.model.SavingsAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Contains methods to act upon Accounts in Bank
 * Create, Close, UpdateBalance, TypeExists, NumAccounts
 * FindByType, FindByUser
 */
@Component
public class AccountDaoImpl implements AccountDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Creates a new Account based on specified Type
     * @param type Type of Account to be created
     * @param userID User ID to create Account for
     */
    @Override
    public void createAccount(String type, int userID) {
        Random rand = new Random();

        int accountID = rand.nextInt(Integer.MAX_VALUE);

        jdbcTemplate.update(
                "INSERT INTO bank_accounts " +
                "VALUES('" + type + "', " + 0 + ", " + accountID + ", " + userID + ")");
    }

    /**
     * Closes Account based on specified ID
     * @param accountID Account ID to close
     */
    @Override
    public void closeAccount(int accountID) {
        jdbcTemplate.update(
                "DELETE FROM bank_accounts " +
                "WHERE account_id = '" + accountID+ "'");
    }

    /**
     * Updates balance for an Account
     * @param amount New amount
     * @param accountID Account ID that is being updated
     */
    @Override
    public void updateBalance(BigDecimal amount, int accountID) {
        amount = amount.setScale(2, RoundingMode.FLOOR);

        jdbcTemplate.update(
                "UPDATE bank_accounts " +
                "SET balance = '" + amount + "' " +
                "WHERE account_id = " + accountID);
    }

    /**
     * Returns if an Account of specified Type exists for specified User
     * @param userID Unique User ID to be searched for
     * @param type Account Type to be checked
     * @return True if Account Type exists, false otherwise
     */
    @Override
    public boolean accountTypeExists(int userID, String type) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT count(*) " +
                "FROM bank_accounts " +
                "WHERE user_id = " + userID + " " +
                "AND account_type = '" + type + "'",
                Integer.class);

        return count > 0;
    }

    /**
     * Returns how many Accounts a User has
     * @param userID Unique User ID to search
     * @return Number of accounts
     */
    @Override
    public Integer numberOfAccounts(int userID) {
        return jdbcTemplate.queryForObject(
                "SELECT count(*) " +
                "FROM bank_accounts " +
                "WHERE user_id = " + userID,
                Integer.class);
    }

    /**
     * Returns all Accounts for a specified User ID
     * @param userID Unique User ID to search for
     * @return ArrayList of Accounts
     */
    @Override
    public ArrayList<Account> findAccountByUserID(int userID) {
        ArrayList<Account> accounts = new ArrayList<>();

        // Checks if Checking Account exists, adds to ArrayList
        if(accountTypeExists(userID, "checking")) {
            List<CheckingAccount> checkingAccounts = jdbcTemplate.query(
                    "SELECT * " +
                    "FROM bank_accounts " +
                    "WHERE user_id = '" + userID + "' " +
                    "AND account_type = 'checking'",
                    (rs, columnNum) -> new CheckingAccount( rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id")));

            accounts.add(checkingAccounts.get(0));
        }

        // Checks if Savinsg Account exists, adds to ArrayList
        if(accountTypeExists(userID, "savings")) {
            List<SavingsAccount> savingsAccounts = jdbcTemplate.query(
                    "SELECT * " +
                    "FROM bank_accounts " +
                    "WHERE user_id = '" + userID + "' " +
                    "AND account_type = 'savings'",
                    (rs, columnNum) -> new SavingsAccount( rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id")));

            accounts.add(savingsAccounts.get(0));
        }

        return accounts;
    }

    /**
     * Returns an Account object of specified Type for specified User
     * @param userID Unique User ID to search
     * @param type Type of Account to search
     * @return Account Object if it exists, null otherwise
     */
    @Override
    public Account findAccountByType(int userID, String type) {
        if(type.equals("checking")) {
            List<CheckingAccount> checkingAccounts = jdbcTemplate.query(
                    "SELECT * " +
                    "FROM bank_accounts " +
                    "WHERE user_id = " + userID + " " +
                    "AND account_type = '" + type + "'",
                    (rs, columnNum) -> new CheckingAccount(rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id")));

            return checkingAccounts.get(0);

        } else if(type.equals("savings")) {
            List<SavingsAccount> savingsAccounts = jdbcTemplate.query(
                    "SELECT * " +
                    "FROM bank_accounts " +
                    "WHERE user_id = " + userID + " " +
                    "AND account_type = '" + type + "'",
                    (rs, columnNum) -> new SavingsAccount(rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id")));

            return savingsAccounts.get(0);
        }

        return null;
    }
}