package dao;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Nilus Global
 */
public interface GenericDao<T> {
    // CREATE
    T crear(T t) throws Exception;
    T crear(T t, Connection conn) throws Exception;

    // READ por id
    Optional<T> leer(Long id) throws Exception;
    Optional<T> leer(Long id, Connection conn) throws Exception;

    // READ todos
    List<T> leerTodos() throws Exception;
    List<T> leerTodos(Connection conn) throws Exception; // <-- agrega esta variante

    // UPDATE
    boolean actualizar(T t) throws Exception;
    boolean actualizar(T t, Connection conn) throws Exception;

    // DELETE lógico (según tu interfaz original)
    boolean eliminarLogico(Long id) throws Exception;
    boolean eliminarLogico(Long id, Connection conn) throws Exception;
}
