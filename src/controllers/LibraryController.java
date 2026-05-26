package controllers;

import models.Purchase;
import models.User;
import services.PurchaseService;
import services.WalletService;
import utils.InputHelper;
import utils.Printer;

import java.util.ArrayList;

/**
 * Controlador de la biblioteca personal del usuario.
 * Gestiona: ver juegos comprados, favoritos y recargar saldo.
 */
public class LibraryController {

    private PurchaseService purchaseService;
    private WalletService   walletService;

    // ── Constructor ────────────────────────────────────────────────────────────

    public LibraryController(PurchaseService purchaseService, WalletService walletService) {
        this.purchaseService = purchaseService;
        this.walletService   = walletService;
    }

    // ── Menú de biblioteca ─────────────────────────────────────────────────────

    public void mostrarMenuBiblioteca(User usuario) {
        boolean enBiblioteca = true;

        while (enBiblioteca) {
            Printer.titulo("📚 MI BIBLIOTECA — " + usuario.getUsername());
            System.out.println("   [1] Ver todos mis juegos");
            System.out.println("   [2] Ver favoritos ⭐");
            System.out.println("   [3] Marcar / quitar favorito");
            System.out.println("   [4] Mi billetera 💰");
            System.out.println("   [0] Volver");
            System.out.println();

            int opcion = InputHelper.leerEntero("   Elige una opción: ");

            switch (opcion) {
                case 1: verMisJuegos(usuario);          break;
                case 2: verFavoritos(usuario);          break;
                case 3: gestionarFavorito(usuario);     break;
                case 4: menuBilletera(usuario);         break;
                case 0: enBiblioteca = false;           break;
                default:
                    Printer.aviso("Opción no válida.");
                    InputHelper.pausar();
            }
        }
    }

    // ── Ver todos los juegos ───────────────────────────────────────────────────

    private void verMisJuegos(User usuario) {
        Printer.titulo("🎮 MIS JUEGOS");
        ArrayList<Purchase> biblioteca = purchaseService.obtenerBiblioteca(usuario.getUsername());

        if (biblioteca.isEmpty()) {
            Printer.info("Aún no has comprado ningún juego. ¡Ve a la tienda!");
        } else {
            System.out.printf("  %-5s %-30s %-10s %-12s %s%n",
                    "ID", "JUEGO", "PRECIO", "FECHA", "FAV");
            Printer.linea2();
            for (Purchase p : biblioteca) {
                String fav = p.isEsFavorito() ? "⭐" : "-";
                System.out.printf("  %-5d %-30s $%-9.2f %-12s %s%n",
                        p.getGameId(), p.getGameName(),
                        p.getPrecioCompra(), p.getFecha(), fav);
            }
            Printer.linea2();
            System.out.println("  Total de juegos: " + biblioteca.size());
        }
        InputHelper.pausar();
    }

    // ── Ver favoritos ──────────────────────────────────────────────────────────

    private void verFavoritos(User usuario) {
        Printer.titulo("⭐ MIS FAVORITOS");
        ArrayList<Purchase> favoritos = purchaseService.obtenerFavoritos(usuario.getUsername());

        if (favoritos.isEmpty()) {
            Printer.info("No tienes juegos en favoritos.");
        } else {
            for (Purchase p : favoritos) {
                System.out.println(p.toString());
            }
        }
        InputHelper.pausar();
    }

    // ── Gestionar favorito ─────────────────────────────────────────────────────

    private void gestionarFavorito(User usuario) {
        Printer.titulo("⭐ MARCAR FAVORITO");
        ArrayList<Purchase> biblioteca = purchaseService.obtenerBiblioteca(usuario.getUsername());

        if (biblioteca.isEmpty()) {
            Printer.info("No tienes juegos en tu biblioteca.");
            InputHelper.pausar();
            return;
        }

        // Mostrar biblioteca para que el usuario elija
        for (Purchase p : biblioteca) {
            String estado = p.isEsFavorito() ? "⭐" : "  ";
            System.out.println("  [" + p.getGameId() + "] " + estado + " " + p.getGameName());
        }

        int id = InputHelper.leerEntero("   ID del juego: ");
        String resultado = purchaseService.toggleFavorito(usuario.getUsername(), id);
        String[] partes  = resultado.split(":", 2);

        if ("OK".equals(partes[0])) {
            Printer.exito(partes[1]);
        } else {
            Printer.error(partes[1]);
        }
        InputHelper.pausar();
    }

    // ── Billetera ──────────────────────────────────────────────────────────────

    private void menuBilletera(User usuario) {
        boolean enWallet = true;

        while (enWallet) {
            Printer.titulo("💰 MI BILLETERA");
            System.out.printf("   Saldo actual: $%.2f%n", usuario.getSaldo());
            System.out.println();
            System.out.println("   [1] Recargar saldo");
            System.out.println("   [0] Volver");
            System.out.println();

            int opcion = InputHelper.leerEntero("   Elige una opción: ");

            switch (opcion) {
                case 1:
                    double monto = InputHelper.leerDecimal(
                            "   Monto a recargar (máx $500): $");
                    String resultado = walletService.recargar(usuario, monto);
                    String[] partes  = resultado.split(":", 2);
                    if ("OK".equals(partes[0])) {
                        Printer.exito(partes[1]);
                    } else {
                        Printer.error(partes[1]);
                    }
                    InputHelper.pausar();
                    break;
                case 0:
                    enWallet = false;
                    break;
                default:
                    Printer.aviso("Opción no válida.");
                    InputHelper.pausar();
            }
        }
    }
}
