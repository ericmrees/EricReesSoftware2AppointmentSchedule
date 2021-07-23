package DataAccessObject;

import Model.Contact;
import Utility.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implements ContactDAO Interface to perform CRUD operations on contact objects in the database.
 */
public class ContactDAOImpl implements ContactDAO {

    /**
     * Method implementation to Read contacts.
     * @return ObservableList of all contacts in the database
     */
    @Override
    public ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String selectStatement = ("SELECT * FROM contacts");
            PreparedStatement ps = conn.prepareStatement(selectStatement);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                // add(contactDB) reads all contacts from the database
                Contact contactDB = new Contact(contactId, contact, email);
                allContacts.add(contactDB);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allContacts;
    }
}