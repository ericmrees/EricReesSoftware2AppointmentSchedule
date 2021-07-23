package Model;

import java.time.LocalDateTime;

/**
 * User is used for user objects from the database.
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * Constructor for User. Creates new user.
     * @param userId sets an int value to userId
     * @param username sets a String value to username
     * @param password sets a String value to password
     * @param createDate sets a LocalDateTime value to createDate
     * @param createdBy sets a String value to createdBy
     * @param lastUpdate sets a LocalDateTime value to lastUpdate
     * @param lastUpdatedBy sets a String value to lastUpdatedBy
     */
    public User(int userId, String username, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets userId.
     * @return userId as int
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets userId.
     * @param userId userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets username.
     * @return username as String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     * @param username username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     * @return password as String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     * @param password password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
     * Formatting user and userId to display in a ComboBox.
     * @return String formatted to display the user and userId
     */
    public String toString() {
        return(getUsername() + " [" + getUsername() + "]");
    }
}