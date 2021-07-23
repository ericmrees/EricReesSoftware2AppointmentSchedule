package View_Controller;

import DataAccessObject.*;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import Utility.TimeConversion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Class Controller for the AddAppointment screen with methods to input data and Add new appointments to the database.
 */
public class AddAppointmentController implements Initializable {
    @FXML
    private TextField aAAppointmentIDTxt;

    @FXML
    private TextField aATitleTxt;

    @FXML
    private TextField aADescriptionTxt;

    @FXML
    private TextField aALocationTxt;

    @FXML
    private ComboBox<String> aATypeCBox;

    @FXML
    private ComboBox<LocalTime> aAEndCBox;

    @FXML
    private ComboBox<LocalTime> aAStartCBox;

    @FXML
    private DatePicker aADatePicker;

    @FXML
    private ComboBox<User> aAUserIDCBox;

    @FXML
    private ComboBox<Integer> aACustomerIDCBox;

    @FXML
    private ComboBox<Contact> aAContactNameCBox;

    @FXML
    private Button saveAddAppointmentBtn;

    @FXML
    private Button cancelAddAppointmentBtn;

    private CommonController commonController = new CommonController();
    AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();
    ContactDAO contactDAO = new ContactDAOImpl();
    UserDAO userDAO = new UserDAOImpl();

    /**
     * Cancels adding a new appointment, clears all data from fields, and takes you back to the AppointmentSchedule screen.
     * @param event Cancel Button clicked
     * @throws IOException
     */
    @FXML
    public void handleCancelAddAppointment(ActionEvent event) throws IOException {
        commonController.handleCancelAppointment(event);
    }

    /**
     * Adds a new appointment to the database and validates all of the input data used to create the new appointment.
     * @param event Save Button clicked
     * @throws IOException
     */
    @FXML
    public void handleSaveAddAppointment(ActionEvent event) throws IOException {
        // Displays error message if any fields are empty
        if (aATitleTxt.getText().isEmpty() || aADescriptionTxt.getText().isEmpty() || aALocationTxt.getText().isEmpty()
                || aAContactNameCBox.getValue() == null || aATypeCBox.getValue() == null || aADatePicker.getValue() == null
                || aAStartCBox.getValue() == null || aAEndCBox.getValue() == null || aACustomerIDCBox.getValue() == null
                || aAUserIDCBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Appointment Error");
            alert.setHeaderText("One or more fields are empty!");
            alert.setContentText("Please enter valid data for all fields to add appointment!");
            alert.showAndWait();
        } else {
            LocalDate selectStartDate = aADatePicker.getValue();
            LocalTime selectStartTime = aAStartCBox.getValue();
            LocalTime selectEndTime = aAEndCBox.getValue();
            LocalDateTime startDateTime = LocalDateTime.of(selectStartDate, selectStartTime);
            LocalDateTime endDateTime = LocalDateTime.of(selectStartDate, selectEndTime);
            Appointment overlappingAppointment = null;
            try {
                overlappingAppointment = Appointment.conflictInSchedule(aACustomerIDCBox.getSelectionModel().getSelectedItem(), startDateTime, endDateTime);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            // Displays error message if a customer has any appointments that overlap
            if (overlappingAppointment != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Conflict In Schedule");
                alert.setHeaderText("The customer already has a scheduled appointment at the selected time. Please select a different time!");
                alert.setContentText("Appointment ID: " + overlappingAppointment.getAppointmentId() + "\n" +
                        "Appointment Date: " + overlappingAppointment.getStartDateTime().toLocalDate() + "\n" +
                        "Appointment Start Time: " + overlappingAppointment.getStartDateTime().toLocalTime() + "\n" +
                        "Appointment End Time: " + overlappingAppointment.getEndDateTime().toLocalTime());
                alert.showAndWait();
                aAStartCBox.setValue(overlappingAppointment.getEndDateTime().toLocalTime());
            }
            // Displays error message if Start and End are not in the future
            else if (startDateTime.isBefore(LocalDateTime.now()) || endDateTime.isBefore(LocalDateTime.now())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Date and Time Error");
                alert.setHeaderText("Selected date and time is before current date and time!");
                alert.setContentText("Please select a date and time in the future to schedule the appointment!");
                alert.showAndWait();
            }
            // Displays error message if Date and/or Time are/is outside of business hours: 8:00am-10:00pm EST
            else if (aAStartCBox.getSelectionModel().getSelectedItem().isBefore(LocalTime.from(TimeConversion.getLocalStartTime()))
                    || aAEndCBox.getSelectionModel().getSelectedItem().isAfter(LocalTime.from(TimeConversion.getLocalEndTime()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Outside Open Business Hours");
                alert.setHeaderText("Start time and/or end time are outside of open business hours!");
                alert.setContentText("Please enter a valid start time and/or end time within open business hours from 8:00-22:00EST!");
                alert.showAndWait();
            }
            else {
                // Everything is valid, stores input data in variables
                String title = aATitleTxt.getText();
                String description = aADescriptionTxt.getText();
                String location = aALocationTxt.getText();
                Contact contact = aAContactNameCBox.getSelectionModel().getSelectedItem();
                int contactId = contact.getContactId();
                String type = aATypeCBox.getValue();
                Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
                Timestamp endTimestamp = Timestamp.valueOf(endDateTime);
                int customerId = aACustomerIDCBox.getSelectionModel().getSelectedItem();
                int userId = aAUserIDCBox.getSelectionModel().getSelectedItem().getUserId();
                // addAppointment adds new appointment to the database and returns to AppointmentSchedule
                appointmentDAO.addAppointment(title, description, location, type, startTimestamp, endTimestamp, customerId, userId, contactId);
                commonController.handleAppointmentSchedule(event);
            }
        }
    }

    /**
     * Method auto-populates the endTime ComboBox to 30 minutes after the selection of startTime ComboBox.
     * @param event startTime ComboBox selected
     */
    public void handleStartSelect(ActionEvent event) {
        LocalDate selectDate = aADatePicker.getValue();
        LocalTime localEndTime = TimeConversion.getLocalEndTime().toLocalTime();
        LocalDateTime localEnd = LocalDateTime.of(selectDate, localEndTime);
        LocalTime selectStart = aAStartCBox.getSelectionModel().getSelectedItem();
        LocalDateTime selectStartDate = LocalDateTime.of(selectDate, selectStart);
        aAEndCBox.setValue(selectStart.plusMinutes(30));
    }

    /**
     * Called when AddAppointment screen is loaded.
     * Populates lists in ComboBoxes and sets DatePicker to the current date.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Loads ObservableLists into ComboBoxes
        ObservableList<Customer> allCustomers = customerDAO.getAllCustomers();
        ObservableList<Integer> allCustomerIds = FXCollections.observableArrayList();
        // Gets customerId's to display in customerId ComboBox
        for (Customer customer : allCustomers){
            if (customer.getCustomerId() != 0){
                int customerId = customer.getCustomerId();
                allCustomerIds.add(customerId);
            }
        }
        // Sets data in ComboBoxes for contacts, customerIds, users, and types
        aAContactNameCBox.setItems(contactDAO.getAllContacts());
        aACustomerIDCBox.setItems(allCustomerIds);
        aAUserIDCBox.setItems(userDAO.getAllUsers());
        aATypeCBox.setItems(Appointment.getAllTypes());
        LocalDateTime localStartDateTime = TimeConversion.getLocalStartTime();
        LocalDateTime localEndDateTime = TimeConversion.getLocalEndTime();
        while (localStartDateTime.isBefore(localEndDateTime.plusSeconds(1))){
            aAStartCBox.getItems().add(LocalTime.from(localStartDateTime));
            localStartDateTime = localStartDateTime.plusMinutes(30);
        }
        // Sets DatePicker value to the current date
        aADatePicker.setValue(LocalDate.now());
    }
}