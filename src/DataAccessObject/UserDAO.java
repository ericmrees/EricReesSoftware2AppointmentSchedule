package DataAccessObject;

import Model.User;
import javafx.collections.ObservableList;

/**
 * Interface to use CRUD operations on user objects in the database.
 */
public interface UserDAO {

    /**
     * Method to Read users.
     * @return ObservableList of all users in the database
     */
    ObservableList<User> getAllUsers();
}