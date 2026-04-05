
import enums.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import obras.*;
import usuarios.*;

public class SistemaMuseo {
    
    private List<ObraDeArte> obras;
    private List<Restauracion> restauracionesActivas;
    private List<Cesion> cesiones;
    private List<String> museosColaboradores;
    private List<Usuario> usuarios;
    private Scanner scanner;

    public SistemaMuseo() {
        obras = new ArrayList<>();
        restauracionesActivas = new ArrayList<>();
        cesiones = new ArrayList<>();
        museosColaboradores = new ArrayList<>();
        usuarios = new ArrayList<>();
        scanner = new Scanner(System.in);
        
        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        usuarios.add(new EncargadoCatalogo("Juan", "catalogo", "123"));
        usuarios.add(new RestauradorJefe("Maria", "restaurador", "123"));
        usuarios.add(new Director("Carlos", "director", "123"));
        usuarios.add(new Visitante("Publico", "visitante", ""));
        
        museosColaboradores.add("Museo Universitario de La Salle");
        museosColaboradores.add("Museo Nacional");
    }

    public void iniciar() {
        System.out.println("=========================================");
        System.out.println(" SISTEMA DE GESTIÓN DE OBRAS DE ARTE ");
        System.out.println("=========================================");
        
        while (true) {
            System.out.println("\n1. Iniciar Sesión");
            System.out.println("2. Salir");
            System.out.print("Opción: ");
            String opc = scanner.nextLine();

            if (opc.equals("1")) {
                login();
            } else if (opc.equals("2")) {
                System.out.println("Saliendo del sistema...");
                break;
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    private void login() {
        System.out.print("Usuario: ");
        String user = scanner.nextLine();
        System.out.print("Contraseña (dejar vacío si es visitante): ");
        String pass = scanner.nextLine();

        Usuario usuarioLogueado = null;
        for (Usuario u : usuarios) {
            if (u.autenticar(user, pass)) {
                usuarioLogueado = u;
                break;
            }
        }

        if (usuarioLogueado != null) {
            System.out.println("\n¡Bienvenido, " + usuarioLogueado.getNombre() + "!");
            enrutarMenu(usuarioLogueado);
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }

    private void enrutarMenu(Usuario u) {
        while (true) {
            u.mostrarMenu();
            System.out.print("Seleccione una opción: ");
            String opc = scanner.nextLine();

            if (opc.equals("5") || (u instanceof Visitante && opc.equals("3"))) {
                System.out.println("Cerrando sesión...");
                break;
            }

            if (u instanceof EncargadoCatalogo) {
                logicaEncargado(opc);
            } else if (u instanceof RestauradorJefe) {
                logicaRestaurador(opc);
            } else if (u instanceof Director) {
                logicaDirector(opc);
            } else if (u instanceof Visitante) {
                logicaVisitante(opc);
            }
        }
    }

    private void logicaEncargado(String opc) {
        if (opc.equals("4")) {
            mostrarObras();
            return;
        }

        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Periodo: ");
        String periodo = scanner.nextLine();
        double valor = leerDouble("Valor ($): ");
        LocalDate fechaC = leerFecha("Fecha de creación (DD/MM/YYYY): ");
        LocalDate fechaE = leerFecha("Fecha de entrada al museo (DD/MM/YYYY): ");

        switch (opc) {
            case "1":
                System.out.print("Estilo: ");
                String estC = scanner.nextLine();
                System.out.print("Técnica: ");
                String tec = scanner.nextLine();
                obras.add(new Cuadro(autor, periodo, valor, fechaC, fechaE, estC, tec));
                System.out.println("Cuadro registrado con éxito.");
                break;
            case "2":
                System.out.print("Estilo: ");
                String estE = scanner.nextLine();
                System.out.print("Material: ");
                String mat = scanner.nextLine();
                obras.add(new Escultura(autor, periodo, valor, fechaC, fechaE, estE, mat));
                System.out.println("Escultura registrada con éxito.");
                break;
            case "3":
                System.out.print("Descripción: ");
                String desc = scanner.nextLine();
                obras.add(new Objeto(autor, periodo, valor, fechaC, fechaE, desc));
                System.out.println("Objeto registrado con éxito.");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void logicaRestaurador(String opc) {
        switch (opc) {
            case "1":
                System.out.println("\n--- Obras que requieren restauración automática ---");
                boolean hay = false;
                for (ObraDeArte obra : obras) {
                    if (obra.getEstado() == EstadoObra.EXPUESTA && obra.necesitaRestauracionAutomatica()) {
                        System.out.println("- " + obra.getIdObra() + " de " + obra.getAutor());
                        hay = true;
                    }
                }
                if (!hay) System.out.println("Ninguna obra necesita mantenimiento actualmente.");
                break;
            case "2":
                System.out.print("Ingrese ID de la obra a restaurar: ");
                String idBusqueda = scanner.nextLine();
                ObraDeArte obraRest = buscarObraPorId(idBusqueda);
                if (obraRest != null && obraRest.getEstado() == EstadoObra.EXPUESTA) {
                    obraRest.setEstado(EstadoObra.EN_RESTAURACION);
                    Restauracion r = new Restauracion(obraRest, TipoRestauracion.MANTENIMIENTO); 
                    obraRest.agregarRestauracion(r);
                    restauracionesActivas.add(r);
                    System.out.println("Restauración iniciada.");
                } else {
                    System.out.println("Obra no encontrada o no está expuesta.");
                }
                break;
            case "3":
                System.out.print("Ingrese ID de la obra para finalizar restauración: ");
                String idFin = scanner.nextLine();
                ObraDeArte obraFin = buscarObraPorId(idFin);
                if (obraFin != null && obraFin.getEstado() == EstadoObra.EN_RESTAURACION) {
                    obraFin.setEstado(EstadoObra.EXPUESTA);
                    Restauracion rUltima = obraFin.getHistorialRestauraciones().get(obraFin.getHistorialRestauraciones().size()-1);
                    rUltima.finalizar();
                    restauracionesActivas.remove(rUltima);
                    System.out.println("Restauración finalizada con éxito.");
                } else {
                    System.out.println("La obra no está en restauración.");
                }
                break;
            case "4":
                System.out.print("Ingrese ID de la obra: ");
                ObraDeArte obraHist = buscarObraPorId(scanner.nextLine());
                if (obraHist != null) {
                    System.out.println("\nHistorial de restauraciones:");
                    for (Restauracion res : obraHist.getHistorialRestauraciones()) {
                        res.imprimirInfo();
                    }
                }
                break;
        }
    }

    private void logicaDirector(String opc) {
        switch (opc) {
            case "1":
                double total = 0;
                for (ObraDeArte o : obras) {
                    if(o.getEstado() == EstadoObra.EXPUESTA) total += o.getValor();
                }
                System.out.println("\nValoración total de obras expuestas: $" + String.format("%.2f", total));
                break;
            case "2":
                System.out.print("Ingrese ID de la obra a ceder: ");
                ObraDeArte obraCed = buscarObraPorId(scanner.nextLine());
                if (obraCed != null && obraCed.puedeSerCedida()) {
                    System.out.println("Museos disponibles:");
                    for(int i=0; i<museosColaboradores.size(); i++) {
                        System.out.println((i+1) + ". " + museosColaboradores.get(i));
                    }
                    System.out.print("Seleccione número del museo: ");
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    double importe = leerDouble("Importe de cesión: ");
                    LocalDate fechaF = leerFecha("Fecha fin (DD/MM/YYYY): ");
                    
                    Cesion nuevaCesion = new Cesion(obraCed, museosColaboradores.get(idx), importe, fechaF);
                    obraCed.setCesionVigente(nuevaCesion);
                    cesiones.add(nuevaCesion);
                    System.out.println("Cesión registrada correctamente.");
                } else {
                    System.out.println("La obra no está disponible para cesión.");
                }
                break;
            case "3":
                System.out.println("\n--- Cesiones Vigentes ---");
                for (Cesion c : cesiones) {
                    if (c.estaVigente()) c.imprimirInfo();
                }
                break;
            case "4":
                System.out.print("Nombre del nuevo museo colaborador: ");
                museosColaboradores.add(scanner.nextLine());
                System.out.println("Museo registrado.");
                break;
        }
    }

    private void logicaVisitante(String opc) {
        if (opc.equals("1")) {
            mostrarObras();
        } else if (opc.equals("2")) {
            System.out.print("Ingrese ID de la obra para ver detalles: ");
            ObraDeArte o = buscarObraPorId(scanner.nextLine());
            if (o != null) {
                o.imprimirInfoCompleta();
            } else {
                System.out.println("Obra no encontrada.");
            }
        }
    }
    
    private void mostrarObras() {
        if(obras.isEmpty()){
            System.out.println("No hay obras registradas.");
            return;
        }
        System.out.println("\n--- CATÁLOGO DE OBRAS ---");
        for (ObraDeArte o : obras) {
            System.out.println(o.getIdObra() + " | " + o.getAutor() + " | Estado: " + o.getEstado());
        }
    }

    private ObraDeArte buscarObraPorId(String id) {
        for (ObraDeArte o : obras) {
            if (o.getIdObra().equals(id)) return o;
        }
        return null;
    }

    private double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }
    }

    private LocalDate leerFecha(String mensaje) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            try {
                System.out.print(mensaje);
                return LocalDate.parse(scanner.nextLine(), formato);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Use el formato DD/MM/YYYY.");
            }
        }
    }

    public static void main(String[] args) {    
        SistemaMuseo sistema = new SistemaMuseo();
        sistema.iniciar();
    }
}