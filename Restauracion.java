import java.time.LocalDate;
import java.util.UUID;

public class Restauracion {
    private String id;
    private ObraDeArte obra;
    private TipoRestauracion tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Restauracion(ObraDeArte obra, TipoRestauracion tipo) {
        this.id = UUID.randomUUID().toString().substring(0, 6);
        this.obra = obra;
        this.tipo = tipo;
        this.fechaInicio = LocalDate.now();
        this.fechaFin = null;
    }

    public void finalizar() {
        this.fechaFin = LocalDate.now();
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void imprimirInfo() {
        String fin = (fechaFin != null) ? fechaFin.toString() : "En progreso";
        System.out.println("Restauración [" + id + "] | Tipo: " + tipo + " | Inicio: " + fechaInicio + " | Fin: " + fin);
    }
}