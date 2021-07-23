package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contact is used for contact objects from the database.
 */
public class Contact {
    private int contactId;
    private String contact;
    private String email;
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * Constructor for Contact. Creates new contact.
     * @param contactId sets an int value to contactId
     * @param contact sets a String value to contact
     * @param email sets a String value to email
     */
    public Contact(int contactId, String contact, String email) {
        this.contactId = contactId;
        this.contact = contact;
        this.email = email;
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
     * Gets contact.
     * @return contact as String
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets contact.
     * @param contact contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Gets email.
     * @return email as String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     * @param email email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Creates an ObservableList of all contacts as Contact values used to populate a CombBox.
     * @return ObservableList of all contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }

    /**
     * Sets allContacts.
     * @param allContacts Contact allContacts to set
     */
    public static void setAllContacts(ObservableList<Contact> allContacts) {
        Contact.allContacts = allContacts;
    }

    /**
     * Formatting contact and contactId to display in a CombBox.
     * @return String formatted to display the contact and contactId
     */
    public String toString() {
        return(getContact() + " [" + getContactId() + "]");
    }
}