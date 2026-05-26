package moduloAdmin;

import java.util.Map;
import java.util.Scanner;

// IMPORT FUTURO
// import moduloTienda.TiendaControlador;

public class AdminControlador {

    private Map<String, String> usuarios;
    private Map<String, Double> saldos;

    // FUTURO:
    // Aqui irá el catálogo de videojuegos
    // private Map<String, Juego> juegos;

    private Scanner scanner;

    // FUTURO:
    // private TiendaControlador tienda;

    public AdminControlador(
            Map<String, String> usuarios,
            Map<String, Double> saldos
    ) {

        this.usuarios = usuarios;
        this.saldos = saldos;

        // FUTURO:
        // this.juegos = juegos;
        // this.tienda = tienda;

        this.scanner = new Scanner(System.in);
    }

    public void menuAdmin() {

        boolean salir = false;

        while (!salir) {

            System.out.println("\n=== PANEL ADMIN ===");
            System.out.println("1. Ver usuarios");
            System.out.println("2. Eliminar usuario");
            System.out.println("3. Ver saldo de un usuario");

            // RF-19
            System.out.println("4. Agregar videojuego");

            // RF-20
            System.out.println("5. Modificar videojuego");

            // RF-21
            System.out.println("6. Eliminar videojuego");

            // RF-23
            System.out.println("7. Ver estadísticas");

            // FUTURO
            // System.out.println("8. Ir a tienda");

            System.out.println("9. Salir");

            System.out.print("Seleccione: ");

            String opcion = scanner.nextLine();

            switch (opcion) {

                case "1":
                    verUsuarios();
                    break;

                case "2":
                    eliminarUsuario();
                    break;

                case "3":
                    verSaldo();
                    break;

                // RF-19
                case "4":
                    agregarVideojuego();
                    break;

                // RF-20
                case "5":
                    modificarVideojuego();
                    break;

                // RF-21
                case "6":
                    eliminarVideojuego();
                    break;

                // RF-23
                case "7":
                    verEstadisticas();
                    break;

                // FUTURO
                /*
                case "8":
                    tienda.menuUsuario();
                    break;
                 */

                case "9":
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // RF-22
    private void verUsuarios() {

        System.out.println("\n--- LISTA DE USUARIOS ---");

        if (usuarios.isEmpty()) {

            System.out.println("No hay usuarios registrados.");
            return;
        }

        int i = 1;

        for (String user : usuarios.keySet()) {

            System.out.println(i + ". " + user);
            i++;
        }
    }

    private void eliminarUsuario() {

        verUsuarios();

        System.out.print("\nNombre del usuario a eliminar: ");
        String user = scanner.nextLine();

        if (!usuarios.containsKey(user)) {

            System.out.println("Error: Usuario no encontrado.");
            return;
        }

        // VALIDACION IMPORTANTE
        if (user.equals("Admin")) {

            System.out.println("No se puede eliminar al administrador.");
            return;
        }

        usuarios.remove(user);
        saldos.remove(user);

        System.out.println("Usuario '" + user + "' eliminado.");
    }

    private void verSaldo() {

        System.out.print("Nombre del usuario: ");
        String user = scanner.nextLine();

        if (!saldos.containsKey(user)) {

            System.out.println("Error: Usuario no encontrado.");
            return;
        }

        System.out.println(
                "Saldo de " + user + ": $"
                        + String.format("%.2f",
                        saldos.get(user))
        );
    }

    // =====================================================
    // RF-19 — AGREGAR VIDEOJUEGO
    // =====================================================

    private void agregarVideojuego() {

        System.out.println("\n--- AGREGAR VIDEOJUEGO ---");

        // FUTURO:
        /*
        Aqui se pedirá:
        - nombre
        - precio
        - stock
        - categoria

        Luego se agregará al catálogo.

        Ejemplo futuro:

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        juegos.put(nombre, nuevoJuego);
        */

        System.out.println("Módulo aún en desarrollo.");
    }

    // =====================================================
    // RF-20 — MODIFICAR VIDEOJUEGO
    // =====================================================

    private void modificarVideojuego() {

        System.out.println("\n--- MODIFICAR VIDEOJUEGO ---");

        // FUTURO:
        /*
        Aqui se podrá modificar:
        - precio
        - stock
        - categoria

        Flujo futuro:
        1. Buscar juego
        2. Mostrar datos
        3. Editar campos
        4. Guardar cambios
        */

        System.out.println("Módulo aún en desarrollo.");
    }

    // =====================================================
    // RF-21 — ELIMINAR VIDEOJUEGO
    // =====================================================

    private void eliminarVideojuego() {

        System.out.println("\n--- ELIMINAR VIDEOJUEGO ---");

        // FUTURO:
        /*
        Aqui se eliminará un juego
        del catálogo principal.

        Ejemplo futuro:

        juegos.remove(nombreJuego);
        */

        System.out.println("Módulo aún en desarrollo.");
    }

    // =====================================================
    // RF-23 — VER ESTADISTICAS
    // =====================================================

    private void verEstadisticas() {

        System.out.println("\n--- ESTADÍSTICAS DEL SISTEMA ---");

        // FUTURO:
        /*
        Aqui se mostrarán estadísticas como:

        - Total de usuarios
        - Total de videojuegos
        - Juegos más vendidos
        - Usuario con más compras
        - Total de ventas
        - Saldo total del sistema
        */

        System.out.println("Módulo aún en desarrollo.");
    }
}