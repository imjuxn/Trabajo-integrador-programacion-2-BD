package entities;

import java.util.Objects;

/**
 *
 * @author Nilus Global
 */
public class Vehiculo {
    private Long id;
    private boolean eliminado;
    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private String tipo;
    private String color;
    private Long idCliente;
    private SeguroVehicular seguro;

    public Vehiculo() {}

    public Vehiculo(Long id, boolean eliminado, String patente, String marca, String modelo,
                    Integer anio, String tipo, String color, Long idCliente, SeguroVehicular seguro) {
        this.id = id;
        this.eliminado = eliminado;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.tipo = tipo;
        this.color = color;
        this.idCliente = idCliente;
        this.seguro = seguro;
    }

    public Vehiculo(String patente, String marca, String modelo, Integer anio,
                    String tipo, String color, Long idCliente) {
        this(null, false, patente, marca, modelo, anio, tipo, color, idCliente, null);
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public SeguroVehicular getSeguro() { return seguro; }
    public void setSeguro(SeguroVehicular seguro) { this.seguro = seguro; }

    @Override
    public String toString() {
        return "Vehiculo{id=" + id + ", patente='" + patente + "', marca='" + marca + "', modelo='" + modelo + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehiculo)) return false;
        Vehiculo v = (Vehiculo) o;
        return Objects.equals(id, v.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
