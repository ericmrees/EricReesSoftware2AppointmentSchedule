package View_Controller;

import DataAccessObject.AppointmentDAOImpl;
import DataAccessObject.UserDAOImpl;
import Model.Appointment;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static Utility.DatabaseConnection.closeConnection;

/**
 * Class Controller for the LoginScreen with methods to successfully login to the application.
 */
public class LoginScreenController implements Initializable {
    @FXML
    private Label appointmentScheduleAppLbl;

    @FXML
    private Button loginBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Label usernameLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField passwordPField;

    @FXML
    private Label userLocationLbl;

    @FXML
    private Label zoneIDLbl;

    private CommonController commonController = new CommonController();
    DataAccessObject.AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    DataAccessObject.UserDAO userDAO = new UserDAOImpl();

    /**
     * Exits the application and closes the database connection.
     * @param event Exit Button clicked
     */
    @FXML
    public void handleExitAppFromLogin(ActionEvent event) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Utility/Lang", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("en")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                // Displays confirmation alert to exit the application in English if the user's computer is set to English
                alert.setTitle("Confirmation");
                alert.setHeaderText("Confirm to exit Appointment Schedule App!");
                alert.setContentText("Are you sure you want to exit?");
                Optional<ButtonType> res = alert.showAndWait();
                if (res.get() == ButtonType.OK) {
                    closeConnection();
                    System.exit(0);
                }
            } else if (Locale.getDefault().getLanguage().equals("fr")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                // Displays confirmation alert to exit the application in French if the user's computer is set to French
                alert.setTitle(rb.getString("Confirmation"));
                alert.setHeaderText(rb.getString("Confirm"));
                alert.setContentText(rb.getString("Ex"));
                Optional<ButtonType> res = alert.showAndWait();
                if (res.get() == ButtonType.OK) {
                    closeConnection();
                    System.exit(0);
                }
            }
        } catch(NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Logs the user into the application providing validation for correct username and password.
     * Tracks user activity, recording all successful and unsuccessful log-in attempts, dates, and timestamps.
     * @param event Log In Button clicked
     * @throws IOException
     */
    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("Utility/Lang", Locale.getDefault());
        if (event.getSource() == loginBtn) {
            String username = usernameTxt.getText();
            String password = passwordPField.getText();
            // Tracks successful user log-in activity
            String loginActivityFile = "login_activity.txt";
            FileWriter fileWriter = new FileWriter(loginActivityFile, true);
            PrintWriter outputLoginActivity = new PrintWriter(fileWriter);
            if (username.equalsIgnoreCase("test") && password.equals("test")) {
                String loginActivity = "Successfully Logged In: " + username + " Date/Time: " + LocalDateTime.now();
                outputLoginActivity.println(loginActivity);
                outputLoginActivity.close();
                commonController.handleAppointmentSchedule(event);
                // Appointment reminder
                ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
                ObservableList<User> allUsers = userDAO.getAllUsers();
                Appointment upcomingAppointment = null;
                User user = null;
                // Username retrieved from login
                for (User activeUser : allUsers) {
                    if (activeUser.getUsername().equals(username)) {
                        user = activeUser;
                    }
                }
                // Checks if the user has an appointment within 15 minutes of logging in
                for (Appointment nextAppointment : allAppointments) {
                    long timeDifference = ChronoUnit.MINUTES.between(LocalDateTime.now(), nextAppointment.getStartDateTime());
                    assert user != null;
                    if (nextAppointment.getUserId() == user.getUserId() && timeDifference > 0 && timeDifference <= 15/*change back to 15*/) {
                        upcomingAppointment = nextAppointment;
                    }
                }
                // Displays a reminder if the user has an appointment within 15 minutes of logging in
                if (upcomingAppointment != null) {
                    LocalDate appointmentDate = upcomingAppointment.getStartDateTime().toLocalDate();
                    LocalTime appointmentTime = upcomingAppointment.getStartDateTime().toLocalTime();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Reminder");
                    alert.setHeaderText("You have an appointment within the next 15 minutes!");
                    alert.setContentText("Appointment ID: " + upcomingAppointment.getAppointmentId() + "\n" +
                                         "Appointment Date: " + appointmentDate + "\n" +
                                         "Appointment Time: " + appointmentTime);
                    alert.showAndWait();
                }
                // Displays a message if the user has no appointments within 15 minutes of logging in
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Reminder");
                    alert.setHeaderText("You have no appointments within the next 15 minutes!");
                    alert.showAndWait();
                }
            } else if (Locale.getDefault().getLanguage().equals("fr")) {
                // Tracks unsuccessful user log-in activity
                String loginActivity = "Unsuccessfully Logged In: " + username + " Date/Time: " + LocalDateTime.now();
                outputLoginActivity.println(loginActivity);
                outputLoginActivity.close();
                // Displays error message in French if username and/or password are incorrect
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("Error"));
                alert.setHeaderText(rb.getString("Invalid"));
                alert.setContentText(rb.getString("Enter"));
                alert.showAndWait();
            } else if (Locale.getDefault().getLanguage().equals("en")) {
                // Tracks unsuccessful user log-in activity
                String loginActivity = "Unsuccessfully Logged In: " + username + " Date/Time: " + LocalDateTime.now();
                outputLoginActivity.println(loginActivity);
                outputLoginActivity.close();
                // Displays error message in English if username and/or password are incorrect
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid username and/or password!");
                alert.setContentText("Please enter a valid username and/or password!");
                alert.showAndWait();
            }
        }
    }

    /**
     * Called when LoginScreen is loaded.
     * Loads the data from Resource Bundle 'Lang' to translate the text into French is the user's computer language is set to French.
     * Loads the zoneId as the default Locale for User Location.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Locale & Language
        ResourceBundle rb = ResourceBundle.getBundle("Utility/Lang", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr")) {
            appointmentScheduleAppLbl.setText(rb.getString("Appointment"));
            usernameLbl.setText(rb.getString("Username"));
            usernameTxt.setPromptText(rb.getString("Username"));
            passwordLbl.setText(rb.getString("Password"));
            passwordPField.setPromptText(rb.getString("Password"));
            loginBtn.setText(rb.getString("Log"));
            exitBtn.setText(rb.getString("Exit"));
            userLocationLbl.setText(rb.getString("Location"));
        }
        zoneIDLbl.setText(ZoneId.systemDefault().toString());
    }
}