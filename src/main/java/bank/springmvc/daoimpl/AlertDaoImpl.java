//package bank.springmvc.daoimpl;
//
//import bank.springmvc.BankingApplication;
//import bank.springmvc.dao.AlertDao;
//import java.sql.ResultSet;
//
//public class AlertDaoImpl implements AlertDao {
//
//    // Displays a message to user when account falls below a certain amount
//    @Override
//    public void displayAlert(int accountID) {
//        try {
//            ResultSet rs = BankingApplication.CONNECTION.select("select * from bank_alerts where " +
//                    "account_id = " + accountID);
//
//            // If no accounts exist for accountID
//            if(rs.next()) {
//                if(rs.getString("alert_enabled").equals("true")) {
//                    System.out.println("ALERT : Account has dropped below $" + rs.getString("alert_level"));
//                }
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Updates alert amount for a specified account
//    @Override
//    public void updateAlert(int accountID, double amount, String enabled) {
//        try {
//            ResultSet rs = BankingApplication.CONNECTION.select("select * from bank_alerts where " +
//                    "account_id = " + accountID);
//
//            // If no accounts exist for accountID
//            if(!rs.next()) {
//                BankingApplication.CONNECTION.select("insert into bank_alerts " +
//                        "values(" + accountID + ", " + amount + ", '" + "true" + "')");
//            } else {
//                BankingApplication.CONNECTION.select("update bank_alerts set alert_level = " + amount + ", alert_enabled = '"
//                        + enabled + "' where account_id = " + accountID);
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Checks if an account has alerts enabled
//    @Override
//    public boolean alertsEnabled(int accountID) {
//        try {
//            ResultSet rs = BankingApplication.CONNECTION.select("select * from bank_alerts where " +
//                    "account_id = " + accountID);
//
//            if(rs.next()) {
//                return rs.getString("alert_enabled").equals("true");
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//}
