package service;


import dao.VehiculoDao;
import dao.SeguroVehicularDao;
import entities.Vehiculo;
import entities.SeguroVehicular;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import config.DatabaseConnection;

/**
 *
 * @author Nilus Global
 */
public class VehiculoService {
    private final VehiculoDao vehiculoDao;
    private final SeguroVehicularDao seguroDao;

    public VehiculoService(VehiculoDao vehiculoDao, SeguroVehicularDao seguroDao) {
        this.vehiculoDao = vehiculoDao;
        this.seguroDao = seguroDao;
    }

    // 1) Crear vehículo simple
    public Vehiculo crear(Vehiculo v) throws Exception {
        if (v == null) throw new Exception("Vehículo nulo");
        return vehiculoDao.crear(v);   // usa el DAO simple
    }

    // 2) Listar todos
    public List<Vehiculo> getAll() throws Exception {
        return vehiculoDao.leerTodos();
    }

    // 3) Buscar por patente
    public Optional<Vehiculo> buscarPorPatente(String patente) throws Exception {
        if (patente == null || patente.isBlank())
            throw new Exception("Patente inválida");

        return vehiculoDao.buscarPorPatente(patente);
    }

    // 4) Eliminar lógico
    public boolean eliminar(Long id) throws Exception {
        return vehiculoDao.eliminarLogico(id);
    }

    // 5) Crear vehículo + seguro
    public Vehiculo crearConSeguro(Vehiculo v, SeguroVehicular s) throws Exception {

        if (v == null) throw new Exception("Vehículo nulo");
        if (s == null) throw new Exception("Seguro nulo");

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            Vehiculo creado = vehiculoDao.crear(v, conn);

            boolean existe = seguroDao.existsByVehiculoId(creado.getId(), conn);
            if (existe) {
                conn.rollback();
                throw new Exception("Ese vehículo ya tiene seguro");
            }

            s.setIdVehiculo(creado.getId());
            seguroDao.crear(s, conn);

            conn.commit();
            creado.setSeguro(s);

            return creado;

        } catch (Exception e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); }
                catch (SQLException ignored) {}
            }
        }
    }
}
