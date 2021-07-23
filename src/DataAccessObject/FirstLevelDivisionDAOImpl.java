package DataAccessObject;

import Model.FirstLevelDivision;
import Utility.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Implements FirstLevelDivisionDAO Interface to perform CRUD operations on first level division objects in the database.
 */
public class FirstLevelDivisionDAOImpl implements FirstLevelDivisionDAO {

    /**
     * Method implementation to Read first level divisions.
     * @return ObservableList of all first level divisions in the database
     */
    @Override
    public ObservableList<FirstLevelDivision> getAllDivisions() {
        ObservableList<FirstLevelDivision> allFirstLevelDivisions = FXCollections.observableArrayList();
        try {
            Connection conn = DatabaseConnection.getConnection();
            String selectStatement = ("SELECT * FROM first_level_divisions");
            PreparedStatement ps = conn.prepareStatement(selectStatement);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("Country_ID");
                // add(firstLevelDivisionDB) reads all first level divisions from the database
                FirstLevelDivision firstLevelDivisionDB = new FirstLevelDivision(divisionId, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
                allFirstLevelDivisions.add(firstLevelDivisionDB);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allFirstLevelDivisions;
    }
}