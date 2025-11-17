package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import config.DatabaseConnection;

public class TestConexion {

    public static void main(String[] args) {
        // try-with-resources: cierra la conexión automáticamente
        try (Connection conn = DatabaseConnection.getConnection()) {

            // 1) Verificación básica de versión, puerto y base activa
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT VERSION(), @@port, DATABASE(), USER(), CURRENT_USER()")) {
                if (rs.next()) {
                    System.out.println("Conectado a MySQL " + rs.getString(1) +
                            " puerto=" + rs.getInt(2) +
                            " base=" + rs.getString(3) +
                            " user=" + rs.getString(4) +
                            " current_user=" + rs.getString(5));
                }
            }

            // 2) Cantidades por tabla
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(
                         "SELECT " +
                         "(SELECT COUNT(*) FROM Cliente) AS clientes, " +
                         "(SELECT COUNT(*) FROM Vehiculo) AS vehiculos, " +
                         "(SELECT COUNT(*) FROM SeguroVehicular) AS seguros")) {
                if (rs.next()) {
                    System.out.println("Registros -> Cliente: " + rs.getInt("clientes")
                            + ", Vehiculo: " + rs.getInt("vehiculos")
                            + ", SeguroVehicular: " + rs.getInt("seguros"));
                }
            }

            // 3) Muestra 10 seguros con su vehículo y cliente
            String sql =
                "SELECT s.ID_Seguro, v.ID_Vehiculo, v.Patente, c.Nombre, c.Apellido, " +
                "       s.Tipo_Seguro, s.Monto_Asegurado, s.Fecha_Inicio, s.Fecha_Fin, s.Estado " +
                "FROM SeguroVehicular s " +
                "JOIN Vehiculo v ON v.ID_Vehiculo = s.ID_Vehiculo " +
                "JOIN Cliente c  ON c.ID_Cliente  = v.ID_Cliente " +
                "ORDER BY s.ID_Seguro " +
                "LIMIT 10";

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                System.out.println("Primeros 10 seguros:");
                while (rs.next()) {
                    System.out.println(
                        "Seguro #" + rs.getInt("ID_Seguro") +
                        " Vehiculo=" + rs.getInt("ID_Vehiculo") + " (" + rs.getString("Patente") + ")" +
                        " Cliente=" + rs.getString("Nombre") + " " + rs.getString("Apellido") +
                        " Tipo=" + rs.getString("Tipo_Seguro") +
                        " Monto=" + rs.getBigDecimal("Monto_Asegurado") +
                        " Inicio=" + rs.getDate("Fecha_Inicio") +
                        " Fin=" + rs.getDate("Fecha_Fin") +
                        " Estado=" + rs.getString("Estado")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al conectar/consultar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
