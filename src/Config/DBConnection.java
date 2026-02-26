package Config;

import Exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private DBConnection() { }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(AppConfig.DB_URL, AppConfig.DB_USER, AppConfig.DB_PASSWORD);
        } catch (SQLException e) {
            throw new DataAccessException("No se pudo conectar a la base de datos", e);
        }
    }
}