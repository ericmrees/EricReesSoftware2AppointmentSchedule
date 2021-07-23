package View_Controller;

import DataAccessObject.*;
import Model.Country;
import Model.FirstLevelDivision;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class Controller for the AddCustomer screen with methods to input data and Add new customers to the database.
 */
public class AddCustomerController implements Initializable {
    @FXML
    private TextField aCCustomerIDTxt;

    @FXML
    private TextField aCCustomerNameTxt;

    @FXML
    private TextField aCAddressTxt;

    @FXML
    private TextField aCPostalCodeTxt;

    @FXML
    private TextField aCPhoneNumberTxt;

    @FXML
    private ComboBox<FirstLevelDivision> aCDivisionCBox;

    @FXML
    private ComboBox<Country> aCCountryCBox;

    @FXML
    private Button saveAddCustomerBtn;

    @FXML
    private Button cancelAddCustomerBtn;

    private CommonController commonController = new CommonController();
    CustomerDAO customerDAO = new CustomerDAOImpl();
    CountryDAO countryDAO = new CountryDAOImpl();
    FirstLevelDivisionDAO firstLevelDivisionDAO = new FirstLevelDivisionDAOImpl();

    /**
     * Cancels adding a new customer, clears all data from fields, and takes you back to the CustomerDirectory screen.
     * @param event Cancel Button clicked
     * @throws IOException
     */
    @FXML
    public void handleCancelAddCustomer(ActionEvent event) throws IOException {
        commonController.handleCancelCustomer(event);
    }

    /**
     * Adds a new customer to the database and validates all of the input data used to create the new customer.
     * @param event Save Button clicked
     * @throws IOException
     */
    @FXML
    public void handleSaveAddCustomer(ActionEvent event) throws IOException {
        // Displays error message if any fields are empty
        if (aCCustomerNameTxt.getText().isEmpty() || aCAddressTxt.getText().isEmpty() || aCPostalCodeTxt.getText().isEmpty()
                || aCPhoneNumberTxt.getText().isEmpty() || aCDivisionCBox.getSelectionModel().getSelectedItem() == null
                || aCCountryCBox.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Customer Error");
            alert.setHeaderText("One or more fields are empty!");
            alert.setContentText("Please enter valid data for all fields to add customer!");
            alert.showAndWait();
        } else {
            // Stores input data in variables
            String customerName = aCCustomerNameTxt.getText();
            String address = aCAddressTxt.getText();
            String postalCode = aCPostalCodeTxt.getText();
            String phoneNumber = aCPhoneNumberTxt.getText();
            int divisionId = aCDivisionCBox.getValue().getDivisionId();
            // addCustomer method adds new customer to the database and returns to CustomerDirectory
            customerDAO.addCustomer(customerName, address, postalCode, phoneNumber, divisionId);
            commonController.handleCustomerDirectory(event);
        }
    }

    /**
     * Method loads the Division ComboBox with the appropriate list of values according to the selection in the Country ComboBox.
     * This Lambda Expression takes away the need to use an extra ObservableList, hence less code and better readability.
     * If the division belongs in the filtered list and the division's countryId equals the selected country's countryId,
     * it returns true and populates the division ComboBox with the appropriate list of values according to the country selection, otherwise it returns false.
     * @param event Country ComboBox selected
     */
    @FXML
    public void handleSelectCountry(ActionEvent event) {
        Country selectCountry = aCCountryCBox.getSelectionModel().getSelectedItem();
        // Lambda Expression
        ObservableList<FirstLevelDivision> countryDivisions = firstLevelDivisionDAO.getAllDivisions().filtered(division -> {
            if (division.getCountryId() == selectCountry.getCountryId()) {
                return true;
            }
            return false;
        });
        aCDivisionCBox.setItems(countryDivisions);
    }

    /**
     * Called when AddCustomer screen is loaded.
     * Populates a list in the country ComboBox.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets data in Country ComboBox
        aCCountryCBox.setItems(countryDAO.getAllCountries());
    }
}