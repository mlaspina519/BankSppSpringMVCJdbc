package bank.springmvc.controllerimpl;

import bank.springmvc.BankingApplication;
import bank.springmvc.dao.AccountDao;
import bank.springmvc.dao.TransactionDao;
import bank.springmvc.dao.UserDao;
import bank.springmvc.model.Account;
import bank.springmvc.model.Customer;
import bank.springmvc.model.Transaction;
import bank.springmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Service Class that handles all of the Business Logic
 * Takes information/request in from Controller, performs operations, sends to DAO layer
 */
@Service
public class BankServices {

    @Autowired
    UserDao userDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    TransactionDao transactionDao;

    /**
     * Updates specified Account with a new Balance, Processes a Transaction
     * @param amount Amount of money
     * @param transactionType Type of Transaction (Deposit/Withdrawal, Checking/Savings)
     * @param user User Object to act upon
     */
    public void updateAccountBalance(BigDecimal amount, String transactionType, User user) {

        switch(transactionType) {
            case "Deposit Into Checking":
                // If checking account exists
                if(accountDao.accountTypeExists(user.getUserID(), "checking")) {
                    // Gets current balance, adds deposit value to it, updates balance
                    // Processes transaction
                    Account account = accountDao.findAccountByType(user.getUserID(), "checking");
                    BigDecimal amountBeforeDeposit = account.getBalance();
                    BigDecimal amountAfterDeposit = amountBeforeDeposit.add(amount);
                    accountDao.updateBalance(amountAfterDeposit, account.getAccountID());
                    transactionDao.processTransaction(account.getAccountID(), BankingApplication.CURRENT_USER.getUserID(),
                            "Deposit", amount);
                }
                break;
            case "Deposit Into Savings":
                // If savings account exists
                if(accountDao.accountTypeExists(user.getUserID(), "savings")) {
                    // Gets current balance, adds deposit value to it, updates balance
                    // Processes transaction
                    Account account = accountDao.findAccountByType(user.getUserID(), "savings");
                    BigDecimal amountBeforeDeposit = account.getBalance();
                    BigDecimal amountAfterDeposit = amountBeforeDeposit.add(amount);
                    accountDao.updateBalance(amountAfterDeposit, account.getAccountID());
                    transactionDao.processTransaction(account.getAccountID(), BankingApplication.CURRENT_USER.getUserID(),
                            "Deposit", amount);
                }
                break;
            case "Withdraw From Checking":
                // If checking account exists
                if(accountDao.accountTypeExists(user.getUserID(), "checking")) {
                    // Gets current balance, subtracts withdrawal value from it, updates balance
                    // Processes transaction
                    Account account = accountDao.findAccountByType(user.getUserID(), "checking");
                    BigDecimal amountBeforeDeposit = account.getBalance();
                    BigDecimal amountAfterDeposit = amountBeforeDeposit.subtract(amount);
                    accountDao.updateBalance(amountAfterDeposit, account.getAccountID());
                    transactionDao.processTransaction(account.getAccountID(), BankingApplication.CURRENT_USER.getUserID(),
                            "Withdrawal", amount);
                }
                break;
            case "Withdraw From Savings":
                // If checking account exists
                if(accountDao.accountTypeExists(user.getUserID(), "savings")) {
                    // Gets current balance, subtracts withdrawal value from it, updates balance
                    // Processes transaction
                    Account account = accountDao.findAccountByType(user.getUserID(), "savings");
                    BigDecimal amountBeforeDeposit = account.getBalance();
                    BigDecimal amountAfterDeposit = amountBeforeDeposit.subtract(amount);
                    accountDao.updateBalance(amountAfterDeposit, account.getAccountID());
                    transactionDao.processTransaction(account.getAccountID(), BankingApplication.CURRENT_USER.getUserID(),
                            "Withdrawal", amount);
                }
                break;
        }
    }

    /**
     * Performs a specified Account Operation on a User
     * @param user User to search for
     * @param accountOperation Operation to be done (Open/Close, Checking/Savings)
     */
    public void manageAccount(User user, String accountOperation) {
        switch(accountOperation) {
            case "Close Savings Account":
                Account savingsAccount = accountDao.findAccountByType(user.getUserID(), "savings");
                // If account exists, close it
                if(savingsAccount != null) {
                    accountDao.closeAccount(savingsAccount.getAccountID());
                }
                break;
            case "Close Checking Account":
                Account checkingAccount = accountDao.findAccountByType(user.getUserID(), "checking");
                // If account exists, close it
                if(checkingAccount != null) {
                    accountDao.closeAccount(checkingAccount.getAccountID());
                }
                break;
            case "Open Checking Account":
                if(!accountDao.accountTypeExists(user.getUserID(), "checking")) {
                    accountDao.createAccount("checking", user.getUserID());
                }
                break;
            case "Open Savings Account":
                if(!accountDao.accountTypeExists(user.getUserID(), "savings")) {
                    accountDao.createAccount("savings", user.getUserID());
                }
                break;
            default:
                break;
        }

        // TODO: process remaining money, create transaction, whatever else
    }

    /**
     * Allows User to enter in new information, excluding Type and ID
     * @param user User to update
     * @param first New first name
     * @param last New last name
     * @param login New login
     * @param pass New password
     */
    public void updateInfo(User user, String first, String last, String login, String pass) {
        userDao.updateUserInfo(first, last, login, pass, user.getUserLogin());
    }

    /**
     * Allows User to transfer money between Accounts or to another User
     * @param amount Amount to transfer
     * @param transferType Type of Transfer (To savings/To checking/To user)
     */
    public void transferMoney(BigDecimal amount, String transferType) {
        if(transferType.equals("Transfer To Savings")) {
            // Checks that user has a checking account and a savings account
            if(accountDao.numberOfAccounts(BankingApplication.CURRENT_USER.getUserID()) == 2) {
                int userID = BankingApplication.CURRENT_USER.getUserID();

                // Removes money from Checking Account
                Account accountToTakeFrom = accountDao.findAccountByType(userID, "checking");
                BigDecimal accountToTakeFromBalance = accountToTakeFrom.getBalance();
                accountDao.updateBalance(accountToTakeFromBalance.subtract(amount), accountToTakeFrom.getAccountID());

                // Adds money to Savings Account
                Account accountToAddTo = accountDao.findAccountByType(userID, "savings");
                BigDecimal accountToAddToBalance = accountToAddTo.getBalance();
                accountDao.updateBalance(accountToAddToBalance.add(amount), accountToAddTo.getAccountID());
//
                // Processes Transaction
                transactionDao.processTransaction(accountToTakeFrom.getAccountID(), userID, "Transfer Out", amount);
                transactionDao.processTransaction(accountToAddTo.getAccountID(), userID, "Transfer In", amount);
            }
        } else if(transferType.equals("Transfer To Checking")) {
            // Checks that user has a checking account and savings account
            if(accountDao.numberOfAccounts(BankingApplication.CURRENT_USER.getUserID()) == 2) {
                int userID = BankingApplication.CURRENT_USER.getUserID();

                // Removes money from Savings Account
                Account accountToTakeFrom = accountDao.findAccountByType(userID, "savings");
                BigDecimal accountToTakeFromBalance = accountToTakeFrom.getBalance();
                accountDao.updateBalance(accountToTakeFromBalance.subtract(amount), accountToTakeFrom.getAccountID());

                // Adds money to Checking Account
                Account accountToAddTo = accountDao.findAccountByType(userID, "checking");
                BigDecimal accountToAddToBalance = accountToAddTo.getBalance();
                accountDao.updateBalance(accountToAddToBalance.add(amount), accountToAddTo.getAccountID());
//
                // Processes Transaction
                transactionDao.processTransaction(accountToTakeFrom.getAccountID(), userID, "Transfer Out", amount);
                transactionDao.processTransaction(accountToAddTo.getAccountID(), userID, "Transfer In", amount);
            }
        } else {
            int userID = BankingApplication.CURRENT_USER.getUserID();
            User userToTransferTo = userDao.findUser(transferType);

            // Checks that user has a checking account, and target has a checking account
            if(accountDao.accountTypeExists(userID, "checking") &&
                    accountDao.accountTypeExists(userToTransferTo.getUserID(), "checking")) {

                // Removes money from Checking Account of currently logged in user
                Account accountToTakeFrom = accountDao.findAccountByType(userID, "checking");
                BigDecimal accountToTakeFromBalance = accountToTakeFrom.getBalance();
                accountDao.updateBalance(accountToTakeFromBalance.subtract(amount), accountToTakeFrom.getAccountID());

                // Adds money to Checking Account of target user
                Account accountToAddTo = accountDao.findAccountByType(userToTransferTo.getUserID(), "checking");
                BigDecimal accountToAddToBalance = accountToAddTo.getBalance();
                accountDao.updateBalance(accountToAddToBalance.add(amount), accountToAddTo.getAccountID());

                // Processes Transactions, one for user, one for target
                transactionDao.processTransaction(accountToTakeFrom.getAccountID(), userID, "Transfer Out", amount);
                transactionDao.processTransaction(accountToAddTo.getAccountID(), userToTransferTo.getUserID(), "Transfer In", amount);
            }
        }
    }

    /**
     * Displays Transactions for a specified User
     * @param callingUser User that called this method
     * @param user User that is going to have Transactions shown
     * @param model Model to print Transactions to
     */
    public void displayTransactions(User callingUser, User user, Model model) {
        // Stores first name, user type, and transactions for specified user
        String firstName = user.getFirstName();
        String userType = user.getUserType();
        ArrayList<Transaction> transactions = transactionDao.findTransactionsByUserID(user.getUserID());

        // Stores the user type of the user CALLING this method, for redirection from JSP
        String callingUserType = callingUser.getUserType();

        // Sends transactions to jsp
        model.addAttribute("transactions", transactions);
        model.addAttribute("userType", userType);
        model.addAttribute("callingUserType", callingUserType);
        model.addAttribute("firstName", firstName);
    }

    /**
     * Create a new Customer
     * @param first First name
     * @param last Last name
     * @param login Login
     * @param pass Password
     */
    public void createCustomer(String first, String last, String login, String pass) {
        if(userDao.findUser(login) == null) {
            userDao.addUser(new Customer(first, last, login, pass));
        }
    }

    /**
     * Removes an existing Customer if they exist in DB, and their Accounts
     * @param login Login of Customer to be removed
     */
    public void removeCustomer(String login) {
        if(userDao.findUser(login) != null) {
            userDao.removeUser(login);
        }
    }
}

