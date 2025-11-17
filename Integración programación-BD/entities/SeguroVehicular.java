package entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Nilus Global
 */
public class SeguroVehicular {
    private Long id;
    private boolean eliminado;
    private Long idVehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String tipoSeguro;
    private BigDecimal montoAsegurado;
    private String estado;

    public SeguroVehicular() {}

    public SeguroVehicular(Long id, boolean eliminado, Long idVehiculo,
                           LocalDate fechaInicio, LocalDate fechaFin,
                           String tipoSeguro, BigDecimal montoAsegurado, String estado) {
        this.id = id;
        this.eliminado = eliminado;
        this.idVehiculo = idVehiculo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoSeguro = tipoSeguro;
        this.montoAsegurado = montoAsegurado;
        this.estado = estado;
    }

    public SeguroVehicular(Long idVehiculo, LocalDate fechaInicio, LocalDate fechaFin,
                           String tipoSeguro, BigDecimal montoAsegurado, String estado) {
        this(null, false, idVehiculo, fechaInicio, fechaFin, tipoSeguro, montoAsegurado, estado);
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
    public Long getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(Long idVehiculo) { this.idVehiculo = idVehiculo; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public String getTipoSeguro() { return tipoSeguro; }
    public void setTipoSeguro(String tipoSeguro) { this.tipoSeguro = tipoSeguro; }
    public BigDecimal getMontoAsegurado() { return montoAsegurado; }
    public void setMontoAsegurado(BigDecimal montoAsegurado) { this.montoAsegurado = montoAsegurado; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "SeguroVehicular{id=" + id + ", idVehiculo=" + idVehiculo + ", tipo='" + tipoSeguro + "', estado='" + estado + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeguroVehicular)) return false;
        SeguroVehicular s = (SeguroVehicular) o;
        return Objects.equals(id, s.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
