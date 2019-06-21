package bank.springmvc.daoimpl;

import bank.springmvc.model.Account;
import bank.springmvc.model.CheckingAccount;
import bank.springmvc.model.SavingsAccount;
import bank.springmvc.controller.BankingApplication;
import bank.springmvc.dao.AccountDao;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

public class AccountDaoImpl implements AccountDao {



    // Creates an account of specified type
    public void createAccount(String type, int userID) {
        Random rand = new Random();

        int accountID = rand.nextInt(Integer.MAX_VALUE);

        try {
            BankingApplication.CONNECTION.select("insert into bank_accounts " +
                    "values('" + type + "', " + 0 + ", " + accountID + ", " + userID + ")");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Finds an account, updates the balance with specified amount
    public void updateBalance(BigDecimal amount, int accountID) {
        try {
            amount = amount.setScale(2, RoundingMode.FLOOR);

            BankingApplication.CONNECTION.select("update bank_accounts " +
                    "set balance = '" + amount + "' where account_id = " + accountID);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Deletes an account by specified account ID
    public void closeAccount(int accountID) {
        try {
            BankingApplication.CONNECTION.select("delete from bank_accounts" +
                    " where account_id = '" + accountID+ "'");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Returns an Account based on Type
    public Account findAccountByType(int userID, String type) {
        try {
            String newType = type.toLowerCase();
            ResultSet rs = BankingApplication.CONNECTION.select("select * from bank_accounts where " +
                    "account_type = '" + newType + "' and user_id = " + userID);

            if(!rs.next()) {
                return null;
            } else {
                if(newType.equals("checking")) {
                    return new CheckingAccount(rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id"));
                } else if(newType.equals("savings")) {
                    return new SavingsAccount(rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id"));
                }
            }


        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Returns Account ArrayList based on user ID
    public ArrayList<Account> findAccountByUserID(int userID) {
        try {
            ResultSet rs = BankingApplication.CONNECTION.select("select * from bank_accounts where " +
                    "user_id = " + userID);

            // If no accounts exist for user ID, return null
            if(!rs.next()) {
                return null;
            } else {
                ArrayList<Account> accounts = new ArrayList<Account>();

                do {
                    if (rs.getString("account_type").equals("checking")) {
                        accounts.add(new CheckingAccount(rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id")));
                    } else if (rs.getString("account_type").equals("savings")) {
                        accounts.add(new SavingsAccount(rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id")));
                    }
                } while(rs.next());

                return accounts;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Checks if an account of a specific type exists for user
    public boolean accountTypeExists(int userID, String type) {
        try {
            ResultSet rs = BankingApplication.CONNECTION.select("select * from bank_accounts where " +
                    "user_id = " + userID + " and account_type = '" + type + "'" );

            // If user has an existing account of specified type, return true, else false
            return rs.next();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Returns an Account object based on account ID
    public Account findAccountByAccountID(int accountID) {
        try {
            ResultSet rs = BankingApplication.CONNECTION.select("select * from bank_accounts where " +
                    "account_id = " + accountID);

            // If no accounts exist for accountID
            if(!rs.next()) {
                return null;
            } else {
                if (rs.getString("account_type").equals("Checking")) {
                    return new CheckingAccount(rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id"));
                } else if (rs.getString("account_type").equals("Savings")) {
                    return new SavingsAccount(rs.getBigDecimal("balance"), rs.getInt("account_id"), rs.getInt("user_id"));
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Returns the number of accounts a user has
    public int numberOfAccounts(int userID) {
        try {
            ResultSet rs = BankingApplication.CONNECTION.select("select * from bank_accounts where " +
                    "user_id = " + userID);

            // If no accounts exist for accountID
            if(!rs.next()) {
                return 0;
            } else {
                int num = 1;

                while(rs.next()) {
                    num++;
                }

                return num;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // Lists all accounts a user currently has
    public void listAccounts(int userID) {
        try {
            ResultSet rs = BankingApplication.CONNECTION.select("select * from bank_accounts where " +
                    "user_id='" + userID + "'");
            while(rs.next()) {
                String type = rs.getString("account_type");
                BigDecimal balance = rs.getBigDecimal("balance");
                int accountID = rs.getInt("account_id");
                System.out.println(type + " Account ~ ID : " + accountID + " Balance : " + balance);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
