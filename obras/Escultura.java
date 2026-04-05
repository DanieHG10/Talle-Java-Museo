package obras;

import java.time.LocalDate;

public class Escultura extends ObraDeArte {
    private String estilo;
    private String material;

    public Escultura(String autor, String periodo, double valor, LocalDate fechaCreacion, LocalDate fechaEntrada, String estilo, String material) {
        super(autor, periodo, valor, fechaCreacion, fechaEntrada);
        this.estilo = estilo;
        this.material = material;
    }

    @Override
    public String obtenerInfoEspecifica() {
        return "Tipo: ESCULTURA\nEstilo: " + estilo + "\nMaterial: " + material;
    }

    @Override
    public boolean puedeSerCedida1() {
        throw new UnsupportedOperationException("Unimplemented method 'puedeSerCedida1'");
    }

    @Override
    public boolean puedeSerCedida11() {
        throw new UnsupportedOperationException("Unimplemented method 'puedeSerCedida11'");
    }
}