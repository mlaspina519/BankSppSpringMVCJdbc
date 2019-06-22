package bank.springmvc.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleDBConnection {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "SYSTEM";
    private static final String DB_PASS = "1234";
    private Connection connection;

    public OracleDBConnection() {
        connect();
    }

    private void connect() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL,
                    DB_USER, DB_PASS);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    ResultSet select(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch(Exception ex) {
            System.out.println("ERORR while executing query");
            System.out.println(ex.toString());
            return null;
        }
    }
}