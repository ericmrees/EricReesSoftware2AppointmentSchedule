package View_Controller;

import DataAccessObject.*;
import Model.Appointment;
import Model.Customer;
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
 * Class Controller for the CustomerScheduleReport screen with methods to display reports of appointments by each customer.
 */
public class CustomerScheduleReportController implements Initializable {
    @FXML
    private TableView<Appointment> customerScheduleReportTableView;

    @FXML
    private TableColumn<Appointment, Integer> cSRAppointmentIDCol;

    @FXML
    private TableColumn<Appointment, String> cSRTitleCol;

    @FXML
    private TableColumn<Appointment, String> cSRTypeCol;

    @FXML
    private TableColumn<Appointment, String> cSRDescriptionCol;

    @FXML
    private TableColumn<Appointment, String> cSRContactCol;

    @FXML
    private TableColumn<Appointment, String> cSRStartCol;

    @FXML
    private TableColumn<Appointment, String> cSREndCol;

    @FXML
    private TableColumn<Appointment, Integer> cSRCustomerIDCol;

    @FXML
    private Button backAppointmentScheduleBtn;

    @FXML
    private ComboBox<Customer> cSRCustomerNameCBox;

    private CommonController commonController = new CommonController();
    private ObservableList<Appointment> appointmentFilter = FXCollections.observableArrayList();
    CustomerDAO customerDAO = new CustomerDAOImpl();
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
     * Method displays the customer's appointments in the table according to the selection in the Customer ComboBox.
     * This Lambda Expression takes away the need to use an extra ObservableList, hence less code and better readability.
     * If the appointment belongs in the filtered list and the appointment's customerId equals the selected customer's customerId,
     * it returns true and populates the appropriate appointments and attributes into the table according to the customer selection, otherwise it returns false.
     * @param event Select Customer ComboBox customer selected
     */
    @FXML
    public void handleSelectCustomer(ActionEvent event) {
        Customer selectCustomer = cSRCustomerNameCBox.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> customerAppointments = appointmentDAO.getAllAppointments().filtered(appointment -> {
            if (appointment.getCustomerId() == selectCustomer.getCustomerId()) {
                return true;
            }
            return false;
        });
        customerScheduleReportTableView.setItems(customerAppointments);
        cSRAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        cSRTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        cSRDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        cSRContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        cSRTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        cSRStartCol.setCellValueFactory(new PropertyValueFactory<>("convertedStartDateTime"));  // Calls converted version of getter
        cSREndCol.setCellValueFactory(new PropertyValueFactory<>("convertedEndDateTime"));  // Calls converted version of getter
        cSRCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * Called when CustomerScheduleReport screen is loaded.
     * Populates the Customer Name ComboBox with a list and displays the appointment data for whichever customer name is selected.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cSRCustomerNameCBox.setItems(customerDAO.getAllCustomers());
        ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
        ObservableList<Appointment> futureAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointments) {
            if (appointment.getEndDateTime().isAfter(LocalDateTime.now())) {
                futureAppointments.add(appointment);
            }
        }
    }
}