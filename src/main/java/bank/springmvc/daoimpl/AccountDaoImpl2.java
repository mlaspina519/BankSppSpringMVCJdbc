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

@Component
public class AccountDaoImpl2 implements AccountDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void createAccount(String type, int userID) {
        Random rand = new Random();

        int accountID = rand.nextInt(Integer.MAX_VALUE);

        jdbcTemplate.update("insert into bank_accounts " + "values('" + type + "', " + 0 + ", " + accountID + ", " + userID + ")");
    }

    @Override
    public void closeAccount(int accountID) {
        jdbcTemplate.update("delete from bank_accounts" + " where account_id = '" + accountID+ "'");
    }

    @Override
    public void updateBalance(BigDecimal amount, int accountID) {
        amount = amount.setScale(2, RoundingMode.FLOOR);

        jdbcTemplate.update("update bank_accounts " + "set balance = '" + amount + "' where account_id = " + accountID);
    }

    @Override
    public boolean accountTypeExists(int userID, String type) {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bank_accounts where user_id = " + userID + " and account_type = '" + type + "'", Integer.class);

        return count > 0;
    }

    @Override
    public int numberOfAccounts(int userID) {
        Integer count = jdbcTemplate.queryForObject("select count(*) from bank_accounts where user_id = " + userID, Integer.class);

        return count;
    }

    @Override
    public ArrayList<Account> findAccountByUserID(int userID) {
        ArrayList<Account> accounts = new ArrayList<>();

        if(accountTypeExists(userID, "checking")) {
            List<CheckingAccount> checkingAccounts = jdbcTemplate.query("select * from bank_accounts where user_id = '" + userID + "' and account_type = 'checking'", (rs, columnNum) ->
                    new CheckingAccount( rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id")));
            accounts.add(checkingAccounts.get(0));
        }

        if(accountTypeExists(userID, "savings")) {
            List<SavingsAccount> savingsAccounts = jdbcTemplate.query("select * from bank_accounts where user_id = '" + userID + "' and account_type = 'savings'", (rs, columnNum) ->
                    new SavingsAccount( rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id")));
            accounts.add(savingsAccounts.get(0));
        }

        return accounts;
    }

    @Override
    public Account findAccountByType(int userID, String type) {
        if(type.equals("checking")) {
            List<CheckingAccount> checkingAccounts = jdbcTemplate.query("select * from bank_accounts where user_id = " + userID + " and account_type = '" + type + "'", (rs, columnNum) ->
                    new CheckingAccount(rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id")));

            return checkingAccounts.get(0);
        } else if(type.equals("savings")) {
            List<SavingsAccount> savingsAccounts = jdbcTemplate.query("select * from bank_accounts where user_id = " + userID + " and account_type = '" + type + "'", (rs, columnNum) ->
                    new SavingsAccount(rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id")));

            return savingsAccounts.get(0);
        }

        return null;
    }
}
