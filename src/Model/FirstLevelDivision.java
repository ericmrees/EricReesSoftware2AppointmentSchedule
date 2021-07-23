package Model;

import java.time.LocalDateTime;

/**
 * FirstLevelDivision is used for first level division objects from the database.
 */
public class FirstLevelDivision {
    private int divisionId;
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryId;

    /**
     * Constructor for FirstLevelDivision. Creates new first level division.
     * @param divisionId sets an int value to divisionId
     * @param division sets a String value to division
     * @param createDate sets a LocalDateTime value to createDate
     * @param createdBy sets a String value to createdBy
     * @param lastUpdate sets a LocalDateTime value to lastUpdate
     * @param lastUpdatedBy sets a String value to lastUpdatedBy
     * @param countryId sets an int value to countryId
     */
    public FirstLevelDivision(int divisionId, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    /**
     * Gets divisionId.
     * @return divisionId as int
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets divisionId.
     * @param divisionId divisionId to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets division.
     * @return division as String
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets division.
     * @param division division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets createDate.
     * @return createDate as LocalDateTime
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets createDate.
     * @param createDate createDate to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets createdBy.
     * @return createdBy as String
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets createdBy.
     * @param createdBy createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets lastUpdate.
     * @return lastUpdate as LocalDateTime
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets lastUpdate.
     * @param lastUpdate lastUpdate to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets lastUpdatedBy.
     * @return lastUpdatedBy as String
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets lastUpdatedBy.
     * @param lastUpdatedBy lastUpdatedBy to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets countryId.
     * @return countryId as int
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets countryId.
     * @param countryId countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Formatting division and divisionId to display in a ComboBox.
     * @return String formatted to display the division and divisionId
     */
    public String toString() {
        return(getDivision() + " [" + getDivisionId() + "]");
    }
}