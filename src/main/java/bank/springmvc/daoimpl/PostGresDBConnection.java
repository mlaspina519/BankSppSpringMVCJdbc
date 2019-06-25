package bank.springmvc.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostGresDBConnection {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/BankDB";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "1234";
    private Connection connection;

    public PostGresDBConnection() {
        connect();
    }

    private void connect() {
        try {
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