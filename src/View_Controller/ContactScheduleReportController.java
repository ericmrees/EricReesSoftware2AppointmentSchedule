package View_Controller;

import DataAccessObject.AppointmentDAO;
import DataAccessObject.AppointmentDAOImpl;
import DataAccessObject.ContactDAO;
import DataAccessObject.ContactDAOImpl;
import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Class Controller for the ContactScheduleReport screen with methods to display reports of appointments by each contact.
 */
public class ContactScheduleReportController implements Initializable {
    @FXML
    private TableView<Appointment> contactScheduleReportTableView;

    @FXML
    private TableColumn<Appointment, Integer> cSRAppointmentIDCol;

    @FXML
    private TableColumn<Appointment, String> cSRTitleCol;

    @FXML
    private TableColumn<Appointment, String> cSRTypeCol;

    @FXML
    private TableColumn<Appointment, String> cSRDescriptionCol;

    @FXML
    private TableColumn<Appointment, String> cSRStartCol;

    @FXML
    private TableColumn<Appointment, String> cSREndCol;

    @FXML
    private TableColumn<Appointment, Integer> cSRCustomerIDCol;

    @FXML
    private Button backAppointmentScheduleBtn;

    @FXML
    private ComboBox<Contact> cSRContactNameCBox;

    private CommonController commonController = new CommonController();
    ContactDAO contactDAO = new ContactDAOImpl();
    AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    /**
     * Takes you to the AppointmentSchedule screen.
     * @param event Back Button clicked
     * @throws IOException
     */
    @FXML
    public void handleBackAppointmentSchedule(ActionEvent event) throws IOException {
        commonController.handleAppointmentSchedule(event);
    }

    /**
     * Method displays the contact's appointments in the table according to the selection in the Contact ComboBox.
     * This Lambda Expression takes away the need to use an extra ObservableList, hence less code and better readability.
     * If the appointment belongs in the filtered list and the appointment's contactId equals the selected contact's contactId,
     * it returns true and populates the appropriate appointments and attributes into the table according to the contact selection, otherwise it returns false.
     * @param event Select Contact ComboBox contact selected
     */
    @FXML
    public void handleSelectContact(ActionEvent event) {
        Contact selectContact = cSRContactNameCBox.getSelectionModel().getSelectedItem();
        // Lambda Expression
        ObservableList<Appointment> contactAppointments = appointmentDAO.getAllAppointments().filtered(appointment -> {
            if (appointment.getContactId() == selectContact.getContactId()) {
                return true;
            }
            return false;
        });
        contactScheduleReportTableView.setItems(contactAppointments);
        cSRAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        cSRTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        cSRDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        cSRTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        cSRStartCol.setCellValueFactory(new PropertyValueFactory<>("convertedStartDateTime"));  // Calls converted version of getter
        cSREndCol.setCellValueFactory(new PropertyValueFactory<>("convertedEndDateTime"));  // Calls converted version of getter
        cSRCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * Called when ContactScheduleReport screen is loaded.
     * Populates the Contact Name ComboBox with a list and displays the appointment data for whichever contact name is selected.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cSRContactNameCBox.setItems(contactDAO.getAllContacts());
        ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
        ObservableList<Appointment> futureAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointments) {
            if (appointment.getEndDateTime().isAfter(LocalDateTime.now())) {
                futureAppointments.add(appointment);
            }
        }
    }
}
