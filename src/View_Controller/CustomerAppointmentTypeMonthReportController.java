package View_Controller;

import DataAccessObject.AppointmentDAO;
import DataAccessObject.AppointmentDAOImpl;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class Controller for the CustomerAppointmentTypeMonthReport screen with methods to display reports of appointments by each type and appointments by each month.
 */
public class CustomerAppointmentTypeMonthReportController implements Initializable {
    @FXML
    private TableView<Appointment> typeTotalTableView;

    @FXML
    private TableColumn<Appointment, String> cARTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> cARTypeAppointmentIdCol;

    @FXML
    private Button backAppointmentScheduleBtn;

    @FXML
    private TableView<Appointment> monthTotalTableView;

    @FXML
    private TableColumn<Appointment, LocalDateTime> cARAppointmentDateCol;

    @FXML
    private TableColumn<Appointment, Integer> cARMonthAppointmentIdCol;

    @FXML
    private TextField totalTypeTxt;

    @FXML
    private TextField totalMonthTxt;

    @FXML
    private ComboBox<String> cARTypeCBox;

    @FXML
    private ComboBox<Month> cARMonthCBox;

    private CommonController commonController = new CommonController();
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
     * Displays the month's appointments in the table for whichever month you select in the ComboBox.
     * @param event Select Month ComboBox month selected
     */
    @FXML
    public void handleSelectMonth(ActionEvent event) {
        Month selectMonth = cARMonthCBox.getSelectionModel().getSelectedItem();
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
        ObservableList<Appointment> getMonthOfAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointments) {
            LocalDateTime startDateTime = appointment.getStartDateTime();
            LocalDate appointmentDate = startDateTime.toLocalDate();
            if (Objects.equals(appointmentDate.getMonth(), selectMonth)) {
                getMonthOfAppointments.add(appointment);
            }
        }
        monthTotalTableView.setItems(getMonthOfAppointments);
        cARMonthAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        cARAppointmentDateCol.setCellValueFactory(new PropertyValueFactory<>("convertedStartDate"));    // Calls converted version of getter
        totalMonthTxt.setText(String.valueOf(monthTotalTableView.getItems().size()));
    }

    /**
     * Method displays the type's appointments in the table according to the selection in the Type ComboBox.
     * This Lambda Expression takes away the need to use an extra ObservableList, hence less code and better readability.
     * If the appointment belongs in the filtered list and the appointment's type equals the selected type, it returns true
     * and populates the appropriate appointments and attributes into the table according to the type selection, otherwise it returns false.
     * @param event Select Type ComboBox type selected
     */
    @FXML
    public void handleSelectType(ActionEvent event) {
        String selectType = cARTypeCBox.getSelectionModel().getSelectedItem();
        // Lambda Expression
        ObservableList<Appointment> typeAppointments = appointmentDAO.getAllAppointments().filtered(appointment -> {
            if (appointment.getType().equals(selectType)) {
                return true;
            }
            return false;
        });
        typeTotalTableView.setItems(typeAppointments);
        cARTypeAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        cARTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalTypeTxt.setText(String.valueOf(typeTotalTableView.getItems().size()));
    }

    /**
     * Called when CustomerAppointmentTypeMonthReport screen is loaded.
     * Populates the Type and Month ComboBoxes with lists and displays the appointment data for whichever contact name and month is selected.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cARTypeCBox.setItems(Appointment.getAllTypes());
        cARMonthCBox.setItems(Appointment.getAllMonths());
        ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
        ObservableList<Appointment> futureAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointments) {
            if (appointment.getEndDateTime().isAfter(LocalDateTime.now())) {
                futureAppointments.add(appointment);
            }
        }
    }
}
