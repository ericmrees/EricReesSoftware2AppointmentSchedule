package DataAccessObject;

import Model.Contact;
import javafx.collections.ObservableList;

/**
 * Interface to use CRUD operations on contact objects in the database.
 */
public interface ContactDAO {

    /**
     * Method to Read contacts.
     * @return ObservableList of all contacts in the database
     */
    ObservableList<Contact> getAllContacts();
}