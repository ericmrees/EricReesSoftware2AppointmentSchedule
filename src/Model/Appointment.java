package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import static java.time.Month.*;

/**
 * Appointment is used for appointment objects from the database.
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private String contact;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;

    /**
     * Constructor for Appointment. Creates new appointment.
     * @param appointmentId sets an int value to appointmentId
     * @param title sets a String value to title
     * @param description sets a String value to description
     * @param location sets a String value to location
     * @param type sets a String value to type
     * @param contact sets a String value to contact
     * @param startDateTime sets a LocalDateTime value to startDateTime
     * @param endDateTime sets a LocalDateTime value to endDateTime
     * @param customerId sets an int value to customerId
     * @param userId sets an int value to userId
     * @param contactId sets an int value to contactId
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, String contact,
                       LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.contact = contact;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createDate = LocalDateTime.now();
        this.createdBy = "admin";
        this.lastUpdate = LocalDateTime.now();
        this.lastUpdatedBy = "admin";
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Gets appointmentId.
     * @return appointmentId as int
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets appointmentId.
     * @param appointmentId appointmentId to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets title.
     * @return title as String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     * @param title title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     * @return description as String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     * @param description description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets location.
     * @return location as String
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     * @param location location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets type.
     * @return type as String
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     * @param type type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets contact.
     * @return contact as String
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets contact
     * @param contact contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Gets startDateTime.
     * @return startDateTime as LocalDateTime
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Sets startDateTime.
     * @param startDateTime startDateTime to set
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Gets endDateTime.
     * @return endDateTime as LocalDateTime
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Sets endDateTime.
     * @param endDateTime endDateTime to set
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
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
     * Gets customerId.
     * @return customerId as int
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
     * Gets userId.
     * @return userId as int
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets userId.
     * @param userId userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets contactId.
     * @return contactId as int
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets contactId.
     * @param contactId contactId to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Converts zoned UTC startDateTime to zoned local startDateTime to display date and time.
     * @return zoned local startDateTime, in the following format: MM/dd/yyyy - HH:mm
     */
    public String getConvertedStartDateTime() {
        ZoneId zoneId = ZoneId.systemDefault(); // Default ZoneId
        ZonedDateTime localZonedStartDateTime = ZonedDateTime.of(startDateTime, zoneId); // ZonedDateTime Object - local time
        ZoneId zoneIdUTC = ZoneId.of("UTC");    // UTC ZoneId
        ZonedDateTime zonedUTCStartDateTime = ZonedDateTime.ofInstant(localZonedStartDateTime.toInstant(), zoneIdUTC);  // UTC ZonedDateTime - local time to UTC
        ZonedDateTime utcToLocalStartDateTime = ZonedDateTime.ofInstant(zonedUTCStartDateTime.toInstant(), zoneId);   // UTC to local time
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm");
        return dateTimeFormatter.format(utcToLocalStartDateTime);
    }

    /**
     * Converts zoned UTC endDateTime to zoned local endDateTime to display date and time.
     * @return zoned local endDateTime, in the following format: MM/dd/yyyy - HH:mm
     */
    public String getConvertedEndDateTime() {
        ZoneId zoneId = ZoneId.systemDefault(); // Default ZoneId
        ZonedDateTime localZonedEndDateTime = ZonedDateTime.of(endDateTime, zoneId); // ZonedDateTime Object - local time
        ZoneId zoneIdUTC = ZoneId.of("UTC");    // UTC ZoneId
        ZonedDateTime zonedUTCEndDateTime = ZonedDateTime.ofInstant(localZonedEndDateTime.toInstant(), zoneIdUTC);  // UTC ZonedDateTime - local time to UTC
        ZonedDateTime utcToLocalEndDateTime = ZonedDateTime.ofInstant(zonedUTCEndDateTime.toInstant(), zoneId);   // UTC to local time
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm");
        return dateTimeFormatter.format(utcToLocalEndDateTime);
    }

    /**
     * Converts zoned UTC startDateTime to zoned local startDateTime to only display the date.
     * @return zoned local startDateTime, in the following format: MM/dd/yyyy
     */
    public String getConvertedStartDate() {
        ZoneId zoneId = ZoneId.systemDefault(); // Default ZoneId
        ZonedDateTime localZonedStartDateTime = ZonedDateTime.of(startDateTime, zoneId); // ZonedDateTime Object - local time
        ZoneId zoneIdUTC = ZoneId.of("UTC");    // UTC ZoneId
        ZonedDateTime zonedUTCStartDateTime = ZonedDateTime.ofInstant(localZonedStartDateTime.toInstant(), zoneIdUTC);  // UTC ZonedDateTime - local time to UTC
        ZonedDateTime utcToLocalStartDateTime = ZonedDateTime.ofInstant(zonedUTCStartDateTime.toInstant(), zoneId);   // UTC to local time
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return dateTimeFormatter.format(utcToLocalStartDateTime);
    }

    /**
     * Verifies if there is a conflict with overlapping appointments using appointmentId for startDateTime and endDateTime for each customer.
     * @param appointmentId appointmentId to retrieve appointments by id
     * @param startDateTime startDateTime to verify is there's a date or time conflict
     * @param endDateTime endDateTime to verify is there's a date or time conflict
     * @return true if there's a conflict with the appointment, false if there's no conflict with the appointment
     * @throws SQLException
     */
    public static Appointment conflictInSchedule(int appointmentId, LocalDateTime startDateTime, LocalDateTime endDateTime) throws SQLException {
        Appointment appointmentConflict = null;
        ObservableList<Appointment> allCustomerAppointments = Customer.getAllCustomerAppointments(appointmentId);
        for (Appointment appointment : allCustomerAppointments) {
            if (startDateTime.isAfter(appointment.getStartDateTime()) && startDateTime.isBefore(appointment.getEndDateTime()) ||
                    endDateTime.isAfter(appointment.getStartDateTime()) && endDateTime.isBefore(appointment.getEndDateTime()) ||
                    startDateTime.isBefore(appointment.getStartDateTime()) && endDateTime.isAfter(appointment.getEndDateTime()) ||
                    startDateTime.equals(appointment.getStartDateTime()) && endDateTime.equals(appointment.getEndDateTime()) ||
                    startDateTime.equals(appointment.getStartDateTime()) || endDateTime.equals(appointment.getEndDateTime())) {
                appointmentConflict = appointment;
            }
        }
        return appointmentConflict;
    }

    /**
     * Creates an ObservableList of all types as String values used to populate a ComboBox.
     * @return ObservableList of all types
     */
    public static ObservableList<String> getAllTypes() {
        ObservableList<String> allTypes = FXCollections.observableArrayList();
        allTypes.add("Human Resource");
        allTypes.add("Marketing");
        allTypes.add("Personal");
        allTypes.add("Sales");
        allTypes.add("Virtual");
        allTypes.add("Other");
        return allTypes;
    }

    /**
     * Creates an ObservableList of all months as Month values used to populate a ComboBox.
     * @return ObservableList of all months
     */
    public static ObservableList<Month> getAllMonths() {
        ObservableList<Month> allMonths = FXCollections.observableArrayList();
        allMonths.add(JANUARY);
        allMonths.add(FEBRUARY);
        allMonths.add(MARCH);
        allMonths.add(APRIL);
        allMonths.add(MAY);
        allMonths.add(JUNE);
        allMonths.add(JULY);
        allMonths.add(AUGUST);
        allMonths.add(SEPTEMBER);
        allMonths.add(OCTOBER);
        allMonths.add(NOVEMBER);
        allMonths.add(DECEMBER);
        return allMonths;
    }
}