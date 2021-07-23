package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class used to create the database connection, to start the database connection, and to close the database connection.
 */
public class DatabaseConnection {
    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ05bQR";
    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    // Driver and Connection Interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    // Username and Password
    private static final String username = "U05bQR";
    private static final String password = "53688457900";
    private static Connection conn = null;

    /**
     * Starts the database connection.
     * @return connection to the database
     */
    public static Connection getConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful!");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}