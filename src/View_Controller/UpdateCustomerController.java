package View_Controller;

import DataAccessObject.*;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Class Controller for the UpdateCustomer screen with methods to input data and Update customers in the database.
 */
public class UpdateCustomerController {
    @FXML
    private TextField uCCustomerIDTxt;

    @FXML
    private TextField uCCustomerNameTxt;

    @FXML
    private TextField uCAddressTxt;

    @FXML
    private TextField uCPostalCodeTxt;

    @FXML
    private TextField uCPhoneNumberTxt;

    @FXML
    private ComboBox<FirstLevelDivision> uCDivisionCBox;

    @FXML
    private ComboBox<Country> uCCountryCBox;

    @FXML
    private Button saveUpdateCustomerBtn;

    @FXML
    private Button cancelUpdateCustomerBtn;

    private CommonController commonController = new CommonController();
    CountryDAO countryDAO = new CountryDAOImpl();
    FirstLevelDivisionDAO firstLevelDivisionDAO = new FirstLevelDivisionDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();

    /**
     * Cancels updating a customer, clears and cancels any changes made in the fields, and takes you back to the CustomerSchedule screen.
     * @param event Cancel Button clicked
     * @throws IOException
     */
    @FXML
    public void handleCancelUpdateCustomer(ActionEvent event) throws IOException {
        commonController.handleCancelCustomer(event);
    }

    /**
     * Updates a customer in the database and validates all of the input data used to update the customer.
     * Displays the original customer data in all the fields once the UpdateCustomer screen is opened.
     * @param event Save Button clicked
     * @throws IOException
     */
    @FXML
    public void handleSaveUpdateCustomer(ActionEvent event) throws IOException {
        // Displays error message if any fields are empty
        if (uCCustomerNameTxt.getText().isEmpty() || uCAddressTxt.getText().isEmpty() || uCPostalCodeTxt.getText().isEmpty() || uCPhoneNumberTxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Customer Error");
            alert.setHeaderText("One or more fields are empty!");
            alert.setContentText("Please enter valid data for all fields to update customer!");
            alert.showAndWait();
        }
        else {
            // Everything is valid, stores and updates input data in variables
            int customerId = Integer.parseInt(uCCustomerIDTxt.getText());
            String customerName = String.valueOf(uCCustomerNameTxt.getText());
            String address = String.valueOf(uCAddressTxt.getText());
            String postalCode = String.valueOf(uCPostalCodeTxt.getText());
            String phoneNumber = String.valueOf(uCPhoneNumberTxt.getText());
            int divisionId = uCDivisionCBox.getValue().getDivisionId();
            // updateCustomer updates the customer to the database and returns to CustomerDirectory
            customerDAO.updateCustomer(customerId, customerName, address, postalCode, phoneNumber, divisionId);
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
        Country selectCountry = uCCountryCBox.getSelectionModel().getSelectedItem();
        // Lambda Expression
        ObservableList<FirstLevelDivision> countryDivisions = firstLevelDivisionDAO.getAllDivisions().filtered(division -> {
            if (division.getCountryId() == selectCountry.getCountryId()) {
                return true;
            }
            return false;
        });
        uCDivisionCBox.setItems(countryDivisions);
    }

    /**
     * Automatically sets customer attributes in the UpdateCustomer screen from the customer selected from the table in the CustomerDirectory screen.
     * @param customer selected customer from the table in the CustomerDirectory screen
     */
    public void setCustomer(Customer customer) {
        // Gets division from divisionId
        FirstLevelDivision selectDivision = getDivisionFromDivisionId(customer.getDivisionId());
        // Gets country from divisionId
        Country selectCountry = getCountryFromCountryId(selectDivision.getCountryId());
        // List of all divisions within a selected country
        ObservableList<FirstLevelDivision> divisionInSelectCountry = FXCollections.observableArrayList();
        // Populates country ComboBox with all countries
        uCCountryCBox.setItems(countryDAO.getAllCountries());
        // Sets the selected country
        uCCountryCBox.setValue(selectCountry);
        // Gets all divisions within the selected country
        for (FirstLevelDivision division : firstLevelDivisionDAO.getAllDivisions()) {
            if (selectCountry.getCountryId() == division.getCountryId()) {
                divisionInSelectCountry.add(division);
            }
        }
        // Sets all divisions within the selected country
        uCDivisionCBox.setItems(divisionInSelectCountry);
        // Sets the selected division
        uCDivisionCBox.setValue(selectDivision);
        // All selected customer attributes taken for modification
        uCCustomerIDTxt.setText(Integer.toString(customer.getCustomerId()));
        uCCustomerNameTxt.setText(customer.getCustomerName());
        uCAddressTxt.setText(customer.getAddress());
        uCPostalCodeTxt.setText(customer.getPostalCode());
        uCPhoneNumberTxt.setText(customer.getPhoneNumber());
    }

    /**
     * Gets the division from the divisionId.
     * @param divisionId divisionId to retrieve divisions by id
     * @return division
     */
    public FirstLevelDivision getDivisionFromDivisionId(int divisionId) {
        ObservableList<FirstLevelDivision> allDivisions = firstLevelDivisionDAO.getAllDivisions();
        FirstLevelDivision selectDivision = null;
        for (FirstLevelDivision division : allDivisions){
            if (divisionId == division.getDivisionId()){
                selectDivision = division;
            }
        }
        return selectDivision;
    }

    /**
     * Gets the country from the countryId.
     * @param countryId countryId to retrieve countries by id
     * @return country
     */
    public Country getCountryFromCountryId(int countryId) {
        ObservableList<Country> allCountries = countryDAO.getAllCountries();
        Country selectCountry = null;
        for (Country country : allCountries){
            if (countryId == country.getCountryId()){
                selectCountry = country;
            }
        }
        return selectCountry;
    }
}