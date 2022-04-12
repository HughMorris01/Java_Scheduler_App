package database;

import java.sql.Connection;
import java.sql.DriverManager;

/** Some code for this class was pulled from the C195 Code Repository.
 * This abstract class provides the interface between the Java program a MySQL database.
 * CONNECTOR VERSION: Connector/J 8.0.25
 * @author Greg Farrell
 * @version 1.0
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    private static Connection connection;  // Connection Interface

    /**
     * Static method uses the driver, database url and login variables to establish a connection with the database.
     * */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Static method that returns the Connection object created by openConnection().
     * */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Static method that closes the connection established by openConnection().
     * */
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
