package controller;

import database.DBCountries;
import database.DBCustomers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** This is the controller class for the MainScreen.fxml document and not meant to be instantiated.
 *
 * @author Greg Farrell
 * @version 1.0
 * */
public class MainScreenController implements Initializable {
    public TextField userIdTextField;
    public TextField passwordTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(2);
        try {
            DBCustomers.selectCustomers();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /*
    public void showCountries() {
        // List variable to populate
        ObservableList<Country> countryList;

        // Return the list of countries from the database
        countryList = DBCountries.getAllCountries();

        // Display all countries in console
        for (Country c : countryList) {
            System.out.println("Country ID: " + c.getId() + " Country Name: " + c.getName());
        }
    }
    */

    public void loginValidation(ActionEvent actionEvent) throws IOException {
        String enteredName = userIdTextField.getText();
        String enteredPassword = passwordTextField.getText();

        Parent root = FXMLLoader.load(getClass().getResource("/view/UserHomeScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.setTitle("User Home Screen");
    }
}
