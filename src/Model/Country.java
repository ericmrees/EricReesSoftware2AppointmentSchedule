package Model;

import java.time.LocalDateTime;

/**
 * Country is used for country objects from the database.
 */
public class Country {
    private int countryId;
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * Constructor for Country. Creates new country.
     * @param countryId sets an int value to countryId
     * @param country sets a String value to country
     * @param createDate sets a LocalDateTime value to createDate
     * @param createdBy sets a String value to createdBy
     * @param lastUpdate sets a LocalDateTime value to lastUpdate
     * @param lastUpdatedBy sets a String value to lastUpdatedBy
     */
    public Country(int countryId, String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *Gets countryId.
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
     * Gets country.
     * @return country as String
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     * @param country country to set
     */
    public void setCountry(String country) {
        this.country = country;
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
     * Formatting country and countryId to display in a combo box.
     * @return String formatted to display the country and countryId
     */
    public String toString() {
        return(getCountry() + " [" + getCountryId() + "]");
    }
}