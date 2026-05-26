package models;

/**
 * Modelo que representa un videojuego dentro del catálogo de NexusGames.
 */
public class Game {

    // ── Atributos ──────────────────────────────────────────────────────────────
    private int    id;
    private String nombre;
    private double precio;
    private String categoria;
    private int    stock;
    private String clasificacion;   // "E", "T", "M", etc.

    // ── Constructor completo ───────────────────────────────────────────────────

    public Game(int id, String nombre, double precio,
                String categoria, int stock, String clasificacion) {
        this.id            = id;
        this.nombre        = nombre;
        this.precio        = precio;
        this.categoria     = categoria;
        this.stock         = stock;
        this.clasificacion = clasificacion;
    }

    // ── Getters y Setters ──────────────────────────────────────────────────────

    public int    getId()                        { return id; }
    public void   setId(int id)                  { this.id = id; }

    public String getNombre()                    { return nombre; }
    public void   setNombre(String n)            { this.nombre = n; }

    public double getPrecio()                    { return precio; }
    public void   setPrecio(double p)            { this.precio = p; }

    public String getCategoria()                 { return categoria; }
    public void   setCategoria(String c)         { this.categoria = c; }

    public int    getStock()                     { return stock; }
    public void   setStock(int s)                { this.stock = s; }

    public String getClasificacion()             { return clasificacion; }
    public void   setClasificacion(String cl)    { this.clasificacion = cl; }

    // ── Métodos de utilidad ────────────────────────────────────────────────────

    public boolean hayStock() {
        return this.stock > 0;
    }

    /**
     * Convierte el juego a línea de texto para persistencia.
     * Formato: id|nombre|precio|categoria|stock|clasificacion
     */
    public String toFileString() {
        return id + "|" + nombre + "|" + precio + "|" +
               categoria + "|" + stock + "|" + clasificacion;
    }

    @Override
    public String toString() {
        return String.format("  [%d] %-30s $%-8.2f  Categoría: %-15s  Stock: %d  [%s]",
                id, nombre, precio, categoria, stock, clasificacion);
    }
}
