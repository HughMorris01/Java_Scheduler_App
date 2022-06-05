package controller;

import database.DBAppointments;
import database.DBContacts;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the controller class for the Appointments screen.
*/
public class AppointmentScreenController implements Initializable {
    public ToggleGroup scheduleToggle;
    public ComboBox<Contact> contactComboBox;
    public TableView<Appointment> appointmentsTable;
    public TableColumn<Appointment, Integer> appointmentIdCol;
    public TableColumn <Appointment, String> startDateCol;
    public TableColumn <Appointment, LocalTime> startTimeCol;
    public TableColumn <Appointment, LocalTime> endTimeCol;
    public TableColumn <Appointment, Integer> titleCol;
    public TableColumn <Appointment, String> descriptionCol;
    public TableColumn <Appointment, String> locationCol;
    public TableColumn <Appointment, String> typeCol;
    public TableColumn <Appointment, Integer>customerIdCol;
    public TableColumn <Appointment, String> contactNameCol;
    public TableColumn <Appointment, Integer> userIdCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDateString"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTimeString"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTimeString"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointmentsTable.setItems(DBAppointments.getAllAppointments());

        contactComboBox.setItems(DBContacts.getAllContacts());
    }

    public void toUserHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UserHomeScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.setTitle("User Home Screen");
    }

    public void onNewAppointment(ActionEvent actionEvent) throws IOException {
        AddModifyAppointmentScreenController.isNewAppointment = true;

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddModifyAppointment.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("New Appointment");
    }

    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {

        if (appointmentsTable.getSelectionModel().getSelectedItem() != null ) {
            AddModifyAppointmentScreenController.isNewAppointment = false;
            AddModifyAppointmentScreenController.tempAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(getClass().getResource("/view/AddModifyAppointment.fxml"));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.setTitle("Update Existing Appointment");
        }
        else  {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Selection Made");
            alert1.setContentText("Please Select an Appointment to Update.");
            alert1.show();
        }
    }

    public void onDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null ) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Permanently Delete Selected Appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent()  && result.get() == ButtonType.OK) {

                int appointmentId = selectedAppointment.getAppointmentId();
                String type = selectedAppointment.getType();
                DBAppointments.deleteAppointment(appointmentId);
                appointmentsTable.setItems(DBAppointments.getAllAppointments());

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Deletion Confirmation");
                alert1.setContentText("The Selected Appointment ID#" + appointmentId + " of Type " + type +  " Has Been Deleted");
                alert1.show();
            }
        }
        else  {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("No Selection Made");
            alert1.setContentText("Please Select an Appointment to Delete.");
            alert1.show();
        }
    }

    public void loadMonthlySchedule(ActionEvent actionEvent) {
        appointmentsTable.setItems(DBAppointments.getAppointmentsByMonth());
    }

    public void loadWeeklySchedule(ActionEvent actionEvent) {
        appointmentsTable.setItems(DBAppointments.getAppointmentsByWeek());
    }

    public void loadAllAppointments(ActionEvent actionEvent) {
        appointmentsTable.setItems(DBAppointments.getAllAppointments());
    }

    public void onSelectContact(ActionEvent actionEvent) {
        Contact selectedContact = contactComboBox.getSelectionModel().getSelectedItem();
        appointmentsTable.setItems(DBAppointments.getAppointmentsByContact(selectedContact));
    }
}
