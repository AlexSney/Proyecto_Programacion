package services;

import models.Game;
import models.Purchase;
import models.User;
import utils.FileManager;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Servicio de compras y biblioteca.
 * Gestiona: comprar juegos, historial, favoritos.
 */
public class PurchaseService {

    private ArrayList<Purchase> compras;
    private WalletService       walletService;
    private GameService         gameService;

    // ── Constructor ────────────────────────────────────────────────────────────

    public PurchaseService(WalletService walletService, GameService gameService) {
        this.walletService = walletService;
        this.gameService   = gameService;
        this.compras       = FileManager.cargarCompras();
    }

    // ── Comprar un juego ───────────────────────────────────────────────────────

    /**
     * Ejecuta el proceso de compra completo con todas sus validaciones.
     *
     * @return mensaje de resultado para mostrar al usuario
     */
    public String comprar(User usuario, int gameId) {
        // Validación 1: el juego existe
        Game juego = gameService.buscarPorId(gameId);
        if (juego == null) {
            return "ERROR:No existe un juego con ID " + gameId + ".";
        }

        // Validación 2: stock disponible
        if (!juego.hayStock()) {
            return "ERROR:El juego '" + juego.getNombre() + "' está agotado.";
        }

        // Validación 3: compra duplicada
        if (yaComprado(usuario.getUsername(), gameId)) {
            return "ERROR:Ya tienes '" + juego.getNombre() + "' en tu biblioteca.";
        }

        // Validación 4: saldo suficiente
        boolean descuento = walletService.descontar(usuario, juego.getPrecio());
        if (!descuento) {
            return "ERROR:Saldo insuficiente. Necesitas $" +
                   String.format("%.2f", juego.getPrecio()) +
                   " y tienes $" + String.format("%.2f", usuario.getSaldo()) + ".";
        }

        // Todo correcto: registrar la compra y reducir stock
        String fecha  = LocalDate.now().toString();
        Purchase nueva = new Purchase(
            usuario.getUsername(), juego.getId(),
            juego.getNombre(), juego.getPrecio(), fecha
        );
        compras.add(nueva);
        gameService.reducirStock(gameId);
        FileManager.guardarCompras(compras);

        return "OK:¡Compra exitosa! '" + juego.getNombre() + "' agregado a tu biblioteca.";
    }

    // ── Verificar compra duplicada ─────────────────────────────────────────────

    private boolean yaComprado(String username, int gameId) {
        for (Purchase p : compras) {
            if (p.getUsername().equalsIgnoreCase(username) && p.getGameId() == gameId) {
                return true;
            }
        }
        return false;
    }

    // ── Biblioteca del usuario ─────────────────────────────────────────────────

    /** Devuelve solo las compras del usuario actual. */
    public ArrayList<Purchase> obtenerBiblioteca(String username) {
        ArrayList<Purchase> biblioteca = new ArrayList<>();
        for (Purchase p : compras) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                biblioteca.add(p);
            }
        }
        return biblioteca;
    }

    // ── Favoritos ──────────────────────────────────────────────────────────────

    /**
     * Marca o desmarca un juego como favorito.
     * Busca entre las compras del usuario el gameId indicado.
     */
    public String toggleFavorito(String username, int gameId) {
        for (Purchase p : compras) {
            if (p.getUsername().equalsIgnoreCase(username) && p.getGameId() == gameId) {
                p.setEsFavorito(!p.isEsFavorito());
                FileManager.guardarCompras(compras);
                String estado = p.isEsFavorito() ? "agregado a favoritos ⭐" : "eliminado de favoritos";
                return "OK:'" + p.getGameName() + "' " + estado + ".";
            }
        }
        return "ERROR:No tienes ese juego en tu biblioteca.";
    }

    /** Devuelve solo los juegos marcados como favoritos del usuario. */
    public ArrayList<Purchase> obtenerFavoritos(String username) {
        ArrayList<Purchase> favoritos = new ArrayList<>();
        for (Purchase p : obtenerBiblioteca(username)) {
            if (p.isEsFavorito()) {
                favoritos.add(p);
            }
        }
        return favoritos;
    }

    // ── Estadísticas para Admin ────────────────────────────────────────────────

    public ArrayList<Purchase> getTodasLasCompras() {
        return compras;
    }

    /** Calcula el total de ventas sumando los precios de todas las compras. */
    public double calcularTotalVentas() {
        double total = 0;
        for (Purchase p : compras) {
            total += p.getPrecioCompra();
        }
        return total;
    }
}
