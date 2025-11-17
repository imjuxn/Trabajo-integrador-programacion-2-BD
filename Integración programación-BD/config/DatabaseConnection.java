package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Nilus Global
 */
public class DatabaseConnection {
    private static final String HOST = "127.0.0.1";
    private static final int    PORT = 3306; // si luego usas 8.4 en otro puerto, cámbialo aquí
    private static final String DB   = "ProgramacionIntegracion";
    private static final String USER = "root";
    private static final String PASS = "Juan12345";

    private static final String URL =
        "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB +
        "?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC de MySQL no encontrado", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
