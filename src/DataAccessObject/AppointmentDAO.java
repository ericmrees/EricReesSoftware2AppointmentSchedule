package DataAccessObject;

import Model.Appointment;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

/**
 * Interface to use CRUD operations on appointment objects in the database.
 */
public interface AppointmentDAO {

    /**
     * Method to Read appointments.
     * @return ObservableList of all appointments in the database
     */
    ObservableList<Appointment> getAllAppointments();

    /**
     * Method to Create/Add appointments.
     * @param title appointment title to add
     * @param description appointment description to add
     * @param location appointment location to add
     * @param type appointment type to add
     * @param startDateTime appointment startDateTime to add
     * @param endDateTime appointment endDateTime to add
     * @param customerId appointment customerId to add
     * @param userId appointment userId to add
     * @param contactId appointment contactId to add
     */
    void addAppointment(String title, String description, String location, String type, Timestamp startDateTime, Timestamp endDateTime, int customerId, int userId, int contactId);

    /**
     * Method to Update appointments.
     * @param appointmentId appointment appointmentId to update
     * @param title appointment title to update
     * @param description appointment description to update
     * @param location appointment location to update
     * @param type appointment type to update
     * @param startDateTime appointment startDateTime to update
     * @param endDateTime appointment endDateTime to update
     * @param customerId appointment customerId to update
     * @param userId appointment userId to update
     * @param contactId appointment contactId to update
     */
    void updateAppointment(int appointmentId, String title, String description, String location, String type, Timestamp startDateTime, Timestamp endDateTime, int customerId,
                           int userId, int contactId);

    /**
     * Method to Delete appointments.
     * @param appointmentId appointment appointmentId of selected appointment to delete
     */
    void deleteAppointment(int appointmentId);
}