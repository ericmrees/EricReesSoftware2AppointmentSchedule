package Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class used to help build database queries with statements and preparedStatements.
 */
public class DatabaseQuery {
    // PreparedStatement reference
    private static PreparedStatement preparedStatement;

    /**
     * Creates preparedStatement object.
     * @param conn connects to the database
     * @param sqlStatement sqlStatement as a String value
     * @throws SQLException
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        preparedStatement = conn.prepareStatement(sqlStatement);
    }

    /**
     * Gets preparedStatement.
     * @return preparedStatement object
     */
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    // Statement reference
    private static Statement statement;

    /**
     * Creates statement object.
     * @param conn connects to the database
     * @throws SQLException
     */
    public static void setStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

    /**
     * Gets statement.
     * @return statement object
     */
    public static Statement getStatement() {
        return statement;
    }
}