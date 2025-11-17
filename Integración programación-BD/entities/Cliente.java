package entities;

import java.util.Objects;

/**
 *
 * @author Nilus Global
 */
public class Cliente {
private Long id;
    private boolean eliminado;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String direccion;

    public Cliente() {}

    public Cliente(Long id, boolean eliminado, String nombre, String apellido,
                   String dni, String email, String direccion) {
        this.id = id;
        this.eliminado = eliminado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.direccion = direccion;
    }

    public Cliente(String nombre, String apellido, String dni, String email, String direccion) {
        this(null, false, nombre, apellido, dni, email, direccion);
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nombre='" + nombre + "', apellido='" + apellido + "', dni='" + dni + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente c = (Cliente) o;
        return Objects.equals(id, c.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
