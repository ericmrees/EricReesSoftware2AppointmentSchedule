package Main;

import Utility.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

/**
 * @author Eric Rees
 */
public class AppointmentScheduleApp extends Application {

    /**
     * Method to load the LoginScreen to start the application.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method opens the database connection, calls launch, and closes the database connection when the application closes.
     * @param args
     */
    public static void main(String[] args) {
        // Get connection to database
        Connection conn = DatabaseConnection.getConnection();
        // Calls launch
        launch(args);
        // Close connection to database
        DatabaseConnection.closeConnection();
    }
}