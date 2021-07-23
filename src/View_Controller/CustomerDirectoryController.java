package View_Controller;

import DataAccessObject.*;
import Model.Appointment;
import Model.Customer;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class Controller for the CustomerDirectory screen with methods to display customers, search for customers, and navigate to Add, Update, and Delete customers.
 */
public class CustomerDirectoryController implements Initializable {
    @FXML
    private TableView<Customer> customerDirectoryTableView;

    @FXML
    private TableColumn<Customer, Integer> cDCustomerIDCol;

    @FXML
    private TableColumn<Customer, String> cDCustomerNameCol;

    @FXML
    private TableColumn<Customer, String> cDAddressCol;

    @FXML
    private TableColumn<Customer, String> cDDivisionCol;

    @FXML
    private TableColumn<Customer, String> cDPostalCodeCol;

    @FXML
    private TableColumn<Customer, Integer> cDDivisionIDCol;

    @FXML
    private TableColumn<Customer, String> cDPhoneNumberCol;

    @FXML
    private Button addCustomerBtn;

    @FXML
    private Button updateCustomerBtn;

    @FXML
    private Button deleteCustomerBtn;

    @FXML
    private Button backAppointmentScheduleBtn;

    @FXML
    private Button searchCustomersBtn;

    @FXML
    private TextField searchCustomersTxt;

    @FXML
    private Button clearSearchCustomersBtn;

    private CommonController commonController = new CommonController();
    private ObservableList<Customer> customersSearch = FXCollections.observableArrayList();
    CustomerDAO customerDAO = new CustomerDAOImpl();
    AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    /**
     * Takes you to the AddCustomer screen.
     * @param event Add Customer Button clicked
     * @throws IOException
     */
    @FXML
    public void handleAddCustomer(ActionEvent event) throws IOException {
        commonController.handleGoToView(event, "/View_Controller/AddCustomer.fxml");
    }

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
     * Clears the search bar.
     * @param event Clear Button clicked
     */
    @FXML
    public void handleClearSearchCustomers(ActionEvent event) {
        customerDirectoryTableView.setItems(customerDAO.getAllCustomers());
        searchCustomersTxt.setText("");
    }

    /**
     * Deletes a customer from the database.
     * @param event Delete Customer Button clicked
     */
    @FXML
    public void handleDeleteCustomer(ActionEvent event) {
        // Displays error message if no customer is selected to be deleted
        if (customerDirectoryTableView.getSelectionModel().getSelectedItem() == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Customer Selection Error");
            errorAlert.setHeaderText("No customer selected to delete!");
            errorAlert.setContentText("Please select a customer to delete!");
            errorAlert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setHeaderText("Do you wish to continue?");
            alert.setContentText("Continuing will delete the selected customer and all appointments associated with that customer!");
            Optional<ButtonType> res = alert.showAndWait();
            if (res.isPresent() && res.get() == ButtonType.OK) {
                if (customerDirectoryTableView.getSelectionModel().getSelectedItem() != null) {
                    Customer selectCustomer = customerDirectoryTableView.getSelectionModel().getSelectedItem();
                    ObservableList<Appointment> allAppointments = appointmentDAO.getAllAppointments();
                    // If a customer is deleted and has any appointments, those appointments will also be deleted/cancelled
                    for (Appointment appointment : allAppointments) {
                        if (appointment.getCustomerId() == selectCustomer.getCustomerId()) {
                            appointmentDAO.deleteAppointment(appointment.getAppointmentId());
                        }
                    }
                    customerDAO.deleteCustomer(selectCustomer.getCustomerId());
                    customerDirectoryTableView.setItems(customerDAO.getAllCustomers());

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Customer Deleted Successfully");
                    alert1.setHeaderText("The following customer was deleted:");
                    alert1.setContentText("Customer ID: " + selectCustomer.getCustomerId() + "\n" +
                                          "Customer Name: " + selectCustomer.getCustomerName() + "\n" +
                                          "Customer Address: " + selectCustomer.getAddress() + "\n");
                    alert1.showAndWait();
                }
            }
        }
    }

    /**
     * Searches for customers by customerId or customerName.
     * @param event Search Button clicked
     */
    @FXML
    public void handleSearchCustomers(ActionEvent event) {
        String query = searchCustomersTxt.getText();
        if(!query.isEmpty()) {
            customersSearch.clear();
            for(Customer customer : customerDAO.getAllCustomers()) {
                if(customer.getCustomerName().toLowerCase().contains(query.toLowerCase()) || (Integer.toString(customer.getCustomerId()).contains(query))) {
                    customersSearch.add(customer);
                }
            }
            customerDirectoryTableView.setItems(customersSearch);
            customerDirectoryTableView.refresh();
        } else {
            // Displays warning message if you click on search while the search bar is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Customers search bar is empty!");
            alert.showAndWait();
        }
    }

    /**
     * Takes you to the UpdateCustomer screen.
     * @param event Update Customer Button clicked
     * @throws IOException
     */
    @FXML
    public void handleUpdateCustomer(ActionEvent event) throws IOException {
        if (customerDirectoryTableView.getSelectionModel().getSelectedItem() != null){
            Stage stage;
            Parent root;
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/UpdateCustomer.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
            UpdateCustomerController controller = loader.getController();
            Customer customer = customerDirectoryTableView.getSelectionModel().getSelectedItem();
            controller.setCustomer(customer);
        }
        else {
            // Displays error message if no customer is selected to be updated
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Selection Error");
            alert.setHeaderText("No Customer selected for update!");
            alert.setContentText("Please select a customer to update!");
            alert.showAndWait();
        }
    }

    /**
     * Called when CustomerDirectory screen is loaded.
     * Populates the table with all customer data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerDirectoryTableView.setItems(customerDAO.getAllCustomers());
        cDCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        cDCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cDAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        cDPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cDPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        cDDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        cDDivisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }
}