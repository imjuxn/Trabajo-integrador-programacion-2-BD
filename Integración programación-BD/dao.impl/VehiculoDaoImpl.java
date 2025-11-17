package dao.impl;

import dao.VehiculoDao;
import entities.SeguroVehicular;
import entities.Vehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nilus Global
 */
public class VehiculoDaoImpl extends AbstractDao implements VehiculoDao {

    private Vehiculo mapVehiculo(ResultSet rs) throws SQLException {
        Vehiculo v = new Vehiculo();
        v.setId(rs.getLong("ID_Vehiculo"));
        v.setEliminado(rs.getBoolean("eliminado"));
        v.setPatente(rs.getString("Patente"));
        v.setMarca(rs.getString("Marca"));
        v.setModelo(rs.getString("Modelo"));
        v.setAnio(rs.getInt("Anio"));           
        v.setTipo(rs.getString("Tipo"));        
        v.setColor(rs.getString("Color"));
        v.setIdCliente(rs.getLong("ID_Cliente"));
        return v;
    }

    private SeguroVehicular mapSeguro(ResultSet rs) throws SQLException {
        SeguroVehicular s = new SeguroVehicular();
        s.setId(rs.getLong("s_ID_Seguro"));
        s.setEliminado(rs.getBoolean("s_eliminado"));
        s.setIdVehiculo(rs.getLong("s_ID_Vehiculo"));
        s.setFechaInicio(rs.getDate("s_Fecha_Inicio").toLocalDate());
        s.setFechaFin(rs.getDate("s_Fecha_Fin").toLocalDate());
        s.setTipoSeguro(rs.getString("s_Tipo_Seguro"));
        s.setMontoAsegurado(rs.getBigDecimal("s_Monto_Asegurado"));
        s.setEstado(rs.getString("s_Estado"));
        return s;
    }

    // CREATE
    @Override
    public Vehiculo crear(Vehiculo t) throws Exception {
        try (Connection conn = newConnection()) {
            return crear(t, conn);
        }
    }

    @Override
    public Vehiculo crear(Vehiculo t, Connection conn) throws Exception {
        String sql = "INSERT INTO Vehiculo " +
                "(Patente, Marca, Modelo, Anio, Tipo, Color, ID_Cliente, eliminado) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getPatente());
            ps.setString(2, t.getMarca());
            ps.setString(3, t.getModelo());
            ps.setInt(4, t.getAnio());
            ps.setString(5, t.getTipo());              // 'auto'/'moto'/'camion'
            ps.setString(6, t.getColor());
            ps.setLong(7, t.getIdCliente());
            ps.setBoolean(8, t.isEliminado());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) t.setId(keys.getLong(1));
            }
            return t;
        }
    }

    // READ by id
    @Override
    public Optional<Vehiculo> leer(Long id) throws Exception {
        try (Connection conn = newConnection()) {
            return leer(id, conn);
        }
    }

    @Override
    public Optional<Vehiculo> leer(Long id, Connection conn) throws Exception {
        String sql = "SELECT * FROM Vehiculo WHERE ID_Vehiculo=? AND eliminado=FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapVehiculo(rs)) : Optional.empty();
            }
        }
    }

    @Override
    public List<Vehiculo> leerTodos() throws Exception {
        try (Connection conn = newConnection()) {
            return leerTodos(conn);
        }
    }

    @Override
    public List<Vehiculo> leerTodos(Connection conn) throws Exception {
        String sql = "SELECT * FROM Vehiculo WHERE eliminado=FALSE";
        List<Vehiculo> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapVehiculo(rs));
        }
        return list;
    }

    // UPDATE
    @Override
    public boolean actualizar(Vehiculo t) throws Exception {
        try (Connection conn = newConnection()) {
            return actualizar(t, conn);
        }
    }

    @Override
    public boolean actualizar(Vehiculo t, Connection conn) throws Exception {
        String sql = "UPDATE Vehiculo " +
                "SET Patente=?, Marca=?, Modelo=?, Anio=?, Tipo=?, Color=?, ID_Cliente=?, eliminado=? " +
                "WHERE ID_Vehiculo=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getPatente());
            ps.setString(2, t.getMarca());
            ps.setString(3, t.getModelo());
            ps.setInt(4, t.getAnio());
            ps.setString(5, t.getTipo());
            ps.setString(6, t.getColor());
            ps.setLong(7, t.getIdCliente());
            ps.setBoolean(8, t.isEliminado());
            ps.setLong(9, t.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarLogico(Long id) throws Exception {
        try (Connection conn = newConnection()) {
            return eliminarLogico(id, conn);
        }
    }

    @Override
    public boolean eliminarLogico(Long id, Connection conn) throws Exception {
        String sql = "UPDATE Vehiculo SET eliminado=TRUE WHERE ID_Vehiculo=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Vehiculo> buscarPorPatente(String patente) throws Exception {
        try (Connection conn = newConnection()) {
            String sql = "SELECT * FROM Vehiculo WHERE Patente=? AND eliminado=FALSE";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, patente);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() ? Optional.of(mapVehiculo(rs)) : Optional.empty();
                }
            }
        }
    }

    @Override
    public Optional<Vehiculo> leerConSeguro(Long id, Connection conn) throws Exception {
        String sql =
            "SELECT v.*, " +
            "       s.ID_Seguro      AS s_ID_Seguro, " +
            "       s.ID_Vehiculo    AS s_ID_Vehiculo, " +
            "       s.Fecha_Inicio   AS s_Fecha_Inicio, " +
            "       s.Fecha_Fin      AS s_Fecha_Fin, " +
            "       s.Tipo_Seguro    AS s_Tipo_Seguro, " +
            "       s.Monto_Asegurado AS s_Monto_Asegurado, " +
            "       s.Estado         AS s_Estado, " +
            "       s.eliminado      AS s_eliminado " +
            "FROM Vehiculo v " +
            "LEFT JOIN SeguroVehicular s ON s.ID_Vehiculo = v.ID_Vehiculo AND s.eliminado=FALSE " +
            "WHERE v.ID_Vehiculo=? AND v.eliminado=FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                Vehiculo v = mapVehiculo(rs);
                Long seguroId = rs.getLong("s_ID_Seguro");
                if (!rs.wasNull()) { // hay seguro
                    v.setSeguro(mapSeguro(rs));
                }
                return Optional.of(v);
            }
        }
    }
}
