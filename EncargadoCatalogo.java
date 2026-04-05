public class EncargadoCatalogo extends Usuario {
    public EncargadoCatalogo(String nombre, String username, String password) {
        super(nombre, username, password);
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n=== MENÚ ENCARGADO DE CATÁLOGO ===");
        System.out.println("1. Registrar nuevo cuadro");
        System.out.println("2. Registrar nueva escultura");
        System.out.println("3. Registrar nuevo objeto");
        System.out.println("4. Consultar obras registradas");
        System.out.println("5. Salir");
    }
}