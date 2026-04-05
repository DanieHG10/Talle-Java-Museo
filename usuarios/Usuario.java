package usuarios;

public abstract class Usuario {
    protected String nombre;
    protected String username;
    protected String password;

    public Usuario(String nombre, String username, String password) {
        this.nombre = nombre;
        this.username = username;
        this.password = password;
    }

    public boolean autenticar(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getNombre() {
        return nombre;
    }

    public abstract void mostrarMenu();
}