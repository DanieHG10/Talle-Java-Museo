public class Visitante extends Usuario {
    public Visitante(String nombre, String username, String password) {
        super(nombre, username, password);
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n=== PANTALLA DEL VESTÍBULO (VISITANTE) ===");
        System.out.println("1. Consultar obras por sala (Catálogo)");
        System.out.println("2. Ver detalles de obra");
        System.out.println("3. Salir");
    }
}