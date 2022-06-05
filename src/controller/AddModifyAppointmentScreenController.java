package controller;

import database.DBAppointments;
import database.DBContacts;
import database.DBCustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AddModifyAppointmentScreenController implements Initializable {
    public Label screenLabel;
    public Button submitButton;
    public static boolean isNewAppointment = false;
    public ComboBox<Contact> contactComboBox;
    public ComboBox<Customer> customerComboBox;
    public TextField appointmentIdTextField;
    public TextField userIdTextField;
    public DatePicker datePickerComboBox;
    public ComboBox<String> startTimeComboBox;
    public ComboBox<String> endTimeComboBox;
    public TextField titleTextField;
    public TextField locationTextField;
    public TextField descriptionTextField;
    public ComboBox<String> typeComboBox;
    public TextField typeTextField;
    public static Appointment tempAppointment = null;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (isNewAppointment) { screenLabel.setText("Enter Appointment Information");
        submitButton.setText("Confirm Appointment");}
        else {screenLabel.setText("Update Existing Appointment");
        submitButton.setText("Save Changes");}

        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("In-Person");
        types.add("Remote");
        types.add("Group Session");

        customerComboBox.setItems(DBCustomers.getAllCustomers());
        contactComboBox.setItems(DBContacts.getAllContacts());
        typeComboBox.setItems(types);
        userIdTextField.setText(Integer.toString(MainScreenController.getUser().getUserId()));

        if (tempAppointment != null) {
            appointmentIdTextField.setText(String.valueOf(tempAppointment.getAppointmentId()));
            customerComboBox.setValue(tempAppointment.getCustomer());
            datePickerComboBox.setValue(tempAppointment.getStart().toLocalDate());
            startTimeComboBox.setValue(tempAppointment.getStartTimeString());
            onStartTime();
            endTimeComboBox.setValue(tempAppointment.getEndTimeString());
            titleTextField.setText(tempAppointment.getTitle());
            descriptionTextField.setText(tempAppointment.getDescription());
            locationTextField.setText(tempAppointment.getLocation());
            typeComboBox.setValue(tempAppointment.getType());
            contactComboBox.setValue(tempAppointment.getContact());
        }
        else {
            appointmentIdTextField.setText(null);
            customerComboBox.setValue(null);
            datePickerComboBox.setValue(null);
            startTimeComboBox.setValue(null);
            endTimeComboBox.setValue(null);
            endTimeComboBox.setDisable(true);
            titleTextField.setText(null);
            descriptionTextField.setText(null);
            locationTextField.setText(null);
            typeComboBox.setValue(null);
            contactComboBox.setValue(null);
        }

        ObservableList<String> startTimes = FXCollections.observableArrayList();
        for(int i = 0; i < 56; i++) {
            LocalTime lt = LocalTime.of(8,0);
            lt = lt.plusMinutes(15 * i);
            String t1 = lt.format(DateTimeFormatter.ofPattern("h:mm a"));
            startTimes.add(t1);
        }
        startTimeComboBox.setItems(startTimes);
    }

    public void toBack(ActionEvent actionEvent) throws IOException {
        tempAppointment = null;

        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 1050, 450);
        stage.setScene(scene);
        stage.setTitle("Appointment Screen");
    }

    public void onSubmit(ActionEvent actionEvent) throws IOException, SQLException {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        ObservableList<Appointment> localCustomerAppointments;
        if (appointmentIdTextField.getText() == null) {
            Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer Name Must Be Selected");
                alert.show();
                return;
            }
            int customerId = selectedCustomer.getCustomerId();
            LocalDate localDate = datePickerComboBox.getValue();
            if (localDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Appointment Date Must be Selected.");
                alert.show();
                return;
            }
            String localTimeStartString = startTimeComboBox.getSelectionModel().getSelectedItem();
            if (localTimeStartString == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Start Time Must be Selected.");
                alert.show();
                return;
            }
            String localTimeEndString = endTimeComboBox.getSelectionModel().getSelectedItem();
            if (localTimeEndString == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An End Time Must be Selected.");
                alert.show();
                return;
            }
            LocalTime localTimeStart = LocalTime.from(timeFormatter.parse(localTimeStartString));
            LocalDateTime localDateTimeStart = LocalDateTime.of(localDate, localTimeStart);
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(localDateTimeStart, localZoneId);
            ZonedDateTime businessHoursStart = ZonedDateTime.of(localDate, LocalTime.of(8, 00), ZoneId.of("US/Eastern"));
            if((zonedDateTimeStart.isBefore(businessHoursStart)) || (zonedDateTimeStart.getDayOfWeek() == DayOfWeek.SATURDAY) || (zonedDateTimeStart.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Outside Business Hours");
                alert.setContentText("Please Select a Start Time that is After 8:00am EST, M-F");
                alert.show();
                return;
            }
            Timestamp timeStampStart = Timestamp.valueOf(localDateTimeStart);
            LocalTime localTimeEnd = LocalTime.from(timeFormatter.parse(localTimeEndString));
            LocalDateTime localDateTimeEnd = LocalDateTime.of(localDate, localTimeEnd);
            ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(localDateTimeEnd, localZoneId);
            ZonedDateTime businessHoursEnd = ZonedDateTime.of(localDate, LocalTime.of(22, 0), ZoneId.of("US/Eastern"));
            if(zonedDateTimeEnd.isAfter(businessHoursEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Outside Business Hours");
                alert.setContentText("Please Select an End Time that is Before 10:00pm EST");
                alert.show();
                return;
            }
            Timestamp timeStampEnd = Timestamp.valueOf(localDateTimeEnd);
            localCustomerAppointments = DBAppointments.getAppointmentsByCustomerId(customerId);
            for(Appointment ap : localCustomerAppointments) {
                if((ap.getStart().isBefore(localDateTimeStart)) && (ap.getEnd().isAfter(localDateTimeStart))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("The Customer Already has An Appointment Scheduled in this Window.");
                    alert.show();
                    return;
                }
                if(ap.getStart().equals(localDateTimeStart)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("The Customer Already has An Appointment Scheduled in this Window.");
                    alert.show();
                    return;
                }
                if((ap.getStart().isAfter(localDateTimeStart)) && (ap.getStart().isBefore(localDateTimeEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("The Customer Already has An Appointment Scheduled in this Window.");
                    alert.show();
                    return;
                }

            }
            String title = titleTextField.getText();
            if (title == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Title Must be Entered.");
                alert.show();
                return;
            }
            String description = descriptionTextField.getText();
            if (description == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Description Must be Entered.");
                alert.show();
                return;
            }
            String location = locationTextField.getText();
            String type = typeComboBox.getSelectionModel().getSelectedItem();
            if (type == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Type Must be Entered.");
                alert.show();
                return;
            }
            Contact contact = contactComboBox.getSelectionModel().getSelectedItem();
            if (contact == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Contact Must be Selected.");
                alert.show();
                return;
            }
            int contactId = contact.getId();
            int userId = Integer.parseInt(userIdTextField.getText());

            DBAppointments.insertAppointment(title, description, location, type, timeStampStart, timeStampEnd, customerId, contactId, userId);

            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1050, 450);
            stage.setScene(scene);
            stage.setTitle("Appointment Screen");
        }
        else {
            int appointmentId = Integer.parseInt(appointmentIdTextField.getText());
            Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer Name Must Be Selected");
                alert.show();
                return;
            }
            int customerId = selectedCustomer.getCustomerId();
            LocalDate localDate = datePickerComboBox.getValue();
            if (localDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Appointment Date Must be Selected.");
                alert.show();
                return;
            }
            String localTimeStartString = startTimeComboBox.getSelectionModel().getSelectedItem();
            String localTimeEndString = endTimeComboBox.getSelectionModel().getSelectedItem();
            if (localTimeStartString == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Start Time Must be Selected.");
                alert.show();
                return;
            }
            if (localTimeEndString == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An End Time Must be Selected.");
                alert.show();
                return;
            }
            LocalTime localTimeStart = LocalTime.from(timeFormatter.parse(localTimeStartString));
            LocalDateTime localDateTimeStart = LocalDateTime.of(localDate, localTimeStart);
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            ZonedDateTime zonedDateTimeStart = ZonedDateTime.of(localDateTimeStart, localZoneId);
            ZonedDateTime businessHoursStart = ZonedDateTime.of(localDate, LocalTime.of(8, 00), ZoneId.of("US/Eastern"));
            if((zonedDateTimeStart.isBefore(businessHoursStart)) || (zonedDateTimeStart.getDayOfWeek() == DayOfWeek.SATURDAY) || (zonedDateTimeStart.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Outside Business Hours");
                alert.setContentText("Please Select a Start Time that is After 8:00am EST, M-F");
                alert.show();
                return;
            }
            Timestamp timeStampStart = Timestamp.valueOf(localDateTimeStart);
            LocalTime localTimeEnd = LocalTime.from(timeFormatter.parse(localTimeEndString));
            LocalDateTime localDateTimeEnd = LocalDateTime.of(localDate, localTimeEnd);
            ZonedDateTime zonedDateTimeEnd = ZonedDateTime.of(localDateTimeEnd, localZoneId);
            ZonedDateTime businessHoursEnd = ZonedDateTime.of(localDate, LocalTime.of(22, 0), ZoneId.of("US/Eastern"));
            if(zonedDateTimeEnd.isAfter(businessHoursEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Outside Business Hours");
                alert.setContentText("Please Select an End Time that is Before 10:00pm EST");
                alert.show();
                return;
            }
            Timestamp timeStampEnd = Timestamp.valueOf(localDateTimeEnd);
            localCustomerAppointments = DBAppointments.getAppointmentsByCustomerId(customerId);
            for(Appointment ap : localCustomerAppointments) {
                if(ap.getAppointmentId() == appointmentId) {
                    //localCustomerAppointments.remove(ap);
                    continue;
                }
                if((ap.getStart().isBefore(localDateTimeStart)) && (ap.getEnd().isAfter(localDateTimeStart))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("The Customer Already has An Appointment Scheduled in this Window.");
                    alert.show();
                    return;
                }
                if(ap.getStart().equals(localDateTimeStart)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("The Customer Already has An Appointment Scheduled in this Window.");
                    alert.show();
                    return;
                }
                if((ap.getStart().isAfter(localDateTimeStart)) && (ap.getStart().isBefore(localDateTimeEnd))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflicting Appointment");
                    alert.setContentText("The Customer Already has An Appointment Scheduled in this Window.");
                    alert.show();
                    return;
                }
            }
            String title = titleTextField.getText();
            if (title == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Title Must be Entered.");
                alert.show();
                return;
            }
            String description = descriptionTextField.getText();
            if (description == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Description Must be Entered.");
                alert.show();
                return;
            }
            String location = locationTextField.getText();
            String type = typeComboBox.getSelectionModel().getSelectedItem();
            if (type == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Type Must be Entered.");
                alert.show();
                return;
            }
            Contact contact = contactComboBox.getSelectionModel().getSelectedItem();
            if (contact == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("An Contact Must be Selected.");
                alert.show();
                return;
            }
            int contactId = contact.getId();
            int userId = Integer.parseInt(userIdTextField.getText());

            DBAppointments.updateAppointment(title, description, location, type, timeStampStart, timeStampEnd, customerId, contactId, userId, appointmentId);

            tempAppointment = null;

            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 1050, 450);
            stage.setScene(scene);
            stage.setTitle("Appointment Screen");
        }
    }

    public void onCustomerSelect(ActionEvent actionEvent) {
        Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
        locationTextField.setText(selectedCustomer.getDivision().getDivisionName());
    }

    public void onStartTime(ActionEvent actionEvent) {
        String startTimeString = startTimeComboBox.getSelectionModel().getSelectedItem();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime localStartTime = LocalTime.from(timeFormatter.parse(startTimeString));
        ObservableList<String> endTimes = FXCollections.observableArrayList();
        int i = 0;
        while(localStartTime.isBefore(LocalTime.of(22,0))) {
            localStartTime = localStartTime.plusMinutes(15);
            String t1 = localStartTime.format(DateTimeFormatter.ofPattern("h:mm a"));
            endTimes.add(t1);
        }
        endTimeComboBox.setItems(endTimes);
        endTimeComboBox.setValue(null);
        endTimeComboBox.setDisable(false);

    }

    public void onStartTime() {
        String start = startTimeComboBox.getSelectionModel().getSelectedItem();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime lt = LocalTime.from(timeFormatter.parse(start));

        ObservableList<String> endTimes = FXCollections.observableArrayList();
        while(lt.isBefore(LocalTime.of(22,0))) {
            lt = lt.plusMinutes(15);
            String t1 = lt.format(DateTimeFormatter.ofPattern("h:mm a"));
            endTimes.add(t1);
        }
        endTimeComboBox.setItems(endTimes);
        endTimeComboBox.setValue(null);
        endTimeComboBox.setDisable(false);
    }

}
