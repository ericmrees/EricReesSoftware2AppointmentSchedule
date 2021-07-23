package DataAccessObject;

import Model.Country;
import javafx.collections.ObservableList;

/**
 * Interface to use CRUD operations on country objects in the database.
 */
public interface CountryDAO {

    /**
     * Method to Read countries.
     * @return ObservableList of all countries in the database
     */
    ObservableList<Country> getAllCountries();
}