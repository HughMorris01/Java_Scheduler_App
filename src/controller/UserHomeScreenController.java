package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UserHomeScreenController {
    /** This method is an event handler on the Customer.
     * When clicked, the button loads and redirects the program to the Customer Screen FXML document.
     * @param actionEvent Passed from the On Action event listener in the User Home Screen FXML document
     * @throws IOException Exception gets thrown if load() cannot locate the FXML file
     * */
    public void toCustomerScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Customer Screen");
        stage.show();
    }

    public void toAppointmentScreen(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.setTitle("Appointment Screen");
            stage.show();
    }
}
