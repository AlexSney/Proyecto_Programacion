package controllers;
import models.Game;
import models.User;
import services.GameService;
import services.PurchaseService;
import utils.InputHelper;
import utils.Printer;

import java.util.ArrayList;

/**
 * Controlador de la tienda.
 * Menús para: ver catálogo, buscar, ver detalles y comprar.
 */
public class StoreController {

    private GameService     gameService;
    private PurchaseService purchaseService;

    // ── Constructor ────────────────────────────────────────────────────────────

    public StoreController(GameService gameService, PurchaseService purchaseService) {
        this.gameService     = gameService;
        this.purchaseService = purchaseService;
    }

    // ── Menú de la tienda ──────────────────────────────────────────────────────

    public void mostrarMenuTienda(User usuario) {
        boolean enTienda = true;

        while (enTienda) {
            Printer.titulo(" TIENDA NEXUSGAMES — Saldo: $" +
                           String.format("%.2f", usuario.getSaldo()));
            System.out.println("   [1] Ver catálogo completo");
            System.out.println("   [2] Buscar juego por nombre");
            System.out.println("   [3] Filtrar por categoría");
            System.out.println("   [4] Comprar un juego");
            System.out.println("   [0] Volver");
            System.out.println();

            int opcion = InputHelper.leerEntero("   Elige una opción: ");

            switch (opcion) {
                case 1: mostrarCatalogo();                  break;
                case 2: buscarJuego();                      break;
                case 3: filtrarPorCategoria();              break;
                case 4: comprarJuego(usuario);              break;
                case 0: enTienda = false;                   break;
                default:
                    Printer.aviso("Opción no válida.");
                    InputHelper.pausar();
            }
        }
    }

    // ── Ver catálogo ───────────────────────────────────────────────────────────

    private void mostrarCatalogo() {
        Printer.titulo(" CATÁLOGO COMPLETO");
        ArrayList<Game> juegos = gameService.getTodosLosJuegos();

        if (juegos.isEmpty()) {
            Printer.info("No hay juegos en el catálogo.");
        } else {
            imprimirEncabezadoCatalogo();
            for (Game g : juegos) {
                System.out.println(g.toString());
            }
        }
        InputHelper.pausar();
    }

    // ── Buscar por nombre ──────────────────────────────────────────────────────

    private void buscarJuego() {
        Printer.titulo(" BUSCAR JUEGO");
        String palabra = InputHelper.leerTexto("   Nombre o palabra clave: ");

        if (InputHelper.estaVacio(palabra)) {
            Printer.aviso("Ingresa un término de búsqueda.");
            InputHelper.pausar();
            return;
        }

        ArrayList<Game> resultados = gameService.buscarPorNombre(palabra);

        if (resultados.isEmpty()) {
            Printer.info("No se encontraron juegos con '" + palabra + "'.");
        } else {
            Printer.subtitulo("Resultados para: " + palabra);
            imprimirEncabezadoCatalogo();
            for (Game g : resultados) {
                System.out.println(g.toString());
            }
        }
        InputHelper.pausar();
    }

    // ── Filtrar por categoría ──────────────────────────────────────────────────

    private void filtrarPorCategoria() {
        Printer.titulo("🏷 FILTRAR POR CATEGORÍA");
        System.out.println("   Categorías disponibles: RPG, Accion, Sandbox, Deportes, Indie, Social");
        String categoria = InputHelper.leerTexto("   Categoría: ");

        ArrayList<Game> resultados = gameService.filtrarPorCategoria(categoria);

        if (resultados.isEmpty()) {
            Printer.info("No hay juegos en la categoría '" + categoria + "'.");
        } else {
            Printer.subtitulo("Categoría: " + categoria);
            imprimirEncabezadoCatalogo();
            for (Game g : resultados) {
                System.out.println(g.toString());
            }
        }
        InputHelper.pausar();
    }

    // ── Comprar juego ──────────────────────────────────────────────────────────

    private void comprarJuego(User usuario) {
        Printer.titulo(" COMPRAR JUEGO");
        mostrarCatalogoSilencioso();

        int id = InputHelper.leerEntero("   ID del juego a comprar: ");

        // Mostrar detalles antes de confirmar
        Game juego = gameService.buscarPorId(id);
        if (juego == null) {
            Printer.error("No existe un juego con ID " + id + ".");
            InputHelper.pausar();
            return;
        }

        Printer.subtitulo("Detalles del juego");
        System.out.println("   Nombre    : " + juego.getNombre());
        System.out.println("   Precio    : $" + String.format("%.2f", juego.getPrecio()));
        System.out.println("   Categoría : " + juego.getCategoria());
        System.out.println("   Stock     : " + juego.getStock());
        System.out.println("   Rating    : " + juego.getClasificacion());
        System.out.println("   Tu saldo  : $" + String.format("%.2f", usuario.getSaldo()));
        System.out.println();

        String confirmar = InputHelper.leerTexto("   ¿Confirmar compra? (s/n): ");

        if (!"s".equalsIgnoreCase(confirmar)) {
            Printer.info("Compra cancelada.");
            InputHelper.pausar();
            return;
        }

        String resultado = purchaseService.comprar(usuario, id);
        String[] partes  = resultado.split(":", 2);

        if ("OK".equals(partes[0])) {
            Printer.exito(partes[1]);
        } else {
            Printer.error(partes[1]);
        }
        InputHelper.pausar();
    }

    // ── Helpers internos ───────────────────────────────────────────────────────

    private void imprimirEncabezadoCatalogo() {
        System.out.printf("  %-6s %-30s %-10s %-15s %-8s %s%n",
                "ID", "NOMBRE", "PRECIO", "CATEGORÍA", "STOCK", "RATING");
        Printer.linea2();
    }

    /** Muestra el catálogo sin pausar (usado antes de pedir el ID de compra). */
    private void mostrarCatalogoSilencioso() {
        ArrayList<Game> juegos = gameService.getTodosLosJuegos();
        if (!juegos.isEmpty()) {
            imprimirEncabezadoCatalogo();
            for (Game g : juegos) {
                System.out.println(g.toString());
            }
            Printer.linea2();
        }
    }
}
