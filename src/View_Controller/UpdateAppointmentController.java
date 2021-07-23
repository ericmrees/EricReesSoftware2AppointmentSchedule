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
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Class Controller for the UpdateAppointment screen with methods to input data and Update appointments in the database.
 */
public class UpdateAppointmentController {
    @FXML
    private TextField uAAppointmentIDTxt;

    @FXML
    private TextField uATitleTxt;

    @FXML
    private TextField uADescriptionTxt;

    @FXML
    private TextField uALocationTxt;

    @FXML
    private ComboBox<String> uATypeCBox;

    @FXML
    private DatePicker uADatePicker;

    @FXML
    private ComboBox<LocalTime> uAEndCBox;

    @FXML
    private ComboBox<LocalTime> uAStartCBox;

    @FXML
    private ComboBox<User> uAUserIDCBox;

    @FXML
    private ComboBox<Integer> uACustomerIDCBox;

    @FXML
    private ComboBox<Contact> uAContactNameCBox;

    @FXML
    private Button saveUpdateAppointmentBtn;

    @FXML
    private Button cancelUpdateAppointmentBtn;

    private CommonController commonController = new CommonController();
    AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    ContactDAO contactDAO = new ContactDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();
    UserDAO userDAO = new UserDAOImpl();

    /**
     * Cancels updating an appointment, clears and cancels any changes made in the fields, and takes you back to the AppointmentSchedule screen.
     * @param event Cancel Button clicked
     * @throws IOException
     */
    @FXML
    public void handleCancelUpdateAppointmentBtn(ActionEvent event) throws IOException {
        commonController.handleCancelAppointment(event);
    }

    /**
     * Updates an appointment in the database and validates all of the input data used to update the appointment.
     * Displays the original appointment data in all the fields once the UpdateAppointment screen is opened.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void handleSaveUpdateAppointment(ActionEvent event) throws IOException, SQLException {
        // Displays error message if any fields are empty
        if (uATitleTxt.getText().isEmpty() || uADescriptionTxt.getText().isEmpty() || uALocationTxt.getText().isEmpty()
                || uAContactNameCBox.getValue() == null || uATypeCBox.getValue() == null || uADatePicker.getValue() == null
                || uAStartCBox.getValue() == null || uAEndCBox.getValue() == null || uACustomerIDCBox.getValue() == null
                || uAUserIDCBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Appointment Error");
            alert.setHeaderText("One or more fields are empty!");
            alert.setContentText("Please enter valid data for all fields to update appointment!");
            alert.showAndWait();
        } else if (uAStartCBox.getSelectionModel().getSelectedItem().isBefore(LocalTime.from(TimeConversion.getLocalStartTime()))
                   || uAEndCBox.getSelectionModel().getSelectedItem().isAfter(LocalTime.from(TimeConversion.getLocalEndTime()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Outside Open Business Hours");
            alert.setHeaderText("Start time and/or end time are outside of open business hours!");
            alert.setContentText("Please enter a valid start time and/or end time within open business hours from 8:00-22:00EST!");
            alert.showAndWait();
        } else {
            //Taking selected date and time, converting to LocalDateTime and then converting to Timestamp for database entry***
            LocalDate startDate = uADatePicker.getValue();
            LocalTime startTime = uAStartCBox.getValue();
            LocalTime endTime = uAEndCBox.getValue();
            LocalDateTime start = LocalDateTime.of(startDate, startTime);
            LocalDateTime end = LocalDateTime.of(startDate, endTime);
            Timestamp startTimestamp = Timestamp.valueOf(start);
            Timestamp endTimestamp = Timestamp.valueOf(end);
            Appointment overlappingAppointment = Appointment.conflictInSchedule(uACustomerIDCBox.getSelectionModel().getSelectedItem(), start, end);
            // Displays error message if a customer has any appointments that overlap
            if (overlappingAppointment != null && overlappingAppointment.getAppointmentId() != Integer.parseInt(uAAppointmentIDTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Conflict in Schedule");
                alert.setHeaderText("The customer already has a scheduled appointment at the selected time. Please select a different time!");
                alert.setContentText("Appointment ID: " + overlappingAppointment.getAppointmentId() + "\n" +
                        "Appointment Date: " + overlappingAppointment.getStartDateTime().toLocalDate() + "\n" +
                        "Appointment Start Time: " + overlappingAppointment.getStartDateTime().toLocalTime() + "\n" +
                        "Appointment End Time: " + overlappingAppointment.getEndDateTime().toLocalTime());
                alert.showAndWait();
                // Displays error message if Date and/or Time are/is outside of business hours: 8:00am-10:00pm EST
            } else if (uAStartCBox.getSelectionModel().getSelectedItem().isBefore(LocalTime.from(TimeConversion.getLocalStartTime()))
                        || uAEndCBox.getSelectionModel().getSelectedItem().isAfter(LocalTime.from(TimeConversion.getLocalEndTime()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Outside Open Business Hours");
                    alert.setHeaderText("Start time and/or end time are outside of open business hours!");
                    alert.setContentText("Please enter a valid start time and/or end time within open business hours from 8:00-22:00EST!");
                    alert.showAndWait();
                // Displays error message if Start and End are not in the future
            } else if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Date and Time Error");
                alert.setHeaderText("Selected date and time is before current date and time!");
                alert.setContentText("Please select a date and time in the future to update the appointment!");
                alert.showAndWait();
            }
            else {
                // Everything is valid, stores and updates input data in variables
                int appointmentId = Integer.parseInt(uAAppointmentIDTxt.getText());
                String title = uATitleTxt.getText();
                String description = uADescriptionTxt.getText();
                String location = uALocationTxt.getText();
                int contactId = uAContactNameCBox.getSelectionModel().getSelectedItem().getContactId();
                String type = uATypeCBox.getValue();
                Integer customerId = uACustomerIDCBox.getValue();
                int userId = uAUserIDCBox.getValue().getUserId();
                // updateAppointment updates the appointment to the database and returns to AppointmentSchedule
                appointmentDAO.updateAppointment(appointmentId, title, description, location, type, startTimestamp, endTimestamp, customerId, userId, contactId);
                commonController.handleAppointmentSchedule(event);
            }
        }
    }

    /**
     * Automatically sets appointment attributes in the UpdateAppointment screen from the appointment selected from the table in the AppointmentSchedule screen.
     * @param appointment selected appointment from the table in the AppointmentSchedule screen
     */
    public void setAppointment(Appointment appointment) {
        // Converts startDateTime and endDateTime to startDate, startTime, and endTime to display in the given fields
        LocalDate startDate = appointment.getStartDateTime().toLocalDate();
        LocalTime startTime = appointment.getStartDateTime().toLocalTime();
        LocalTime endTime = appointment.getEndDateTime().toLocalTime();
        Contact selectContact = null;
        int selectCustomerId = 0;
        User selectUser = null;
        // Sets data in ComboBoxes for contacts, customerIds, users, and types
        ObservableList<Contact> allContacts = contactDAO.getAllContacts();
        ObservableList<Customer> allCustomers = customerDAO.getAllCustomers();
        ObservableList<User> allUsers = userDAO.getAllUsers();
        ObservableList<Integer> allCustomerIds = FXCollections.observableArrayList();
        ObservableList<String> types = Appointment.getAllTypes();
        String selectType = null;
        // Sets business hours from 8:00AM-10:00PM EST
        LocalDateTime startHours = TimeConversion.getLocalStartTime();
        LocalDateTime endHours = TimeConversion.getLocalEndTime();
        // Sets appointment time selection for every 30 minutes between start and end time
        while (startHours.isBefore(endHours.plusSeconds(1))){
            uAStartCBox.getItems().add(LocalTime.from(startHours));
            startHours = startHours.plusMinutes(30);
        }
        // Automatically sets the customerId to appointmentId for the appointment that was selected to update
        for (Customer customer : allCustomers) {
            if (customer.getCustomerId() == appointment.getCustomerId()){
                selectCustomerId = customer.getCustomerId();
            }
        }
        // Automatically sets the user to user for the appointment that was selected to update
        for (User user : allUsers) {
            if (user.getUserId() == appointment.getUserId()){
                selectUser = user;
            }
        }
        // Automatically sets the contact to contact for the appointment that was selected to update
        for (Contact contact : allContacts)
            if (contact.getContactId() == appointment.getContactId()) {
                selectContact = contact;
            }
        // Populates a list of all customer ids
        for (Customer customer : allCustomers){
            if (customer.getCustomerId() != 0){
                int customerId = customer.getCustomerId();
                allCustomerIds.add(customerId);
            }
        }
        // Automatically sets the type to type for the appointment that was selected to update
        for (String string : types){
            if (string.equals(appointment.getType())){
                selectType = string;
            }
        }
        // All appointment attributes taken to automatically populate all fields in the UpdateAppointment screen for modification
        uAAppointmentIDTxt.setText(Integer.toString(appointment.getAppointmentId()));
        uATitleTxt.setText(appointment.getTitle());
        uADescriptionTxt.setText(appointment.getDescription());
        uALocationTxt.setText(appointment.getLocation());
        uATypeCBox.setValue(selectType);
        uATypeCBox.setItems(types);
        uAContactNameCBox.setItems(allContacts);
        uAContactNameCBox.setValue(selectContact);
        uADatePicker.setValue(startDate);
        uAStartCBox.setValue(startTime);
        uAEndCBox.setValue(endTime);
        uACustomerIDCBox.setItems(allCustomerIds);
        uACustomerIDCBox.setValue(selectCustomerId);
        uAUserIDCBox.setItems(allUsers);
        uAUserIDCBox.setValue(selectUser);
    }

    /**
     * Method auto-populates the endTime ComboBox to 30 minutes after the selection of startTime ComboBox.
     * @param event startTime ComboBox selected
     */
    public void handleStartSelect(ActionEvent event) {
        LocalDate selectDate = uADatePicker.getValue();
        LocalTime localEndTime = TimeConversion.getLocalEndTime().toLocalTime();
        LocalDateTime localEnd = LocalDateTime.of(selectDate, localEndTime);
        LocalTime selectStart = uAStartCBox.getSelectionModel().getSelectedItem();
        LocalDateTime selectStartDate = LocalDateTime.of(selectDate, selectStart);
        uAEndCBox.setValue(selectStart.plusMinutes(30));
    }
}