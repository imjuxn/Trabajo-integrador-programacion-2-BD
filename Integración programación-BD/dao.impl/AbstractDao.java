package dao.impl;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Nilus Global
 */
public abstract class AbstractDao {
    protected Connection newConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }
}
