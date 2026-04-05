import java.time.LocalDate;

public class Objeto extends ObraDeArte {
    private String descripcion;

    public Objeto(String autor, String periodo, double valor, LocalDate fechaCreacion, LocalDate fechaEntrada, String descripcion) {
        super(autor, periodo, valor, fechaCreacion, fechaEntrada);
        this.descripcion = descripcion;
    }

    @Override
    public String obtenerInfoEspecifica() {
        return "Tipo: OTRO OBJETO\nDescripción: " + descripcion;
    }
}