package DataAccessObject;

import Model.FirstLevelDivision;
import javafx.collections.ObservableList;

/**
 * Interface to use CRUD operations on first level division objects in the database.
 */
public interface FirstLevelDivisionDAO {

    /**
     * Method to Read first level divisions.
     * @return ObservableList of all first level divisions in the database
     */
    ObservableList<FirstLevelDivision> getAllDivisions();
}