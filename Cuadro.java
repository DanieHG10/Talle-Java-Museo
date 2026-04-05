import java.time.LocalDate;

public class Cuadro extends ObraDeArte {
    private String estilo;
    private String tecnica;

    public Cuadro(String autor, String periodo, double valor, LocalDate fechaCreacion, LocalDate fechaEntrada, String estilo, String tecnica) {
        super(autor, periodo, valor, fechaCreacion, fechaEntrada);
        this.estilo = estilo;
        this.tecnica = tecnica;
    }

    @Override
    public String obtenerInfoEspecifica() {
        return "Tipo: CUADRO\nEstilo: " + estilo + "\nTécnica: " + tecnica;
    }
}