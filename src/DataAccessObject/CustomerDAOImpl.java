package DataAccessObject;

import Model.Customer;
import Utility.DatabaseConnection;
import Utility.DatabaseQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Implements CustomerDAO Interface to perform CRUD operations on customer objects in the database.
 */
public class CustomerDAOImpl implements CustomerDAO {

    /**
     * Method implementation to Read customers.
     * @return ObservableList of all customers in the database
     */
    @Override
    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String selectStatement = ("SELECT * FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID");
            PreparedStatement ps = conn.prepareStatement(selectStatement);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionId = rs.getInt( "Division_ID" );
                String division = rs.getString("Division");
                // add(customerDB) reads all customers from the database
                Customer customerDB = new Customer(customerId, customerName, address, postalCode, phoneNumber, divisionId, division);
                allCustomers.add(customerDB);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allCustomers;
    }

    /**
     * Method implementation to Create/Add customers.
     * @param customerName customer customerName to add
     * @param address customer address to add
     * @param postalCode customer postalCode to add
     * @param phoneNumber customer phoneNumber to add
     * @param divisionId customer divisionId to add
     */
    @Override
    public void addCustomer(String customerName, String address, String postalCode, String phoneNumber, int divisionId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String insertStatement = ("INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement ps = conn.prepareStatement(insertStatement);
            // Key-value mapping
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setString(5, "admin");
            ps.setString(6, "admin");
            ps.setInt(7, divisionId);
            // Execute PreparedStatement
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method implementation to Update customers.
     * @param customerId customer customerId to update
     * @param customerName customer customerName to update
     * @param address customer address to update
     * @param postalCode customer postalCode to update
     * @param phoneNumber customer phoneNumber to update
     * @param divisionId customer divisionId to update
     */
    @Override
    public void updateCustomer(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String updateStatement = ("UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?," +
                                      "Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?");
            PreparedStatement ps = conn.prepareStatement(updateStatement);
            // Key-value mapping
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6, "admin");
            ps.setInt(7, divisionId);
            ps.setInt(8, customerId);
            // Execute PreparedStatement
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method implementation to Delete customers.
     * @param customerId customer customerId of selected customer to delete
     */
    @Override
    public void deleteCustomer(int customerId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";
            DatabaseQuery.setPreparedStatement(conn, deleteStatement);
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            // Key-value mapping
            ps.setInt(1, customerId);
            // Execute PreparedStatement
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}