package controllers;

import models.Game;
import models.Purchase;
import models.User;
import services.AuthService;
import services.GameService;
import services.PurchaseService;
import utils.InputHelper;
import utils.Printer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controlador del panel de administración.
 * Solo accesible para usuarios con rol ADMIN.
 */
public class AdminController {

    private GameService     gameService;
    private AuthService     authService;
    private PurchaseService purchaseService;

    // ── Constructor ────────────────────────────────────────────────────────────

    public AdminController(GameService gameService, AuthService authService,
                           PurchaseService purchaseService) {
        this.gameService     = gameService;
        this.authService     = authService;
        this.purchaseService = purchaseService;
    }

    // ── Menú principal admin ───────────────────────────────────────────────────

    public void mostrarMenuAdmin(User admin) {
        boolean enAdmin = true;

        while (enAdmin) {
            Printer.titulo("👑 PANEL DE ADMINISTRACIÓN — " + admin.getUsername());
            System.out.println("   ── Gestión de juegos ──────────────────");
            System.out.println("   [1] Ver catálogo");
            System.out.println("   [2] Agregar juego");
            System.out.println("   [3] Modificar precio de juego");
            System.out.println("   [4] Modificar stock de juego");
            System.out.println("   [5] Eliminar juego");
            System.out.println("   ── Gestión de usuarios ─────────────────");
            System.out.println("   [6] Ver todos los usuarios");
            System.out.println("   ── Estadísticas ────────────────────────");
            System.out.println("   [7] Ver ventas totales");
            System.out.println("   [8] Estadísticas del sistema");
            System.out.println("   [0] Volver");
            System.out.println();

            int opcion = InputHelper.leerEntero("   Elige una opción: ");

            switch (opcion) {
                case 1: verCatalogo();          break;
                case 2: agregarJuego();         break;
                case 3: modificarPrecio();      break;
                case 4: modificarStock();       break;
                case 5: eliminarJuego();        break;
                case 6: verUsuarios();          break;
                case 7: verVentas();            break;
                case 8: verEstadisticas();      break;
                case 0: enAdmin = false;        break;
                default:
                    Printer.aviso("Opción no válida.");
                    InputHelper.pausar();
            }
        }
    }

    // ── Ver catálogo ───────────────────────────────────────────────────────────

    private void verCatalogo() {
        Printer.titulo("📋 CATÁLOGO DE JUEGOS");
        ArrayList<Game> juegos = gameService.getTodosLosJuegos();
        if (juegos.isEmpty()) {
            Printer.info("No hay juegos registrados.");
        } else {
            System.out.printf("  %-6s %-30s %-10s %-15s %-8s %s%n",
                    "ID", "NOMBRE", "PRECIO", "CATEGORÍA", "STOCK", "RATING");
            Printer.linea2();
            for (Game g : juegos) {
                System.out.println(g.toString());
            }
        }
        InputHelper.pausar();
    }

    // ── Agregar juego ──────────────────────────────────────────────────────────

    private void agregarJuego() {
        Printer.titulo("➕ AGREGAR JUEGO");
        String nombre        = InputHelper.leerTexto("   Nombre: ");
        double precio        = InputHelper.leerDecimal("   Precio: $");
        String categoria     = InputHelper.leerTexto("   Categoría: ");
        int    stock         = InputHelper.leerEntero("   Stock inicial: ");
        String clasificacion = InputHelper.leerTexto("   Clasificación (E/T/M): ");

        String resultado = gameService.agregarJuego(nombre, precio, categoria, stock, clasificacion);
        String[] partes  = resultado.split(":", 2);

        if ("OK".equals(partes[0])) {
            Printer.exito(partes[1]);
        } else {
            Printer.error(partes[1]);
        }
        InputHelper.pausar();
    }

    // ── Modificar precio ───────────────────────────────────────────────────────

    private void modificarPrecio() {
        Printer.titulo("✏ MODIFICAR PRECIO");
        int    id         = InputHelper.leerEntero("   ID del juego: ");
        double nuevoPrecio = InputHelper.leerDecimal("   Nuevo precio: $");

        String resultado = gameService.modificarPrecio(id, nuevoPrecio);
        String[] partes  = resultado.split(":", 2);
        if ("OK".equals(partes[0])) Printer.exito(partes[1]);
        else                         Printer.error(partes[1]);
        InputHelper.pausar();
    }

    // ── Modificar stock ────────────────────────────────────────────────────────

    private void modificarStock() {
        Printer.titulo("📦 MODIFICAR STOCK");
        int id         = InputHelper.leerEntero("   ID del juego: ");
        int nuevoStock = InputHelper.leerEntero("   Nuevo stock: ");

        String resultado = gameService.modificarStock(id, nuevoStock);
        String[] partes  = resultado.split(":", 2);
        if ("OK".equals(partes[0])) Printer.exito(partes[1]);
        else                         Printer.error(partes[1]);
        InputHelper.pausar();
    }

    // ── Eliminar juego ─────────────────────────────────────────────────────────

    private void eliminarJuego() {
        Printer.titulo("🗑 ELIMINAR JUEGO");
        int id = InputHelper.leerEntero("   ID del juego a eliminar: ");

        String confirmar = InputHelper.leerTexto("   ¿Estás seguro? (s/n): ");
        if (!"s".equalsIgnoreCase(confirmar)) {
            Printer.info("Operación cancelada.");
            InputHelper.pausar();
            return;
        }

        String resultado = gameService.eliminarJuego(id);
        String[] partes  = resultado.split(":", 2);
        if ("OK".equals(partes[0])) Printer.exito(partes[1]);
        else                         Printer.error(partes[1]);
        InputHelper.pausar();
    }

    // ── Ver usuarios ───────────────────────────────────────────────────────────

    private void verUsuarios() {
        Printer.titulo("👥 USUARIOS REGISTRADOS");
        ArrayList<User> usuarios = authService.getTodosLosUsuarios();
        System.out.printf("  %-20s %-10s %-12s%n", "USUARIO", "ROL", "SALDO");
        Printer.linea2();
        for (User u : usuarios) {
            System.out.printf("  %-20s %-10s $%.2f%n",
                    u.getUsername(), u.getRol(), u.getSaldo());
        }
        Printer.linea2();
        System.out.println("  Total de usuarios: " + usuarios.size());
        InputHelper.pausar();
    }

    // ── Ver ventas ─────────────────────────────────────────────────────────────

    private void verVentas() {
        Printer.titulo("💵 HISTORIAL DE VENTAS");
        ArrayList<Purchase> ventas = purchaseService.getTodasLasCompras();

        if (ventas.isEmpty()) {
            Printer.info("No hay ventas registradas.");
        } else {
            System.out.printf("  %-15s %-30s %-10s %s%n",
                    "USUARIO", "JUEGO", "PRECIO", "FECHA");
            Printer.linea2();
            for (Purchase p : ventas) {
                System.out.printf("  %-15s %-30s $%-9.2f %s%n",
                        p.getUsername(), p.getGameName(),
                        p.getPrecioCompra(), p.getFecha());
            }
            Printer.linea2();
            System.out.printf("  TOTAL RECAUDADO: $%.2f%n",
                    purchaseService.calcularTotalVentas());
        }
        InputHelper.pausar();
    }

    // ── Estadísticas ───────────────────────────────────────────────────────────

    /**
     * Muestra estadísticas básicas usando un HashMap para contar
     * cuántas veces se vendió cada juego.
     */
    private void verEstadisticas() {
        Printer.titulo("📊 ESTADÍSTICAS DEL SISTEMA");

        ArrayList<Purchase> ventas  = purchaseService.getTodasLasCompras();
        ArrayList<User>     usuarios = authService.getTodosLosUsuarios();
        ArrayList<Game>     juegos   = gameService.getTodosLosJuegos();

        // Contar ventas por juego con HashMap
        HashMap<String, Integer> ventasPorJuego = new HashMap<>();
        for (Purchase p : ventas) {
            String nombre = p.getGameName();
            if (ventasPorJuego.containsKey(nombre)) {
                ventasPorJuego.put(nombre, ventasPorJuego.get(nombre) + 1);
            } else {
                ventasPorJuego.put(nombre, 1);
            }
        }

        System.out.println("   Total de usuarios    : " + usuarios.size());
        System.out.println("   Total de juegos      : " + juegos.size());
        System.out.println("   Total de ventas      : " + ventas.size());
        System.out.printf ("   Total recaudado      : $%.2f%n",
                purchaseService.calcularTotalVentas());
        System.out.println();

        if (!ventasPorJuego.isEmpty()) {
            Printer.subtitulo("Ventas por juego");
            for (String juego : ventasPorJuego.keySet()) {
                System.out.printf("   %-30s %d venta(s)%n",
                        juego, ventasPorJuego.get(juego));
            }
        }

        InputHelper.pausar();
    }
}
