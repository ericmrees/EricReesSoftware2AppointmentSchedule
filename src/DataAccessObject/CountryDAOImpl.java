package DataAccessObject;

import Model.Country;
import Utility.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Implements CountryDAO Interface to perform CRUD operations on country objects in the database.
 */
public class CountryDAOImpl implements CountryDAO {

    /**
     * Method implementation to Read countries.
     * @return ObservableList of all countries in the database
     */
    @Override
    public ObservableList<Country> getAllCountries() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String selectStatement = ("SELECT * FROM countries");
            PreparedStatement ps = conn.prepareStatement(selectStatement);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                // add(countryDB) reads all countries from the database
                Country countryDB = new Country(countryId, country, createDate, createdBy, lastUpdate, lastUpdatedBy);
                allCountries.add(countryDB);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allCountries;
    }
}