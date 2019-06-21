package bank.springmvc.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleDBConnection {
    static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    static final String DB_USER = "SYSTEM";
    static final String DB_PASS = "1234";
    Connection connection;

    public OracleDBConnection() {
        connect();
    }

    public void connect() {
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
            ResultSet result = statement.executeQuery(query);
            return result;
        } catch(Exception ex) {
            System.out.println("ERORR while executing query");
            System.out.println(ex.toString());
            return null;
        }
    }
}
