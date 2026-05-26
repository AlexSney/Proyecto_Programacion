package models;

/**
 * Modelo que representa una compra realizada por un usuario.
 * Relaciona un usuario con un juego y guarda la fecha de la compra.
 */
public class Purchase {

    // ── Atributos ──────────────────────────────────────────────────────────────
    private String username;
    private int    gameId;
    private String gameName;
    private double precioCompra;
    private String fecha;
    private boolean esFavorito;

    // ── Constructor ────────────────────────────────────────────────────────────

    public Purchase(String username, int gameId, String gameName,
                    double precioCompra, String fecha) {
        this.username     = username;
        this.gameId       = gameId;
        this.gameName     = gameName;
        this.precioCompra = precioCompra;
        this.fecha        = fecha;
        this.esFavorito   = false;
    }

    // ── Getters y Setters ──────────────────────────────────────────────────────

    public String  getUsername()              { return username; }
    public int     getGameId()                { return gameId; }
    public String  getGameName()              { return gameName; }
    public double  getPrecioCompra()          { return precioCompra; }
    public String  getFecha()                 { return fecha; }
    public boolean isEsFavorito()             { return esFavorito; }
    public void    setEsFavorito(boolean f)   { this.esFavorito = f; }

    /**
     * Convierte la compra a línea de texto para persistencia.
     * Formato: username|gameId|gameName|precioCompra|fecha|favorito
     */
    public String toFileString() {
        return username + "|" + gameId + "|" + gameName + "|" +
               precioCompra + "|" + fecha + "|" + esFavorito;
    }

    @Override
    public String toString() {
        String fav = esFavorito ? " ⭐" : "";
        return String.format("  %-30s $%-8.2f  Fecha: %s%s",
                gameName, precioCompra, fecha, fav);
    }
}
