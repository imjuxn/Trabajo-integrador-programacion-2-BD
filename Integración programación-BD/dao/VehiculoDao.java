package dao;

import entities.Vehiculo;
import java.sql.Connection;
import java.util.Optional;

/**
 *
 * @author Nilus Global
 */
public interface VehiculoDao extends GenericDao<Vehiculo> {
    Optional<Vehiculo> buscarPorPatente(String patente) throws Exception;
    Optional<Vehiculo> leerConSeguro(Long id, Connection conn) throws Exception;
}
