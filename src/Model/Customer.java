package Model;

import DataAccessObject.AppointmentDAO;
import DataAccessObject.AppointmentDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/**
 * Customer is used for customer objects from the database.
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;
    private String division;

    /**
     * Constructor for Customer. Creates new customer.
     * @param customerId sets an int value to customerId
     * @param customerName sets a String value to customerName
     * @param address sets a String value to address
     * @param postalCode sets a String value to postalCode
     * @param phoneNumber sets a String value to phoneNumber
     * @param divisionId sets an int value to divisionId
     * @param division sets a String value to division
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId, String division) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = LocalDateTime.now();
        this.createdBy = "admin";
        this.lastUpdate = LocalDateTime.now();
        this.lastUpdatedBy = "admin";
        this.divisionId = divisionId;
        this.division = division;
    }

    /**
     *
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets customerId.
     * @param customerId customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets customerName.
     * @param customerName customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     * @param address address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postalCode.
     * @param postalCode postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phoneNumber.
     * @param phoneNumber phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets createDate.
     * @return createDate as LocalDateTime
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets createDate.
     * @param createDate createDate to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets createdBy.
     * @return createdBy as String
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets createdBy.
     * @param createdBy createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets lastUpdate.
     * @return lastUpdate as LocalDateTime
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets lastUpdate.
     * @param lastUpdate lastUpdate to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets lastUpdatedBy.
     * @return lastUpdatedBy as String
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets lastUpdatedBy.
     * @param lastUpdatedBy lastUpdatedBy to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     * @return
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets divisionId.
     * @param divisionId divisionId to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     *
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets division.
     * @param division division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Creates an ObservableList of all appointments by customer, as appointment values.
     * This validates the appointmentId with the customerId to get all appointments by customer.
     * @param appointmentId appointmentId to retrieve appointments by id
     * @return ObservableList of all appointments by customer
     */
    public static ObservableList<Appointment> getAllCustomerAppointments(int appointmentId) {
        ObservableList<Appointment> allCustomerAppointments = FXCollections.observableArrayList();
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        for (Appointment appointment : appointmentDAO.getAllAppointments()){
            if (appointment.getCustomerId() == appointmentId){
                allCustomerAppointments.add(appointment);
            }
        }
        return allCustomerAppointments;
    }

    /**
     * Formatting customer and customerId to display in a ComboBox.
     * @return String formatted to display the customer and customerId
     */
    @Override
    public String toString() {
        return (getCustomerName() + " [" + getCustomerId() + "]");
    }
}