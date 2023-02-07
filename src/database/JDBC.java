package database;

import java.sql.Connection;
import java.sql.DriverManager;

/** Some code for this class was pulled from the C195 Code Repository.
 * This abstract class provides the interface between the Java program and a MySQL database.
 * CONNECTOR VERSION: aws-mysql-jdbc-1.1.3
 * @author Greg Farrell
 * @version 1.1
 */
public abstract class JDBC {
    /** String for database protocol. */
    private static final String protocol = "jdbc";
    /** String for database vendor. */
    private static final String vendor = ":mysql:";
    /** String for database address. */
    private static final String location = "//localhost/";
    private static final String location1 = "//capstone-db.cget13oy91km.us-east-1.rds.amazonaws.com/";
    /** String for database name. */
    private static final String databaseName = "client_schedule";
    /** String for full URL. */
    private static final String jdbcUrl = protocol + vendor + location1 + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    /** String for driver reference. */
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String driver1 = "software.aws.rds.jdbc.mysql.Driver";
    /** String for database username. */
    private static final String userName = "sqlUser";
    private static final String userName1= "hughmorris";
    /** String for database password. */
    final private static String password = "Passw0rd!";
    final private static String password1 = "AWSQueen4383";
    /** Connection interface object. */
    private static Connection connection;

    /** Static method uses the driver, database url and login variables to establish a connection with the database. */
    public static void openConnection() {
        try {
            Class.forName(driver1); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName1, password1); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Static method that returns the Connection object created by openConnection().
     * @return connection as a Connection object. */
    public static Connection getConnection() { return connection; }

    /** Static method that closes the connection established by openConnection(). */
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
