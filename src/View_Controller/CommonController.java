package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Class Controller for the CommonController to store methods that are frequently used throughout the application.
 */
public class CommonController {

    /**
     * Cancels adding a new appointment, clears all data from text fields, and takes you back to the AppointmentSchedule screen.
     * @param event Cancel Button clicked
     * @throws IOException
     */
    public void handleCancelAppointment(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancellation");
        alert.setHeaderText("Data entered on this screen will not be saved!");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentSchedule.fxml"))));
            stage.show();
        }
    }

    /**
     * Cancels adding a new customer, clear all data from text fields, and takes you back to the CustomerDirectory screen.
     * @param event Cancel Button clicked
     * @throws IOException
     */
    public void handleCancelCustomer(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancellation");
        alert.setHeaderText("Data entered on this screen will not be saved!");
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View_Controller/CustomerDirectory.fxml"))));
            stage.show();
        }
    }

    /**
     * Takes you to the AppointmentSchedule screen.
     * @param event Button clicked
     * @throws IOException
     */
    public void handleAppointmentSchedule(ActionEvent event) throws IOException {
        handleGoToView(event, "/View_Controller/AppointmentSchedule.fxml");
    }

    /**
     * Takes you to the CustomerDirectory screen.
     * @param event Button clicked
     * @throws IOException
     */
    public void handleCustomerDirectory(ActionEvent event) throws IOException {
        handleGoToView(event, "/View_Controller/CustomerDirectory.fxml");
    }

    /**
     * Takes you to different screens depending on the resourceName provided.
     * @param event Button clicked
     * @param resourceName resourceName will provide the location of the screen in which you wish to navigate
     * @throws IOException
     */
    public void handleGoToView(ActionEvent event, String resourceName) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(resourceName))));
        stage.show();
    }
}