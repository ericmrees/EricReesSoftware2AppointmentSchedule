package DataAccessObject;

import Model.Appointment;
import Utility.DatabaseConnection;
import Utility.DatabaseQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

import static Utility.DatabaseQuery.getPreparedStatement;

/**
 * Implements AppointmentDAO Interface to perform CRUD operations on appointment objects in the database.
 */
public class AppointmentDAOImpl implements AppointmentDAO {

    /**
     * Method implementation to Read appointments.
     * @return ObservableList of all appointments in the database
     */
    @Override
    public ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String selectStatement = ("SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID");
            PreparedStatement ps = conn.prepareStatement(selectStatement);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String contact = rs.getString("Contact_Name");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt( "Customer_ID" );
                int userId = rs.getInt( "User_ID" );
                int contactId = rs.getInt( "Contact_ID" );
                // add(appointmentDB) reads all appointments from the database
                Appointment appointmentDB = new Appointment(appointmentId, title, description, location, type, contact, startDateTime, endDateTime, customerId, userId, contactId);
                allAppointments.add(appointmentDB);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allAppointments;
    }

    /**
     * Method implementation to Create/Add appointments.
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
    @Override
    public void addAppointment(String title, String description, String location, String type, Timestamp startDateTime, Timestamp endDateTime, int customerId, int userId, int contactId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String insertStatement = ("INSERT INTO appointments(Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By," +
                                      "Customer_ID, User_ID, Contact_Id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement ps = conn.prepareStatement(insertStatement);
            // Key-value mapping
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startDateTime);
            ps.setTimestamp(6, endDateTime);
            ps.setString(7, "admin");
            ps.setString(8, "admin");
            ps.setInt(9, customerId);
            ps.setInt(10, userId);
            ps.setInt(11, contactId);
            // Execute PreparedStatement
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method implementation to Update appointments.
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
    @Override
    public void updateAppointment(int appointmentId, String title, String description, String location, String type, Timestamp startDateTime, Timestamp endDateTime,
                                  int customerId, int userId, int contactId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String updateStatement = ("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?," +
                                      "Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_Id = ? WHERE Appointment_ID = ?");
            PreparedStatement ps = conn.prepareStatement(updateStatement);
            // Key-value mapping
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startDateTime);
            ps.setTimestamp(6, endDateTime);
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, "admin");
            ps.setInt(9, customerId);
            ps.setInt(10, userId);
            ps.setInt(11, contactId);
            ps.setInt(12, appointmentId);
            // Execute PreparedStatement
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method implementation to Delete appointments.
     * @param appointmentId appointment appointmentId of selected appointment to delete
     */
    @Override
    public void deleteAppointment(int appointmentId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
            DatabaseQuery.setPreparedStatement(conn, deleteStatement);
            PreparedStatement ps = getPreparedStatement();
            // Key-value mapping
            ps.setInt(1, appointmentId);
            // Execute PreparedStatement
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}