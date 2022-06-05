package database;

import controller.AddModifyAppointmentScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Country;
import model.Division;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAmount;


public abstract class DBAppointments {

    public static ObservableList<Appointment> getEveryAppointment() {
        ObservableList<Appointment> everyAppointment = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp sts = rs.getTimestamp("Start");
                LocalDateTime start = sts.toLocalDateTime();
                Timestamp ets = rs.getTimestamp("End");
                LocalDateTime end = ets.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment tempAppointment = new Appointment(appointmentId, start, end, title, description, location, type, customerId, userId, contactId);
                everyAppointment.add(tempAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return everyAppointment;
    }

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM appointments WHERE Start >= ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            String dateString = LocalDate.now().toString();
            ps.setString(1, dateString);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp sts = rs.getTimestamp("Start");
                LocalDateTime start = sts.toLocalDateTime();
                Timestamp ets = rs.getTimestamp("End");
                LocalDateTime end = ets.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment tempAppointment = new Appointment(appointmentId, start, end, title, description, location, type, customerId, userId, contactId);
                allAppointments.add(tempAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return allAppointments;
    }

    public static ObservableList<Appointment> getAppointmentsByWeek() {
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM appointments WHERE Start >= ? AND Start <= ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            LocalDate date = LocalDate.now();
            LocalDate date1 = date.plusDays(6);
            String dateString = date.toString();
            String dateString2 = date1.toString();
            ps.setString(1, dateString);
            ps.setString(2, dateString2);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp sts = rs.getTimestamp("Start");
                LocalDateTime start = sts.toLocalDateTime();
                Timestamp ets = rs.getTimestamp("End");
                LocalDateTime end = ets.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment tempAppointment = new Appointment(appointmentId, start, end, title, description, location, type, customerId, userId, contactId);
                weeklyAppointments.add(tempAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return weeklyAppointments;
    }

    public static ObservableList<Appointment> getAppointmentsByMonth() {
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM appointments WHERE Start >= ? AND Start <= ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            LocalDate date = LocalDate.now();
            LocalDate date1 = date.plusDays(30);
            String dateString = date.toString();
            String dateString2 = date1.toString();
            ps.setString(1, dateString);
            ps.setString(2, dateString2);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp sts = rs.getTimestamp("Start");
                LocalDateTime start = sts.toLocalDateTime();
                Timestamp ets = rs.getTimestamp("End");
                LocalDateTime end = ets.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment tempAppointment = new Appointment(appointmentId, start, end, title, description, location, type, customerId, userId, contactId);
                monthlyAppointments.add(tempAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return monthlyAppointments;
    }

    public static ObservableList<Appointment> getAppointmentsByContact(Contact contact) {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM appointments WHERE Start >= ? AND Contact_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            LocalDate date = LocalDate.now();
            String dateString = date.toString();
            int selectedContactId = contact.getId();
            ps.setString(1, dateString);
            ps.setInt(2, selectedContactId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp sts = rs.getTimestamp("Start");
                LocalDateTime start = sts.toLocalDateTime();
                Timestamp ets = rs.getTimestamp("End");
                LocalDateTime end = ets.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment tempAppointment = new Appointment(appointmentId, start, end, title, description, location, type, customerId, userId, contactId);
                contactAppointments.add(tempAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return contactAppointments;
    }

    public static ObservableList<Appointment> getAppointmentsByCustomerId(int customerId) {
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp sts = rs.getTimestamp("Start");
                LocalDateTime start = sts.toLocalDateTime();
                Timestamp ets = rs.getTimestamp("End");
                LocalDateTime end = ets.toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment tempAppointment = new Appointment(appointmentId, start, end, title, description, location, type, customerID, userId, contactId);
                customerAppointments.add(tempAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return customerAppointments;
    }

    public static ObservableList<Appointment> getAppointmentsByMonthAndType(String selectedType, Month month) {
        ObservableList<Appointment> reportAppointments = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM appointments WHERE Start >= ? AND End <= ? AND Type = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            LocalDate localDateStart = LocalDate.of(2022, month, 1);
            LocalDate localDateEnd = LocalDate.of(2022, month.plus(1), 1);
            String dateString = localDateStart.toString();
            String dateString2 = localDateEnd.toString();
            ps.setString(1, dateString);
            ps.setString(2, dateString2);
            ps.setString(3, selectedType);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp sts = rs.getTimestamp("Start");
                LocalDateTime start = sts.toLocalDateTime();
                Timestamp ets = rs.getTimestamp("End");
                LocalDateTime end = ets.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment tempAppointment = new Appointment(appointmentId, start, end, title, description, location, type, customerId, userId, contactId);
                reportAppointments.add(tempAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return reportAppointments;
    }

    public static ObservableList<Appointment> getAppointmentsByMonthAndLocation(Division selectedDivision, Month month) {
        ObservableList<Appointment> reportAppointments = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM appointments WHERE Start >= ? AND End <= ? AND Location = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            LocalDate localDateStart = LocalDate.of(2022, month, 1);
            LocalDate localDateEnd = LocalDate.of(2022, month.plus(1), 1);
            String dateString = localDateStart.toString();
            String dateString2 = localDateEnd.toString();
            String selectedLocationString = selectedDivision.getDivisionName();
            ps.setString(1, dateString);
            ps.setString(2, dateString2);
            ps.setString(3, selectedLocationString);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp sts = rs.getTimestamp("Start");
                LocalDateTime start = sts.toLocalDateTime();
                Timestamp ets = rs.getTimestamp("End");
                LocalDateTime end = ets.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment tempAppointment = new Appointment(appointmentId, start, end, title, description, location, type, customerId, userId, contactId);
                reportAppointments.add(tempAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return reportAppointments;
    }

    public static void insertAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int contactId, int userId) throws SQLException {
            String sqlCommand = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customerId);
            ps.setInt(8, contactId);
            ps.setInt(9, userId);
            ps.executeUpdate();
    }

    public static void updateAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int contactId, int userId, int appointmentId) throws SQLException {
        String sqlCommand = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, Contact_ID = ?, User_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, customerId);
        ps.setInt(8, contactId);
        ps.setInt(9, userId);
        ps.setInt(10, appointmentId);
        ps.executeUpdate();
    }

    public static void deleteAppointment(int appointmentId) throws SQLException {
        String sqlCommand = "DELETE FROM appointments WHERE Appointment_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }

    public static void deleteAppointmentByCustomer(int customerId) throws SQLException {
        String sqlCommand = "DELETE FROM appointments WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }
}

