// ======================================================
// Controlador encargado de las funciones del administrador
// ======================================================
package controllers;

// ================= IMPORTACIONES =================

// Estructuras de datos
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Modelos del sistema
import models.Game;
import models.Purchase;
import models.User;

// Servicios
import services.AuthService;
import services.GameService;
import services.PurchaseService;

// Utilidades
import utils.InputHelper;
import utils.Printer;

// ======================================================
// CLASE ADMINCONTROLLER
// ======================================================
public class AdminController {

    // ================= ATRIBUTOS =================

    // Servicio encargado de gestionar videojuegos
    private final GameService gameService;

    // Servicio encargado de usuarios y autenticación
    private final AuthService authService;

    // Servicio encargado de compras y ventas
    private final PurchaseService purchaseService;

    // ================= CONSTRUCTOR =================

    /**
     * Constructor del controlador administrador
     */
    public AdminController(
            GameService gameService,
            AuthService authService,
            PurchaseService purchaseService
    ) {

        this.gameService = gameService;
        this.authService = authService;
        this.purchaseService = purchaseService;
    }

    // ======================================================
    // MENÚ PRINCIPAL DEL ADMINISTRADOR
    // ======================================================

    /**
     * Muestra el menú principal del administrador
     */
    public void mostrarMenuAdmin(User admin) {

        // Variable para mantener el menú activo
        boolean continuar = true;

        // Ciclo principal
        while (continuar) {

            // Mostrar título
            Printer.titulo(
                    "👑 PANEL DE ADMINISTRACIÓN — "
                            + admin.getUsername()
            );

            // ================= OPCIONES =================

            System.out.println("   ── Gestión de juegos ──────────────────");
            System.out.println("   [1] Ver catálogo");
            System.out.println("   [2] Agregar juego");
            System.out.println("   [3] Modificar precio");
            System.out.println("   [4] Modificar stock");
            System.out.println("   [5] Eliminar juego");

            System.out.println("   ── Gestión de usuarios ────────────────");
            System.out.println("   [6] Ver usuarios");

            System.out.println("   ── Estadísticas ───────────────────────");
            System.out.println("   [7] Ver ventas");
            System.out.println("   [8] Ver estadísticas");

            System.out.println("   [0] Salir");
            System.out.println();

            // Leer opción
            int opcion = InputHelper.leerEntero(
                    "   Elige una opción: "
            );

            // ================= SWITCH =================

            switch (opcion) {

                case 0:
                    continuar = false;
                    break;

                case 1:
                    verCatalogo();
                    break;

                case 2:
                    agregarJuego();
                    break;

                case 3:
                    modificarPrecio();
                    break;

                case 4:
                    modificarStock();
                    break;

                case 5:
                    eliminarJuego();
                    break;

                case 6:
                    verUsuarios();
                    break;

                case 7:
                    verVentas();
                    break;

                case 8:
                    verEstadisticas();
                    break;

                default:
                    Printer.error("Opción inválida.");
                    InputHelper.pausar();
            }
        }
    }

    // ======================================================
    // VER CATÁLOGO
    // ======================================================

    /**
     * Muestra todos los videojuegos registrados
     */
    private void verCatalogo() {

        Printer.titulo("📋 CATÁLOGO DE JUEGOS");

        // Obtener lista de juegos
        ArrayList<Game> juegos =
                gameService.getTodosLosJuegos();

        // Validar si no existen juegos
        if (juegos.isEmpty()) {

            Printer.info("No existen juegos registrados.");

        } else {

            // Encabezado de tabla
            System.out.printf(
                    " %-5s %-30s %-10s %-15s %-8s %s%n",
                    "ID",
                    "NOMBRE",
                    "PRECIO",
                    "CATEGORÍA",
                    "STOCK",
                    "RATING"
            );

            Printer.linea2();

            // Mostrar juegos
            for (Game juego : juegos) {
                System.out.println(juego);
            }
        }

        InputHelper.pausar();
    }

    // ======================================================
    // AGREGAR JUEGO
    // ======================================================

    /**
     * Permite agregar un nuevo videojuego
     */
    private void agregarJuego() {

        Printer.titulo("➕ AGREGAR VIDEOJUEGO");

        // Solicitar datos
        String nombre =
                InputHelper.leerTexto("   Nombre: ");

        double precio =
                InputHelper.leerDecimal("   Precio: $");

        String categoria =
                InputHelper.leerTexto("   Categoría: ");

        int stock =
                InputHelper.leerEntero("   Stock: ");

        String clasificacion =
                InputHelper.leerTexto(
                        "   Clasificación (E/T/M): "
                );

        // Llamar servicio
        String resultado = gameService.agregarJuego(
                nombre,
                precio,
                categoria,
                stock,
                clasificacion
        );

        // Separar estado y mensaje
        String[] datos = resultado.split(":", 2);

        // Mostrar resultado
        if ("OK".equals(datos[0])) {

            Printer.exito(datos[1]);

        } else {

            Printer.error(datos[1]);
        }

        InputHelper.pausar();
    }

    // ======================================================
    // MODIFICAR PRECIO
    // ======================================================

    /**
     * Modifica el precio de un videojuego
     */
    private void modificarPrecio() {

        Printer.titulo("✏ MODIFICAR PRECIO");

        // Solicitar datos
        int id =
                InputHelper.leerEntero(
                        "   ID del juego: "
                );

        double nuevoPrecio =
                InputHelper.leerDecimal(
                        "   Nuevo precio: $"
                );

        // Modificar precio
        String resultado =
                gameService.modificarPrecio(
                        id,
                        nuevoPrecio
                );

        // Separar resultado
        String[] datos = resultado.split(":", 2);

        // Mostrar mensaje
        if ("OK".equals(datos[0])) {

            Printer.exito(datos[1]);

        } else {

            Printer.error(datos[1]);
        }

        InputHelper.pausar();
    }

    // ======================================================
    // MODIFICAR STOCK
    // ======================================================

    /**
     * Modifica el stock de un videojuego
     */
    private void modificarStock() {

        Printer.titulo("📦 MODIFICAR STOCK");

        // Solicitar datos
        int id =
                InputHelper.leerEntero(
                        "   ID del juego: "
                );

        int nuevoStock =
                InputHelper.leerEntero(
                        "   Nuevo stock: "
                );

        // Modificar stock
        String resultado =
                gameService.modificarStock(
                        id,
                        nuevoStock
                );

        // Separar resultado
        String[] datos = resultado.split(":", 2);

        // Mostrar mensaje
        if ("OK".equals(datos[0])) {

            Printer.exito(datos[1]);

        } else {

            Printer.error(datos[1]);
        }

        InputHelper.pausar();
    }

    // ======================================================
    // ELIMINAR JUEGO
    // ======================================================

    /**
     * Elimina un videojuego
     */
    private void eliminarJuego() {

        Printer.titulo("🗑 ELIMINAR VIDEOJUEGO");

        // Solicitar ID
        int id =
                InputHelper.leerEntero(
                        "   ID del juego: "
                );

        // Confirmación
        String confirmacion =
                InputHelper.leerTexto(
                        "   ¿Seguro? (s/n): "
                );

        // Cancelar
        if (!confirmacion.equalsIgnoreCase("s")) {

            Printer.info("Operación cancelada.");
            InputHelper.pausar();
            return;
        }

        // Eliminar juego
        String resultado =
                gameService.eliminarJuego(id);

        // Separar resultado
        String[] datos = resultado.split(":", 2);

        // Mostrar mensaje
        if ("OK".equals(datos[0])) {

            Printer.exito(datos[1]);

        } else {

            Printer.error(datos[1]);
        }

        InputHelper.pausar();
    }

    // ======================================================
    // VER USUARIOS
    // ======================================================

    /**
     * Muestra todos los usuarios registrados
     */
    private void verUsuarios() {

        Printer.titulo("👥 USUARIOS REGISTRADOS");

        // Obtener usuarios
        ArrayList<User> usuarios =
                authService.getTodosLosUsuarios();

        // Encabezado
        System.out.printf(
                " %-20s %-10s %-10s%n",
                "USUARIO",
                "ROL",
                "SALDO"
        );

        Printer.linea2();

        // Mostrar usuarios
        for (User usuario : usuarios) {

            System.out.printf(
                    " %-20s %-10s $%.2f%n",
                    usuario.getUsername(),
                    usuario.getRol(),
                    usuario.getSaldo()
            );
        }

        Printer.linea2();

        // Total usuarios
        System.out.println(
                " Total de usuarios: "
                        + usuarios.size()
        );

        InputHelper.pausar();
    }

    // ======================================================
    // VER VENTAS
    // ======================================================

    /**
     * Muestra el historial de ventas
     */
    private void verVentas() {

        Printer.titulo("💵 HISTORIAL DE VENTAS");

        // Obtener compras
        ArrayList<Purchase> compras =
                purchaseService.getTodasLasCompras();

        // Validar lista vacía
        if (compras.isEmpty()) {

            Printer.info("No existen ventas.");

        } else {

            // Encabezado
            System.out.printf(
                    " %-15s %-30s %-10s %s%n",
                    "USUARIO",
                    "JUEGO",
                    "PRECIO",
                    "FECHA"
            );

            Printer.linea2();

            // Mostrar compras
            for (Purchase compra : compras) {

                System.out.printf(
                        " %-15s %-30s $%-9.2f %s%n",
                        compra.getUsername(),
                        compra.getGameName(),
                        compra.getPrecioCompra(),
                        compra.getFecha()
                );
            }

            Printer.linea2();

            // Total recaudado
            System.out.printf(
                    " TOTAL RECAUDADO: $%.2f%n",
                    purchaseService.calcularTotalVentas()
            );
        }

        InputHelper.pausar();
    }

    // ======================================================
    // VER ESTADÍSTICAS
    // ======================================================

    /**
     * Muestra estadísticas generales del sistema
     */
    private void verEstadisticas() {

        Printer.titulo("📊 ESTADÍSTICAS");

        // Obtener información general
        ArrayList<Purchase> compras =
                purchaseService.getTodasLasCompras();

        ArrayList<User> usuarios =
                authService.getTodosLosUsuarios();

        ArrayList<Game> juegos =
                gameService.getTodosLosJuegos();

        // Mapa para contar ventas por juego
        HashMap<String, Integer> ventasPorJuego =
                new HashMap<>();

        // Recorrer compras
        for (Purchase compra : compras) {

            String nombreJuego =
                    compra.getGameName();

            // Contar ventas
            ventasPorJuego.put(
                    nombreJuego,
                    ventasPorJuego.getOrDefault(
                            nombreJuego,
                            0
                    ) + 1
            );
        }

        // Mostrar estadísticas generales
        System.out.println(
                " Total de usuarios : "
                        + usuarios.size()
        );

        System.out.println(
                " Total de juegos   : "
                        + juegos.size()
        );

        System.out.println(
                " Total de ventas   : "
                        + compras.size()
        );

        System.out.printf(
                " Total recaudado   : $%.2f%n",
                purchaseService.calcularTotalVentas()
        );

        System.out.println();

        // Mostrar ventas por juego
        if (!ventasPorJuego.isEmpty()) {

            Printer.subtitulo(
                    "Ventas por juego"
            );

            for (Map.Entry<String, Integer> entry
                    : ventasPorJuego.entrySet()) {

                System.out.printf(
                        " %-30s %d venta(s)%n",
                        entry.getKey(),
                        entry.getValue()
                );
            }
        }

        InputHelper.pausar();
    }
}