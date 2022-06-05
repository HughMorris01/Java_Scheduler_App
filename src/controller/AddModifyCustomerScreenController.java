package controller;

import database.DBCountries;
import database.DBCustomers;
import database.DBFirst_Level_Divisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddModifyCustomerScreenController implements Initializable {
    /** Static Customer member used to receive a Customer object passed from the Customers table */
    public static Customer tempCustomer = null;
    /** Static int member used to store the static Customer member's index in the static observable list in the DBCustomers class */
    public static int tempCustomerIndex;
    /** Static boolean used to toggle the heading label on the screen */
    public static boolean labelBoolean = false;
    /** Heading label that adjusts depending on if it is a new or existing customer being entered */
    public Label screenLabel;
    public TextField customerIdTextField;
    public TextField customerNameTextField;
    public TextField addressTextField;
    public TextField postalCodeTextField;
    public TextField phoneNumberTextField;
    public ComboBox<Country> countryComboBox;
    public ComboBox<Division> divisionComboBox;
    public ObservableList<Country> localCountryList;
    public Button submitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!labelBoolean) {
            screenLabel.setText("New Customer");
        } else {
            screenLabel.setText("Modify Customer");
        }
        if (tempCustomer != null) {
            customerIdTextField.setText(String.valueOf(tempCustomer.getCustomerId()));
            customerNameTextField.setText(tempCustomer.getCustomerName());
            addressTextField.setText(tempCustomer.getAddress());
            postalCodeTextField.setText(tempCustomer.getPostalCode());
            phoneNumberTextField.setText(tempCustomer.getPhone());
            countryComboBox.setValue(tempCustomer.getDivision().getCountry());
            divisionComboBox.setValue(tempCustomer.getDivision());
            divisionComboBox.setDisable(false);
        }

        localCountryList = DBCountries.getAllCountries();
        countryComboBox.setItems(localCountryList);


        if (countryComboBox.getSelectionModel().getSelectedItem() != null) {
            Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();

            if (selectedCountry.getCountryId() == 1) {
            DBFirst_Level_Divisions.getUSDivisions();
            divisionComboBox.setItems(DBFirst_Level_Divisions.uSDivisionsList);
            divisionComboBox.setDisable(false);
            }
            else if (selectedCountry.getCountryId() == 2) {
            DBFirst_Level_Divisions.getUKDivisions();
            divisionComboBox.setItems(DBFirst_Level_Divisions.uKDivisionsList);
            divisionComboBox.setDisable(false);
            }
            else {
            DBFirst_Level_Divisions.getCanadaDivisions();
            divisionComboBox.setItems(DBFirst_Level_Divisions.canadaDivisionsList);
            divisionComboBox.setDisable(false);
            }
        }
    }


    public void toCustomerScreen(ActionEvent actionEvent) throws IOException {
        tempCustomer = null;

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        Scene scene = new Scene(root, 600, 450);
        stage.setScene(scene);
        stage.setTitle("Customer Screen");
        stage.show();
    }

    public void onCountrySelect(ActionEvent actionEvent) {
            Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
            if (selectedCountry.getCountryId() == 1) {
                DBFirst_Level_Divisions.getUSDivisions();
                divisionComboBox.setItems(DBFirst_Level_Divisions.uSDivisionsList);
                divisionComboBox.setDisable(false);
                divisionComboBox.setValue(null);
            }
            else if (selectedCountry.getCountryId() == 2) {
                DBFirst_Level_Divisions.getUKDivisions();
                divisionComboBox.setItems(DBFirst_Level_Divisions.uKDivisionsList);
                divisionComboBox.setDisable(false);
                divisionComboBox.setValue(null);
            }
            else {
                DBFirst_Level_Divisions.getCanadaDivisions();
                divisionComboBox.setItems(DBFirst_Level_Divisions.canadaDivisionsList);
                divisionComboBox.setDisable(false);
                divisionComboBox.setValue(null);
            }
        }

    public void onSubmitButtonClick(ActionEvent actionEvent) throws SQLException, IOException {

        if (customerIdTextField.getText() == "") {
            String tempText = customerNameTextField.getText();
            if (tempText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blank Field");
                alert.setContentText("A Customer Name Must Be Entered");
                alert.show();
                return;
            }
            String customerName = customerNameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phone = phoneNumberTextField.getText();
            Division division = divisionComboBox.getSelectionModel().getSelectedItem();
            int divisionId = division.getDivisionId();

            DBCustomers.insertCustomer(customerName, address, postalCode, phone, divisionId);

            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
            Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
            Scene scene = new Scene(root, 600, 450);
            stage.setScene(scene);
            stage.setTitle("Customer Screen");
            stage.show();
        }
        else {
           int customerId = Integer.parseInt(customerIdTextField.getText());
           String customerName = customerNameTextField.getText();
           String address = addressTextField.getText();
           String postalCode = postalCodeTextField.getText();
           String phone = phoneNumberTextField.getText();
           Division division = divisionComboBox.getSelectionModel().getSelectedItem();
           int divisionId = division.getDivisionId();

           DBCustomers.updateCustomer(customerId, customerName, address, postalCode, phone, divisionId);

           tempCustomer = null;

           Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
           Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
           Scene scene = new Scene(root, 600, 450);
           stage.setScene(scene);
           stage.setTitle("Customer Screen");
           stage.show();
        }
    }
}
