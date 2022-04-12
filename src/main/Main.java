package main;

import database.DBCountries;
import database.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * This class creates an appointment management application that interfaces with a MySQL database.
 * The class contains the program's main() method and is not intended to be instantiated.
 * FUTURE ENHANCEMENTS:
 * @author Greg Farrell
 * @version 1.0
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void main(String[] args) {

        JDBC.openConnection();
        DBCountries.checkDateTimeConversion();

        //Locale.setDefault(new Locale("fr"));
        launch(args);

        JDBC.closeConnection();
    }
}
