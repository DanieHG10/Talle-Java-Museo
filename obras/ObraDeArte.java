package obras;

import enums.EstadoObra;
import interfaces.ICedible;
import interfaces.IRestaurable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class ObraDeArte implements IRestaurable, ICedible {
    protected String idObra;
    protected String autor;
    protected String periodo;
    protected double valor;
    protected LocalDate fechaCreacion;
    protected LocalDate fechaEntrada;
    protected EstadoObra estado;
    
    protected List<Restauracion> historialRestauraciones;
    protected Cesion cesionVigente;

    public ObraDeArte(String autor, String periodo, double valor, LocalDate fechaCreacion, LocalDate fechaEntrada) {
        this.idObra = UUID.randomUUID().toString().substring(0, 8);
        this.autor = autor;
        this.periodo = periodo;
        this.valor = valor;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntrada = fechaEntrada;
        this.estado = EstadoObra.EXPUESTA;
        this.historialRestauraciones = new ArrayList<>();
        this.cesionVigente = null;
    }

    public abstract String obtenerInfoEspecifica();

    public void imprimirInfoCompleta() {
        System.out.println("\n=== INFORMACIÓN DE LA OBRA ===");
        System.out.println("ID: " + idObra);
        System.out.println("Autor: " + autor);
        System.out.println("Período: " + periodo);
        System.out.println("Valor: $" + String.format("%.2f", valor));
        System.out.println("Estado: " + estado);
        System.out.println(obtenerInfoEspecifica());
    }

    @Override
    public boolean necesitaRestauracionAutomatica() {
        if (historialRestauraciones.isEmpty()) {
            long diasDesdeEntrada = ChronoUnit.DAYS.between(fechaEntrada, LocalDate.now());
            return diasDesdeEntrada >= (365 * 5); 
        } else {
            Restauracion ultima = historialRestauraciones.get(historialRestauraciones.size() - 1);
            if (ultima.getFechaFin() != null) {
                long diasDesdeUltima = ChronoUnit.DAYS.between(ultima.getFechaFin(), LocalDate.now());
                return diasDesdeUltima >= (365 * 5);
            }
        }
        return false;
    }

    @Override
    public boolean puedeSerCedida() {
        return this.estado == EstadoObra.EXPUESTA && this.cesionVigente == null;
    }

    public String getIdObra() { return idObra; }
    public String getAutor() { return autor; }
    public double getValor() { return valor; }
    public EstadoObra getEstado() { return estado; }
    public void setEstado(EstadoObra estado) { this.estado = estado; }
    public List<Restauracion> getHistorialRestauraciones() { return historialRestauraciones; }
    public void agregarRestauracion(Restauracion r) { this.historialRestauraciones.add(r); }
    public void setCesionVigente(Cesion c) { this.cesionVigente = c; }
}