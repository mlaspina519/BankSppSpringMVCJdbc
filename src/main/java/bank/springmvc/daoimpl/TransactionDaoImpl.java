package bank.springmvc.daoimpl;

import bank.springmvc.clientsim.BankingApplication;
import bank.springmvc.dao.TransactionDao;
import bank.springmvc.model.Transaction;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TransactionDaoImpl implements TransactionDao {

    // Updates DB to contain a transaction for specified
    // accountID : Type of transaction (Withdrawal/deposit) : amount added/removed
    @Override
    public void processTransaction(int accountID, int userID, String transactionType, BigDecimal amount) {
        try {
            BankingApplication.CONNECTION.select("insert into bank_transactions " +
                    "values(" + accountID + ", " + userID + ", '" + transactionType + "', " + amount + ")");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Returns the number of transactions a user has
    @Override
    public int numberOfTransactions(int userID) {
        try {
            ResultSet rs = BankingApplication.CONNECTION.select("select * from bank_transactions where " +
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

    // Deletes an account by specified account ID
    @Override
    public void removeTransactions(int userID) {
        try {
            BankingApplication.CONNECTION.select("delete from bank_transactions" +
                    " where user_id = '" + userID + "'");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Displays all transactions for a specified user
    @Override
    public ArrayList<Transaction> findTransactionsByUserID(int userID) {
        try {
            ResultSet rs = BankingApplication.CONNECTION.select("select * from bank_transactions where " +
                    "user_id = " + userID);

            if(!rs.next()) {
                return null;
            }

            ArrayList<Transaction> transactions = new ArrayList<>();
            int accountID;
            String transactionType;
            BigDecimal amount;

            do {
                accountID = rs.getInt("account_id");
                transactionType = rs.getString("transaction_type");
                amount = BigDecimal.valueOf(rs.getDouble("amount"));

                transactions.add(new Transaction(accountID, userID, transactionType, amount));
            } while(rs.next());

            return transactions;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
