package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContacts {
    public static int contactId = 3;

    public static int insertContact(String name, String email) throws SQLException {

        String sqlCommand = "INSERT INTO contacts (Contact_Name, Email) VALUES (?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, name);
        ps.setString(2, email);
        int rowsAffected = ps.executeUpdate();
        contactId++;
        return rowsAffected;
    }
}
