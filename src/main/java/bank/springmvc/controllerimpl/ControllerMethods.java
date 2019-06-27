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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class ControllerMethods {

    @Autowired
    UserDao userDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    TransactionDao transactionDao;

    // Updates specified account (based on ID)
    // Adds or removes money to account based on button clicked (Deposit/Withdraw)
    // Processes a transaction
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


        // TODO : add alert/overdraft
        // TODO : give user feedback
        /* Overdraft fee
        if(amountAfterWithdrawal.compareTo(BigDecimal.ZERO) < 0) {
             System.out.println("Balance dropped below $0. Overdraft penalty of $50.");
             amountAfterWithdrawal = amountAfterWithdrawal.subtract(BigDecimal.valueOf(50));
        }

       // Will send an alert to user if enabled for this account
        ALERT_MANAGER.displayAlert(currentAccount.getAccountID()); */
    }

    // Finds an account based on ID, deletes it
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

    // Allows user to enter in new first name, last name, login, password
    // If username is available (no other user exists with that login), updates all info
    public void updateInfo(User user, String first, String last, String login, String pass) {
        userDao.updateUserInfo(first, last, login, pass, user.getUserLogin());
        userDao.updateUserInfo(first, last, login, pass, user.getUserLogin());
    }

    // Allows user to transfer money either between accounts, or to another user
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

    // Displays transactions for a user
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

    // Creates new customer with specified information if one does not exist with that login
    public void createCustomer(String first, String last, String login, String pass) {
        if(userDao.findUser(login) == null) {
            userDao.addUser(new Customer(first, last, login, pass));
        }
    }

    // Finds customer in DB, if they exist then delete them, delete their accounts, delete their transactions
//    public void removeCustomer(String login) {
//        if(userDao.findUser(login) != null) {
//            userDao.findUser(login).delete();
//        }
//    }
}

