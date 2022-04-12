package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomers {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static int insertCustomer(String name, String address, int divisionId) throws SQLException {

        String sqlCommand = "INSERT INTO customers (Customer_Name, Address, Division_ID) VALUES (?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setInt(3, divisionId);
        return ps.executeUpdate();
    }

    public static int updateCustomer(int customerId, String updatedName) throws SQLException {
        String sqlCommand = "UPDATE customers SET Customer_Name = ? WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, updatedName);
        ps.setInt(2, customerId);
        return ps.executeUpdate();
    }

    public static int deleteCustomer(int customerId) throws SQLException {
        String sqlCommand = "DELETE FROM customers WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setInt(1, customerId);
        return ps.executeUpdate();
    }

    public static void selectCustomers() throws SQLException {
        String sqlCommand = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String customerName = rs.getString("Customer_Name");
            String address =  rs.getString("Address");
            System.out.println("Name: " + customerName + " Address: " + address);
        }

    }
}
