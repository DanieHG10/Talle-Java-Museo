import java.time.LocalDate;
import java.util.UUID;

public class Cesion {
    private String id;
    private ObraDeArte obra;
    private String museoDestino;
    private double importe;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Cesion(ObraDeArte obra, String museoDestino, double importe, LocalDate fechaFin) {
        this.id = UUID.randomUUID().toString().substring(0, 6);
        this.obra = obra;
        this.museoDestino = museoDestino;
        this.importe = importe;
        this.fechaInicio = LocalDate.now();
        this.fechaFin = fechaFin;
    }

    public boolean estaVigente() {
        return LocalDate.now().isBefore(fechaFin) || LocalDate.now().isEqual(fechaFin);
    }

    public void imprimirInfo() {
        String estado = estaVigente() ? "Vigente" : "Finalizada";
        System.out.println("Cesión [" + id + "] | Obra: " + obra.getIdObra() + " | Destino: " + museoDestino + " | Estado: " + estado);
    }
}
