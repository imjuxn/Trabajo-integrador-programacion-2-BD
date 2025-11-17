package dao;

import entities.SeguroVehicular;
import java.sql.Connection;
import java.util.Optional;

/**
 *
 * @author Nilus Global
 */
public interface SeguroVehicularDao extends GenericDao<SeguroVehicular> {
    boolean existsByVehiculoId(Long idVehiculo, Connection conn) throws Exception;
    Optional<SeguroVehicular> leerPorVehiculoId(Long idVehiculo, Connection conn) throws Exception;
}
