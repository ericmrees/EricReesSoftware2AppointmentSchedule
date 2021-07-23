package DataAccessObject;

import Model.Customer;
import javafx.collections.ObservableList;

/**
 * Interface to use CRUD operations on customer objects in the database.
 */
public interface CustomerDAO {

    /**
     * Method to Read customers.
     * @return ObservableList of all customers in the database
     */
    ObservableList<Customer> getAllCustomers();

    /**
     * Method to Create/Add customers.
     * @param customerName customer customerName to add
     * @param address customer address to add
     * @param postalCode customer postalCode to add
     * @param phoneNumber customer phoneNumber to add
     * @param divisionId customer divisionId to add
     */
    void addCustomer(String customerName, String address, String postalCode, String phoneNumber, int divisionId);

    /**
     * Method to Update customers.
     * @param customerId customer customerId to update
     * @param customerName customer customerName to update
     * @param address customer address to update
     * @param postalCode customer postalCode to update
     * @param phoneNumber customer phoneNumber to update
     * @param divisionId customer divisionId to update
     */
    void updateCustomer(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId);

    /**
     * Method to Delete customers
     * @param customerId customer customerId of selected customer to delete
     */
    void deleteCustomer(int customerId);
}