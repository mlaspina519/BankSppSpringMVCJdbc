package bank.springmvc.dao;

public interface AlertDao {
    void displayAlert(int accountID);
    void updateAlert(int accountID, double amount, String enabled);
    boolean alertsEnabled(int accountID);
}
