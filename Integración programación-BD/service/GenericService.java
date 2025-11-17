package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nilus Global
 */
public interface GenericService<T> {
    T crear(T t) throws SQLException;
    Optional<T> leer(Long id) throws SQLException;
    List<T> getAll() throws SQLException;
    boolean actualizar(T t) throws SQLException;
    boolean eliminar(Long id) throws SQLException; // eliminado lógico
}
