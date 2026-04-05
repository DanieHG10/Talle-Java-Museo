package usuarios;

public class RestauradorJefe extends Usuario {
    public RestauradorJefe(String nombre, String username, String password) {
        super(nombre, username, password);
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n=== MENÚ RESTAURADOR JEFE ===");
        System.out.println("1. Ver obras que necesitan restauración");
        System.out.println("2. Iniciar restauración");
        System.out.println("3. Finalizar restauración");
        System.out.println("4. Consultar historial de restauraciones de una obra");
        System.out.println("5. Salir");
    }
}