package bank.springmvc.dao;

import bank.springmvc.model.Account;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface AccountDao {
    void createAccount(String type, int userID);
    void closeAccount(int accountID);
    void updateBalance(BigDecimal amount, int accountID);
    boolean accountTypeExists(int userID, String type);
    int numberOfAccounts(int userID);
    ArrayList<Account> findAccountByUserID(int userID);
    Account findAccountByType(int userID, String type);
}
