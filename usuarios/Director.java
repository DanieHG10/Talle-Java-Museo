package usuarios;

public class Director extends Usuario {
    public Director(String nombre, String username, String password) {
        super(nombre, username, password);
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n=== MENÚ DIRECTOR DEL MUSEO ===");
        System.out.println("1. Consultar valoración total de obras");
        System.out.println("2. Ceder obra a otro museo");
        System.out.println("3. Consultar cesiones vigentes");
        System.out.println("4. Registrar museo colaborador");
        System.out.println("5. Salir");
    }
}