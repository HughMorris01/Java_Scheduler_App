package database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This abstract class provides the interface between the Java program and the local MySQL database.
 * UPDATED: Pointing to localhost for C195 Portfolio usage.
 * @author Greg Farrell
 * @version 1.2
 */
public abstract class JDBC {

    // 1. Database Location configuration
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost:3306/";
    private static final String databaseName = "client_schedule";

    // 2. The Connection URL
    // Added 'allowPublicKeyRetrieval=true' and 'useSSL=false' to prevent common connection errors
    private static final String jdbcUrl = protocol + vendor + location + databaseName +
            "?connectionTimeZone=SERVER&allowPublicKeyRetrieval=true&useSSL=false";

    // 3. The Driver (Standard MySQL Driver)
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    // 4. Database Credentials
    // NOTE: Defaults to 'root' / 'root' for portfolio demonstration purposes.
    private static final String userName = "root";
    private static final String password = "root";

    private static Connection connection;

    /** * Static method uses the driver, database url and login variables to establish a connection.
     */
    public static void openConnection() {
        try {
            Class.forName(driver); // Locate the Standard MySQL Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Static method that returns the Connection object. */
    public static Connection getConnection() { return connection; }

    /** Static method that closes the connection. */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection successfully closed");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
