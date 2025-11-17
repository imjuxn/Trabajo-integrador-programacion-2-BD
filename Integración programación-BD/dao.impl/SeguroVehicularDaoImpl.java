package dao.impl;

import dao.SeguroVehicularDao;
import entities.SeguroVehicular;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author Nilus Global
 */
public class SeguroVehicularDaoImpl extends AbstractDao implements SeguroVehicularDao {

    private SeguroVehicular map(ResultSet rs) throws SQLException {
        SeguroVehicular s = new SeguroVehicular();
        s.setId(rs.getLong("ID_Seguro"));
        s.setEliminado(rs.getBoolean("eliminado"));
        s.setIdVehiculo(rs.getLong("ID_Vehiculo"));
        s.setFechaInicio(rs.getDate("Fecha_Inicio").toLocalDate());
        s.setFechaFin(rs.getDate("Fecha_Fin").toLocalDate());
        s.setTipoSeguro(rs.getString("Tipo_Seguro"));       
        s.setMontoAsegurado(rs.getBigDecimal("Monto_Asegurado"));
        s.setEstado(rs.getString("Estado"));                
        return s;
    }

    
    @Override
    public SeguroVehicular crear(SeguroVehicular t) throws Exception {
        try (Connection conn = newConnection()) {
            return crear(t, conn);
        }
    }

    @Override
    public SeguroVehicular crear(SeguroVehicular t, Connection conn) throws Exception {
        String sql = "INSERT INTO SeguroVehicular " +
                "(ID_Vehiculo, Fecha_Inicio, Fecha_Fin, Tipo_Seguro, Monto_Asegurado, Estado, eliminado) " +
                "VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, t.getIdVehiculo());
            ps.setDate(2, Date.valueOf(t.getFechaInicio()));
            ps.setDate(3, Date.valueOf(t.getFechaFin()));
            ps.setString(4, t.getTipoSeguro());
            ps.setBigDecimal(5, t.getMontoAsegurado());
            ps.setString(6, t.getEstado());
            ps.setBoolean(7, t.isEliminado());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) t.setId(keys.getLong(1));
            }
            return t;
        }
    }

    @Override
    public Optional<SeguroVehicular> leer(Long id) throws Exception {
        try (Connection conn = newConnection()) {
            return leer(id, conn);
        }
    }

    @Override
    public Optional<SeguroVehicular> leer(Long id, Connection conn) throws Exception {
        String sql = "SELECT * FROM SeguroVehicular WHERE ID_Seguro=? AND eliminado=FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        }
    }

    @Override
    public List<SeguroVehicular> leerTodos() throws Exception {
        try (Connection conn = newConnection()) {
            return leerTodos(conn);
        }
    }

    @Override
    public List<SeguroVehicular> leerTodos(Connection conn) throws Exception {
        String sql = "SELECT * FROM SeguroVehicular WHERE eliminado=FALSE";
        List<SeguroVehicular> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    // UPDATE
    @Override
    public boolean actualizar(SeguroVehicular t) throws Exception {
        try (Connection conn = newConnection()) {
            return actualizar(t, conn);
        }
    }

    @Override
    public boolean actualizar(SeguroVehicular t, Connection conn) throws Exception {
        String sql = "UPDATE SeguroVehicular " +
                "SET ID_Vehiculo=?, Fecha_Inicio=?, Fecha_Fin=?, Tipo_Seguro=?, Monto_Asegurado=?, Estado=?, eliminado=? " +
                "WHERE ID_Seguro=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, t.getIdVehiculo());
            ps.setDate(2, Date.valueOf(t.getFechaInicio()));
            ps.setDate(3, Date.valueOf(t.getFechaFin()));
            ps.setString(4, t.getTipoSeguro());
            ps.setBigDecimal(5, t.getMontoAsegurado());
            ps.setString(6, t.getEstado());
            ps.setBoolean(7, t.isEliminado());
            ps.setLong(8, t.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // DELETE lógico
    @Override
    public boolean eliminarLogico(Long id) throws Exception {
        try (Connection conn = newConnection()) {
            return eliminarLogico(id, conn);
        }
    }

    @Override
    public boolean eliminarLogico(Long id, Connection conn) throws Exception {
        String sql = "UPDATE SeguroVehicular SET eliminado=TRUE WHERE ID_Seguro=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean existsByVehiculoId(Long idVehiculo, Connection conn) throws Exception {
        String sql = "SELECT COUNT(*) FROM SeguroVehicular WHERE ID_Vehiculo=? AND eliminado=FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idVehiculo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getLong(1) > 0;
            }
        }
    }

    @Override
    public Optional<SeguroVehicular> leerPorVehiculoId(Long idVehiculo, Connection conn) throws Exception {
        String sql = "SELECT * FROM SeguroVehicular WHERE ID_Vehiculo=? AND eliminado=FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idVehiculo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        }
    }
}
