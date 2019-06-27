package bank.springmvc.daoimpl;

import bank.springmvc.dao.AlertDao;

public class AlertDaoImpl2 implements AlertDao {
    @Override
    public void displayAlert(int accountID) {

    }

    @Override
    public void updateAlert(int accountID, double amount, String enabled) {

    }

    @Override
    public boolean alertsEnabled(int accountID) {
        return false;
    }
}
