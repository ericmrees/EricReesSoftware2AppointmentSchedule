package View_Controller;

import DataAccessObject.AppointmentDAO;
import DataAccessObject.AppointmentDAOImpl;
import Model.*;
import Utility.TimeConversion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static Utility.DatabaseConnection.closeConnection;

/**
 * Class Controller for the AppointmentSchedule screen with methods to display appointments, search for appointments, and navigate to Add, Update, and Delete appointments.
 * Also provides methods to navigate to CustomerDirectory, the three different Report screens, and exit the application.
 */
public class AppointmentScheduleController implements Initializable {
    @FXML
    private RadioButton monthlyRBtn;

    @FXML
    private ToggleGroup AppointmentsTGroup;;

    @FXML
    private RadioButton weeklyRBtn;

    @FXML
    private RadioButton allRBtn;

    @FXML
    private ComboBox<Month> aSMonthCBox;

    @FXML
    private TableView<Appointment> appointmentScheduleTableView;

    @FXML
    private TableColumn<Appointment, Integer> aSAppointmentIDCol;

    @FXML
    private TableColumn<Appointment, String> aSTitleCol;

    @FXML
    private TableColumn<Appointment, String> aSDescriptionCol;

    @FXML
    private TableColumn<Appointment, String> aSLocationCol;

    @FXML
    private TableColumn<Appointment, String> aSContactCol;

    @FXML
    private TableColumn<Appointment, String> aSTypeCol;

    @FXML
    private TableColumn<Appointment, String> aSStartCol;

    @FXML
    private TableColumn<Appointment, String> aSEndCol;

    @FXML
    private TableColumn<Appointment, Integer> aSCustomerIDCol;

    @FXML
    private Button addAppointmentBtn;

    @FXML
    private Button updateAppointmentBtn;

    @FXML
    private Button deleteAppointmentBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button customerDirectoryBtn;

    @FXML
    private Button customerAppointmentReportBtn;

    @FXML
    private Button contactScheduleReportBtn;

    @FXML
    private Button customerScheduleReportBtn;

    @FXML
    private Button searchAppointmentsBtn;

    @FXML
    private TextField searchAppointmentsTxt;

    @FXML
    private Button clearSearchAppointmentsBtn;

    private CommonController commonController = new CommonController();
    private ObservableList<Appointment> appointmentsSearch = FXCollections.observableArrayList();
    AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    /**
     * Takes you to the AddAppointment screen.
     * @param event Add Appointment Button clicked
     * @throws IOException
     */
    @FXML
    public void handleAddAppointment(ActionEvent event) throws IOException {
        commonController.handleGoToView(event, "/View_Controller/AddAppointment.fxml");
    }

    /**
     * Clears the search bar.
     * @param event Clear Button clicked
     */
    @FXML
    public void handleClearSearchAppointments(ActionEvent event) {
        appointmentScheduleTableView.setItems(appointmentDAO.getAllAppointments());
        searchAppointmentsTxt.setText("");
        allRBtn.setSelected(true);
    }

    /**
     * Takes you to the ContactScheduleReport screen.
     * @param event Contact Schedule Button clicked
     * @throws IOException
     */
    @FXML
    public void handleContactScheduleReport(ActionEvent event) throws IOException {
        commonController.handleGoToView(event, "/View_Controller/ContactScheduleReport.fxml");
    }

    /**
     * Takes you to the CustomerAppointmentReport screen.
     * @param event Customer Appointment Button clicked
     * @throws IOException
     */
    @FXML
    public void handleCustomerAppointmentReport(ActionEvent event) throws IOException {
        commonController.handleGoToView(event, "/View_Controller/CustomerAppointmentTypeMonthReport.fxml");
    }

    /**
     * Takes you to the CustomerDirectory screen.
     * @param event Customer Directory Button clicked
     * @throws IOException
     */
    @FXML
    public void handleCustomerDirectory(ActionEvent event) throws IOException {
        commonController.handleGoToView(event, "/View_Controller/CustomerDirectory.fxml");
    }

    /**
     * Takes you to the CustomerScheduleReport screen.
     * @param event Customer Schedule Button clicked
     * @throws IOException
     */
    @FXML
    public void handleCustomerScheduleReport(ActionEvent event) throws IOException {
        commonController.handleGoToView(event, "/View_Controller/CustomerScheduleReport.fxml");
    }

    /**
     * Deletes an appointment from the database.
     * @param event Delete Appointment Button clicked
     */
    @FXML
    public void handleDeleteAppointment(ActionEvent event) {
        if (appointmentScheduleTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setHeaderText("Do you wish to continue?");
            alert.setContentText("Continuing will cancel and delete the selected appointment!");
            Optional<ButtonType> res =  alert.showAndWait();
            if (res.isPresent() && res.get() == ButtonType.OK) {
                Appointment selectAppointment = appointmentScheduleTableView.getSelectionModel().getSelectedItem();
                appointmentDAO.deleteAppointment(selectAppointment.getAppointmentId());
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Appointment Deleted Successfully");
                alert1.setHeaderText("The following appointment was cancelled and deleted:");
                alert1.setContentText("Appointment ID: " + selectAppointment.getAppointmentId() + "\n" +
                                      "Appointment Type: " + selectAppointment.getType());
                alert1.showAndWait();
                ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
                ObservableList<Appointment> futureAppointments = FXCollections.observableArrayList();
                for (Appointment appointment : allAppointments){
                    if (appointment.getEndDateTime().isAfter(LocalDateTime.now())){
                        futureAppointments.add(appointment);
                    }
                }
                appointmentScheduleTableView.setItems(futureAppointments);
                setAppointmentScheduleColumns();
            }
        } else {
            // Displays error message if no appointment is selected to be deleted
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Appointment Selection Error");
            errorAlert.setHeaderText("No appointment selected to delete!");
            errorAlert.setContentText("Please select an appointment to delete!");
            errorAlert.showAndWait();
        }
    }

    /**
     * Exits the application and closes the database connection.
     * @param event Exit Button clicked
     */
    @FXML
    public void handleExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm to exit Appointment Schedule App!");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> res = alert.showAndWait();

        if (res.get() == ButtonType.OK) {
            closeConnection();
            System.exit(0);
        }
    }

    /**
     * Displays all future appointments.
     * @param event All Future Appointments Radio Button clicked
     */
    @FXML
    public void handleRadioAllAppointments(ActionEvent event) {
        allRBtn.setSelected(true);
        ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
        ObservableList<Appointment> futureAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointments){
            if (appointment.getEndDateTime().isAfter(LocalDateTime.now())){
                futureAppointments.add(appointment);
            }
        }
        appointmentScheduleTableView.setItems(futureAppointments);
        setAppointmentScheduleColumns();
    }

    /**
     * Displays monthly appointments, only after a month is chosen from the Select Month ComboBox.
     * @param event Monthly Appointments Radio Button clicked
     */
    @FXML
    public void handleRadioMonthlyAppointments(ActionEvent event) {
        monthlyRBtn.setSelected(true);
        aSMonthCBox.setItems(Appointment.getAllMonths());
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
        appointmentScheduleTableView.setItems(filteredAppointments);
        setAppointmentScheduleColumns();
    }

    /**
     * Displays weekly appointments, appointments for the next seven days.
     * @param event Weekly Appointments Radio Button clicked
     */
    @FXML
    public void handleRadioWeeklyAppointments(ActionEvent event) {
        weeklyRBtn.setSelected(true);
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : appointmentDAO.getAllAppointments()){
            if (appointment.getStartDateTime().isAfter(LocalDateTime.now()) && appointment.getStartDateTime().isBefore(LocalDateTime.now().plusWeeks(1))){
                filteredAppointments.add(appointment);
            }
        }
        appointmentScheduleTableView.setItems(filteredAppointments);
        setAppointmentScheduleColumns();
    }

    /**
     * Searches for appointments by appointmentId or customerId.
     * @param event Search Button clicked
     */
    @FXML
    public void handleSearchAppointments(ActionEvent event) {
        String query = searchAppointmentsTxt.getText();
        if (!query.isEmpty()) {
            appointmentsSearch.clear();
            for (Appointment appointment : appointmentDAO.getAllAppointments()) {
                if ((Integer.toString(appointment.getAppointmentId()).equals(query)) || (Integer.toString(appointment.getCustomerId()).equals(query))) {
                    appointmentsSearch.add(appointment);
                    appointmentScheduleTableView.setItems(appointmentsSearch);
                    appointmentScheduleTableView.refresh();
                }
            }
        } else {
            // Displays warning message if you click on search while the search bar is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Appointments search bar is empty!");
            alert.showAndWait();
        }
    }

    /**
     * Displays the month's appointments in the table for whichever month you select in the ComboBox.
     * @param event Select Month ComboBox month selected
     */
    @FXML
    public void handleSelectMonth(ActionEvent event) {
        Month selectMonth = aSMonthCBox.getSelectionModel().getSelectedItem();
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
        appointmentScheduleTableView.setItems(getMonthOfAppointments);
        setAppointmentScheduleColumns();
    }

    /**
     * Takes you to the UpdateAppointment screen.
     * @param event Update Appointment Button clicked
     * @throws IOException
     */
    @FXML
    public void handleUpdateAppointment(ActionEvent event) throws IOException {
        if (appointmentScheduleTableView.getSelectionModel().getSelectedItem() != null){
            Stage stage;
            Parent root;
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/UpdateAppointment.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
            UpdateAppointmentController controller = loader.getController();
            Appointment appointment = appointmentScheduleTableView.getSelectionModel().getSelectedItem();
            controller.setAppointment(appointment);
        } else {
            // Displays error message if no appointment is selected to be updated
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Selection Error");
            alert.setHeaderText("No appointment selected for update!");
            alert.setContentText("Please select an appointment to update!");
            alert.showAndWait();
        }
    }

    /**
     * Sets the columns in the table to display the correct data.
     */
    public void setAppointmentScheduleColumns() {
        aSAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId") );
        aSTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        aSDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        aSLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        aSContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        aSTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        aSStartCol.setCellValueFactory(new PropertyValueFactory<>("convertedStartDateTime"));   // Calls converted version of getter
        aSEndCol.setCellValueFactory(new PropertyValueFactory<>("convertedEndDateTime"));   // Calls converted version of getter
        aSCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * Called when AppointmentSchedule screen is loaded.
     * Selects the All Future Appointments Radio Button as default.
     * Loads the table data for all appointments to be ready for display depending on which radio button is selected.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allRBtn.setSelected(true);
        ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
        ObservableList<Appointment> futureAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointments) {
            if (appointment.getEndDateTime().isAfter(LocalDateTime.now())) {
                futureAppointments.add(appointment);
            }
        }
        appointmentScheduleTableView.setItems(appointmentDAO.getAllAppointments());
        setAppointmentScheduleColumns();
    }
}